package com.laxian.buildsrc;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * use ASM fully create a new class file
 */
public class AsmDemo implements Opcodes {

    public static byte[] dump() throws Exception {

        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;
        AnnotationVisitor av0;

        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, "com/laxian/asmdemo/ui/main/MainFragment", null, "androidx/fragment/app/Fragment", null);

        cw.visitSource("MainFragment.java", null);

        cw.visitInnerClass("com/laxian/asmdemo/R$layout", "com/laxian/asmdemo/R", "layout", ACC_PUBLIC + ACC_FINAL + ACC_STATIC);

        {
            fv = cw.visitField(ACC_PRIVATE, "mViewModel", "Lcom/laxian/asmdemo/ui/main/MainViewModel;", null, null);
            fv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(18, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "androidx/fragment/app/Fragment", "<init>", "()V", false);
            mv.visitInsn(RETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "Lcom/laxian/asmdemo/ui/main/MainFragment;", null, l0, l1, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "newInstance", "()Lcom/laxian/asmdemo/ui/main/MainFragment;", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(23, l0);
            mv.visitTypeInsn(NEW, "com/laxian/asmdemo/ui/main/MainFragment");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "com/laxian/asmdemo/ui/main/MainFragment", "<init>", "()V", false);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "onCreateView", "(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;", null, null);
            {
                av0 = mv.visitAnnotation("Landroidx/annotation/Nullable;", false);
                av0.visitEnd();
            }
            {
                av0 = mv.visitParameterAnnotation(0, "Landroidx/annotation/NonNull;", false);
                av0.visitEnd();
            }
            {
                av0 = mv.visitParameterAnnotation(1, "Landroidx/annotation/Nullable;", false);
                av0.visitEnd();
            }
            {
                av0 = mv.visitParameterAnnotation(2, "Landroidx/annotation/Nullable;", false);
                av0.visitEnd();
            }
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(30, l0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitLdcInsn(new Integer(2131361822));
            mv.visitVarInsn(ALOAD, 2);
            mv.visitInsn(ICONST_0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "android/view/LayoutInflater", "inflate", "(ILandroid/view/ViewGroup;Z)Landroid/view/View;", false);
            mv.visitInsn(ARETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "Lcom/laxian/asmdemo/ui/main/MainFragment;", null, l0, l1, 0);
            mv.visitLocalVariable("inflater", "Landroid/view/LayoutInflater;", null, l0, l1, 1);
            mv.visitLocalVariable("container", "Landroid/view/ViewGroup;", null, l0, l1, 2);
            mv.visitLocalVariable("savedInstanceState", "Landroid/os/Bundle;", null, l0, l1, 3);
            mv.visitMaxs(4, 4);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "onActivityCreated", "(Landroid/os/Bundle;)V", null, null);
            {
                av0 = mv.visitParameterAnnotation(0, "Landroidx/annotation/Nullable;", false);
                av0.visitEnd();
            }
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(35, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKESPECIAL, "androidx/fragment/app/Fragment", "onActivityCreated", "(Landroid/os/Bundle;)V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(36, l1);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESTATIC, "androidx/lifecycle/ViewModelProviders", "of", "(Landroidx/fragment/app/Fragment;)Landroidx/lifecycle/ViewModelProvider;", false);
            mv.visitLdcInsn(Type.getType("Lcom/laxian/asmdemo/ui/main/MainViewModel;"));
            mv.visitMethodInsn(INVOKEVIRTUAL, "androidx/lifecycle/ViewModelProvider", "get", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", false);
            mv.visitTypeInsn(CHECKCAST, "com/laxian/asmdemo/ui/main/MainViewModel");
            mv.visitFieldInsn(PUTFIELD, "com/laxian/asmdemo/ui/main/MainFragment", "mViewModel", "Lcom/laxian/asmdemo/ui/main/MainViewModel;");
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLineNumber(38, l2);
            mv.visitInsn(RETURN);
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitLocalVariable("this", "Lcom/laxian/asmdemo/ui/main/MainFragment;", null, l0, l3, 0);
            mv.visitLocalVariable("savedInstanceState", "Landroid/os/Bundle;", null, l0, l3, 1);
            mv.visitMaxs(3, 2);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "onResume", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(42, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "androidx/fragment/app/Fragment", "onResume", "()V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(43, l1);
            mv.visitLdcInsn("MainFragment");
            mv.visitLdcInsn("hello ASM");
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            mv.visitInsn(POP);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLineNumber(44, l2);
            mv.visitInsn(RETURN);
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitLocalVariable("this", "Lcom/laxian/asmdemo/ui/main/MainFragment;", null, l0, l3, 0);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }
}
