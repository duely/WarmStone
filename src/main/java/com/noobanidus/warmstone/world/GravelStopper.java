package com.noobanidus.warmstone.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class GravelStopper implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        Chunk chunk = world.getChunk(chunkX, chunkZ);
        BlockPos bChunk = new BlockPos(chunkX * 16, 0, chunkZ * 16);

        PopulatePost.underpinGravel(world, chunk, bChunk);
    }
}
