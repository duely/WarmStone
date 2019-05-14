package com.noobanidus.warmstone.core.hooks;

import com.noobanidus.warmstone.Util;
import com.noobanidus.warmstone.init.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureVillagePieces;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class PathHooks {
    public static List<Block> GRASS_STATES = Arrays.asList(Blocks.STONE, Blocks.MYCELIUM, Blocks.DIRT, Blocks.GRAVEL, Blocks.SAND, Blocks.SANDSTONE, Blocks.RED_SANDSTONE);

    public static boolean addComponentParts(StructureVillagePieces.Path path, World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
        IBlockState iblockstate = Blocks.GRASS_PATH.getDefaultState();
        IBlockState iblockstate1 = Blocks.PLANKS.getDefaultState();
        IBlockState iblockstate2 = Blocks.GRAVEL.getDefaultState();
        IBlockState iblockstate3 = Blocks.COBBLESTONE.getDefaultState();

        for (int i = path.boundingBox.minX; i <= path.boundingBox.maxX; ++i) {
            for (int j = path.boundingBox.minZ; j <= path.boundingBox.maxZ; ++j) {
                BlockPos blockpos = new BlockPos(i, 64, j);

                if (structureBoundingBoxIn.isVecInside(blockpos)) {

                    blockpos = new BlockPos(blockpos.getX(), Util.getCaveY(worldIn, blockpos) - 1, blockpos.getZ());

                    while (blockpos.getY() >= Reference.MIN_CAVE_Y - 1) {
                        IBlockState iblockstate4 = worldIn.getBlockState(blockpos);

                        if (iblockstate4.getBlock() == Blocks.GRASS && worldIn.isAirBlock(blockpos.up())) {
                            worldIn.setBlockState(blockpos, iblockstate, 2);
                            break;
                        }

                        if (iblockstate4.getBlock() == Blocks.GRASS && worldIn.getBlockState(blockpos.up()).getBlock() == Blocks.TALLGRASS) {
                            worldIn.setBlockState(blockpos, iblockstate, 2);
                            worldIn.setBlockState(blockpos.up(), Blocks.AIR.getDefaultState(), 2);
                        }

                        if (iblockstate4.getMaterial().isLiquid()) {
                            worldIn.setBlockState(blockpos, iblockstate, 2);
                            break;
                        }

                        if (GRASS_STATES.contains(iblockstate4.getBlock())) {
                            worldIn.setBlockState(blockpos, iblockstate, 2);
                            break;
                        }

                        blockpos = blockpos.down();
                    }
                }
            }
        }

        return true;
    }
}
