package com.noobanidus.warmstone.world;

import com.noobanidus.warmstone.core.hooks.FallingHooks;
import com.noobanidus.warmstone.init.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class GravelStopper implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        Chunk chunk = world.getChunk(chunkX, chunkZ);
        BlockPos bChunk = new BlockPos(chunkX * 16, 0, chunkZ * 16);

        PopulatePost.underpinGravel(world, chunk, bChunk);
        FallingHooks.fallingEnabled = true;
    }
}
