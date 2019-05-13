package com.noobanidus.warmstone.core;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Predicate;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.SortingIndex(1001)
public class WarmStoneCore implements IFMLLoadingPlugin {
    public static String getAverageGroundLevel;

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { "com.noobanidus.warmstone.core.ClassTransformer"};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        boolean dev = !(Boolean) data.get("runtimeDeobfuscationEnabled");
        getAverageGroundLevel = dev ? "getAverageGroundLevel" : "func_74889_b";
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    public static MethodNode findMethod (ClassNode node, Predicate<MethodNode> finder) {
        for (MethodNode m : node.methods) {
            if (finder.test(m)) return m;
        }

        return null;
    }

    public static Predicate<MethodNode> averageGroundLevelFinder = methodNode -> methodNode.name.equals(WarmStoneCore.getAverageGroundLevel);
}
