package com.noobanidus.warmstone.core.hooks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class FallingHooks {
    public static boolean fallingEnabled = true;

    public static boolean canFallThrough(IBlockState state) {
        if (!fallingEnabled) return false;
        Block block = state.getBlock();
        Material material = state.getMaterial();
        return block == Blocks.FIRE || material == Material.AIR || material == Material.WATER || material == Material.LAVA;
    }

}
