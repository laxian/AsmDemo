package com.laxian.buildsrc;

import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class AsmMethodVisitor extends AdviceAdapter {

    private static final String TAG = "AsmMethodVisitor -> ";

    protected AsmMethodVisitor(int api, MethodVisitor mv, int access, String name, String desc) {
        super(api, mv, access, name, desc);
    }

    @Override
    public void visitCode() {
        super.visitCode();
        System.out.println(TAG + "visitCode");
    }

    @Override
    public void visitLabel(Label label) {
        super.visitLabel(label);
        System.out.println(TAG + "visitLabel" + label.info);
    }

    @Override
    public void visitInsn(int opcode) {
        if (opcode == Opcodes.RETURN) {
            // 插入System.out(this is a modify method!);
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("this is a modify method!");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            // 插入Log.d("MainFragment", "onResume");
            mv.visitLdcInsn("MainFragment");
            mv.visitLdcInsn("onResume2");
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        }
        super.visitInsn(opcode);
        System.out.println(TAG + "visitInsn");
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        super.visitVarInsn(opcode, var);
        System.out.println(TAG + "visitVarInsn" + opcode + ": " + var);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        super.visitFieldInsn(opcode, owner, name, desc);
        System.out.println(TAG + "visitFieldInsn");
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        super.visitIntInsn(opcode, operand);
        System.out.println(TAG + "visitIntInsn");
    }

    @Override
    public void visitLdcInsn(Object cst) {
        super.visitLdcInsn(cst);
        System.out.println(TAG + "visitLdcInsn");
    }

    @Override
    public void visitMultiANewArrayInsn(String desc, int dims) {
        super.visitMultiANewArrayInsn(desc, dims);
        System.out.println(TAG + "visitMultiANewArrayInsn");
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        super.visitTypeInsn(opcode, type);
        System.out.println(TAG + "visitTypeInsn");
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        super.visitMethodInsn(opcode, owner, name, desc);
        System.out.println(TAG + "visitMethodInsn");
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        super.visitMethodInsn(opcode, owner, name, desc, itf);
        System.out.println(TAG + "visitMethodInsn: " + opcode + ": " + owner + ": " + name + ": " + desc + ": " + itf);
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
        super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
        System.out.println(TAG + "visitInvokeDynamicInsn");
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        super.visitJumpInsn(opcode, label);
        System.out.println(TAG + "visitJumpInsn: " + opcode + ": " + label.info);
    }

    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        super.visitLookupSwitchInsn(dflt, keys, labels);
        System.out.println(TAG + "visitLookupSwitchInsn");
    }

    @Override
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        super.visitTableSwitchInsn(min, max, dflt, labels);
        System.out.println(TAG + "visitTableSwitchInsn");
    }

    @Override
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        super.visitTryCatchBlock(start, end, handler, type);
        System.out.println(TAG + "visitTryCatchBlock");
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        System.out.println(TAG + "onMethodEnter");
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        System.out.println(TAG + "onMethodExit" + opcode);
    }
}
