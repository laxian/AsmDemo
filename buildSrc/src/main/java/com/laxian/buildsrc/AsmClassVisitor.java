package com.laxian.buildsrc;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import java.io.File;

import static org.objectweb.asm.Opcodes.ASM5;

public class AsmClassVisitor extends ClassVisitor {

    private String name;

    public AsmClassVisitor(ClassVisitor cv) {
        super(ASM5, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.name = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (name.equals("onResume")) {
            MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
            return new AsmMethodVisitor(ASM5, methodVisitor, access, name, desc);
        }
        return super.visitMethod(access, name, desc, signature, exceptions);
    }

    public static byte[] modify(File f) throws Exception {

        return null;
    }
}
