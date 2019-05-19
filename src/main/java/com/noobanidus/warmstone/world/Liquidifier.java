package com.noobanidus.warmstone.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class Liquidifier implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        Chunk chunk = world.getChunk(chunkX, chunkZ);
        BlockPos bChunk = new BlockPos(chunkX * 16, 0, chunkZ * 16);

        /*for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 21; y++) {
                    IBlockState state = world.getBlockState(bChunk.add(x, y, z));
                    Block block = state.getBlock();
                    if (block == Blocks.AIR || block == Blocks.WATER || block == Blocks.LAVA || block == Blocks.FLOWING_LAVA || block == Blocks.FLOWING_WATER || block instanceof IFluidBlock || block instanceof BlockLiquid) {
                        world.setBlockState(bChunk.add(x, y, z), Blocks.STONE.getDefaultState(), Constants.BlockFlags.NO_OBSERVERS | Constants.BlockFlags.SEND_TO_CLIENTS);
                    }
                }
            }
        }*/
    }
}
