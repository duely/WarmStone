package com.noobanidus.warmstone.core.hooks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FallingHooks {
    public static boolean fallingEnabled = true;

    public static boolean canFallThrough(IBlockState state) {
        if (!fallingEnabled) return false;
        Block block = state.getBlock();
        Material material = state.getMaterial();
        return block == Blocks.FIRE || material == Material.AIR || material == Material.WATER || material == Material.LAVA;
    }

    public static void genericUpdate(BlockFalling block, World worldIn, BlockPos pos, IBlockState state) {
        if (!fallingEnabled) return;
        worldIn.scheduleUpdate(pos, block, block.tickRate(worldIn));
    }
}
