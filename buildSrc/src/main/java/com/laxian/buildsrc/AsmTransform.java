package com.laxian.buildsrc;

import com.android.build.api.transform.Format;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.gradle.internal.pipeline.TransformManager;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

class AsmTransform extends Transform {
    Project project;

    AsmTransform(Project project) {
        this.project = project;
    }

    @Override
    public String getName() {
        return "AsmTransform";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        transformInvocation.getInputs().forEach(input -> {
            input.getDirectoryInputs().forEach(dir -> {
                if (dir.getFile().isDirectory()) {
                    transformDir(dir.getFile());

                    File dest = transformInvocation.getOutputProvider().getContentLocation(dir.getName(),
                            dir.getContentTypes(), dir.getScopes(),
                            Format.DIRECTORY);
                    try {
                        FileUtils.copyDirectory(dir.getFile(), dest);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            input.getJarInputs().forEach(jarInput ->{
                String jarName = jarInput.getName();
                String md5Name = DigestUtils.md5Hex(jarInput.getFile().getAbsolutePath());
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4);
                }
                File dest = transformInvocation.getOutputProvider().getContentLocation(jarName + md5Name,
                        jarInput.getContentTypes(), jarInput.getScopes(), Format.JAR);
                try {
                    FileUtils.copyFile(jarInput.getFile(), dest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    private void transformDir(File file) {
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                transformDir(f);
            } else {
                transformFile(f);
            }
        }
    }

    private void transformFile(File f) {
        if (f.getPath().contains("MainFragment")) {
            System.out.println(f.getName());
            try {
//                byte[] code = AsmDemo.dump();
                ClassReader reader = new ClassReader(new FileInputStream(f));
                ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
                ClassVisitor visitor = new AsmClassVisitor(writer);
                reader.accept(visitor, ClassReader.EXPAND_FRAMES);
                byte[] code = writer.toByteArray();
                FileOutputStream fos = new FileOutputStream(f.getAbsolutePath());
                fos.write(code);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
