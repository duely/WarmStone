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

        MethodNode neighbor = WarmStoneCore.findMethod(classNode, WarmStoneCore.neighborChangedFinder);
        if (neighbor != null) {
            neighbor.instructions.clear();
            neighbor.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
            neighbor.instructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
            neighbor.instructions.add(new VarInsnNode(Opcodes.ALOAD, 2));
            neighbor.instructions.add(new VarInsnNode(Opcodes.ALOAD, 3));
            neighbor.instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/noobanidus/warmstone/core/hooks/FallingHooks", "genericUpdate", "(Lnet/minecraft/block/BlockFalling;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)V", false));

            CustomClassWriter writer = new CustomClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(writer);
            return writer.toByteArray();
        }

        MethodNode blockAdded = WarmStoneCore.findMethod(classNode, WarmStoneCore.onBlockAddedFinder);
        if (blockAdded != null) {
            blockAdded.instructions.clear();
            blockAdded.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
            blockAdded.instructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
            blockAdded.instructions.add(new VarInsnNode(Opcodes.ALOAD, 2));
            blockAdded.instructions.add(new VarInsnNode(Opcodes.ALOAD, 3));
            blockAdded.instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/noobanidus/warmstone/core/hooks/FallingHooks", "genericUpdate", "(Lnet/minecraft/block/BlockFalling;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)V", false));

            CustomClassWriter writer = new CustomClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(writer);
            return writer.toByteArray();
        }

        return basicClass;
    }
}
