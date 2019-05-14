package com.noobanidus.warmstone.core.transformers;

import com.noobanidus.warmstone.core.CustomClassWriter;
import com.noobanidus.warmstone.core.IWarmTransformer;
import com.noobanidus.warmstone.core.WarmStoneCore;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Collections;
import java.util.List;

public class FallingTransformer implements IWarmTransformer {
    @Override
    public boolean accepts(String name, String transformedName) {
        List<String> classes = Collections.singletonList("net.minecraft.block.BlockFalling");
        for (String clazz : classes) {
            if (clazz.equals(transformedName)) return true;
        }

        return false;
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, 0);

        MethodNode falling = WarmStoneCore.findMethod(classNode, WarmStoneCore.canFallThroughFinder);
        if (falling != null) {
            falling.instructions.clear();
            falling.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
            falling.instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/noobanidus/warmstone/core/hooks/FallingHooks", "canFallThrough", "(Lnet/minecraft/block/state/IBlockState;)Z", false));
            falling.instructions.add(new InsnNode(Opcodes.IRETURN));

            CustomClassWriter writer = new CustomClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(writer);
            return writer.toByteArray();
        }

        return basicClass;
    }
}
