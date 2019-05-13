package com.noobanidus.warmstone.world;

import com.noobanidus.warmstone.init.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class GrassGenerator implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        Chunk chunk = world.getChunk(chunkX, chunkZ);
        BlockPos bChunk = new BlockPos(chunkX * 16, 0, chunkZ * 16);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                Block previous = null;
                for (int y = Reference.MIN_CAVE_Y; y <= Reference.GRASS_LIMIT_Y; y++) {
                    IBlockState state = chunk.getBlockState(bChunk.add(x, y, z));
                    if (state.getBlock() == Blocks.AIR && previous == Blocks.GRASS) {
                        world.setBlockState(bChunk.add(x, y, z),
                                Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS),
                                Constants.BlockFlags.NO_OBSERVERS | Constants.BlockFlags.SEND_TO_CLIENTS);
                    }
                    previous = state.getBlock();
                }
            }
        }
    }
}
