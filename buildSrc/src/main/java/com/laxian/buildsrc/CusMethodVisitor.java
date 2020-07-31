package com.laxian.buildsrc;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.MethodNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.RETURN;


public class CusMethodVisitor extends MethodVisitor {
    private final ClassVisitor cv;
    private final String className;

    public CusMethodVisitor(String className, ClassVisitor cv, MethodVisitor methodVisitor) {
        super(Opcodes.ASM7, methodVisitor);
        this.cv = cv;
        this.className = className;
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        System.out.println("####sb: " + name);
        List<Object> argumentList = new ArrayList<>(Arrays.asList(bootstrapMethodArguments));
        Type descriptorType = Type.getType(descriptor);
        Type methodType = (Type) argumentList.get(0);
        Type methodImplType = (Type) argumentList.get(2);
        String methodDesc = methodType.getDescriptor();
        String methodNameAndDesc = name + methodDesc;

        //---------------------函数式接口过滤-----------------------

        Type[] type = descriptorType.getArgumentTypes();
        String middleMethodDesc;
        if (type.length == 0) {
            middleMethodDesc = methodImplType.getDescriptor();
        } else {
            StringBuilder sb = new StringBuilder("(");
            for (int i = 0; i < type.length; i++) {
                sb.append(type[i].getDescriptor());
                if (i < type.length - 1) {
                    sb.append(", ");
                }
            }
            String suffix = methodImplType.getDescriptor().replace("(", "");
            sb.append(suffix);
            middleMethodDesc = sb.toString();
        }

        System.out.println("sb: " + middleMethodDesc);
        String middleMethodName = "lambda$" + name + "$" + "trace0";
        Handle oldMethodHandle = (Handle) argumentList.get(1);
        Handle newMethodHandle = new Handle(Opcodes.H_INVOKESTATIC, className, middleMethodName, middleMethodDesc, false);
        argumentList.set(1, newMethodHandle);

        MethodNode methodNode = new MethodNode(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC, middleMethodName, middleMethodDesc, null, null);
        methodNode.visitCode();
        int opcode;
        switch (oldMethodHandle.getTag()) {
            case Opcodes.H_INVOKEINTERFACE:
                opcode = Opcodes.INVOKEINTERFACE;
                break;
            case Opcodes.H_INVOKESPECIAL:
                opcode = INVOKESPECIAL;
                break;
            case Opcodes.H_NEWINVOKESPECIAL:
                methodNode.visitTypeInsn(NEW, oldMethodHandle.getOwner());
                methodNode.visitInsn(DUP);
                opcode = INVOKESPECIAL;
                break;
            case Opcodes.H_INVOKESTATIC:
                opcode = Opcodes.INVOKESTATIC;
                break;
            case Opcodes.H_INVOKEVIRTUAL:
                opcode = INVOKEVIRTUAL;
                break;
            default:
                opcode = 0;
        }

        Type middleMethodType = Type.getType(middleMethodDesc);
        Type[] argumentType = middleMethodType.getArgumentTypes();
        if (argumentType.length > 0) {
            int loadIndex = 0;
            for (Type value : argumentType) {
                methodNode.visitVarInsn(value.getOpcode(Opcodes.ILOAD), loadIndex);
                loadIndex += value.getSize();
            }
        }

        //调用原来的lambda实现
        methodNode.visitMethodInsn(opcode, oldMethodHandle.getOwner(), oldMethodHandle.getName(), oldMethodHandle.getDesc(), false);
        Type returnType = middleMethodType.getReturnType();
        int returnOpcodes = returnType.getOpcode(Opcodes.IRETURN);
        methodNode.visitInsn(returnOpcodes);
        methodNode.visitEnd();
        methodNode.accept(this.cv);
        {
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitInsn(DUP);
            mv.visitInvokeDynamicInsn("run", "()Ljava/lang/Runnable;", new Handle(Opcodes.H_INVOKESTATIC, "java/lang/invoke/LambdaMetafactory", "metafactory", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;"), new Object[]{Type.getType("()V"), new Handle(Opcodes.H_INVOKESTATIC, "com/laxian/asmdemo/ui/main/Java8", "lambda$run$trace0", "()V"), Type.getType("()V")});
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Thread", "<init>", "(Ljava/lang/Runnable;)V", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Thread", "start", "()V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(9, l1);
            mv.visitInsn(RETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("args", "[Ljava/lang/String;", null, l0, l2, 0);
            mv.visitMaxs(3, 1);
            mv.visitEnd();
        }
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
    }
}
