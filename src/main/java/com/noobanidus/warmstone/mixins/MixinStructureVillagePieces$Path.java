package com.noobanidus.warmstone.mixins;

import com.noobanidus.warmstone.init.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

@Mixin(StructureVillagePieces.Path.class)
@SuppressWarnings("unused")
public abstract class MixinStructureVillagePieces$Path extends StructureVillagePieces.Village {
    /**
     * @author Noobanidus
     */
    @Overwrite
    public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
        IBlockState iblockstate = Blocks.GRASS_PATH.getDefaultState();
        IBlockState iblockstate1 = Blocks.PLANKS.getDefaultState();
        IBlockState iblockstate2 = Blocks.GRAVEL.getDefaultState();
        IBlockState iblockstate3 = Blocks.COBBLESTONE.getDefaultState();

        for (int i = ((StructureVillagePieces.Path) (Object) this).boundingBox.minX; i <= ((StructureVillagePieces.Path) (Object) this).boundingBox.maxX; ++i) {
            for (int j = ((StructureVillagePieces.Path) (Object) this).boundingBox.minZ; j <= ((StructureVillagePieces.Path) (Object) this).boundingBox.maxZ; ++j) {
                BlockPos blockpos = new BlockPos(i, getCaveLayer(worldIn, new BlockPos(i, 0, j)), j);

                if (structureBoundingBoxIn.isVecInside(blockpos)) {
                    blockpos = blockpos.down();

                    if (blockpos.getY() < worldIn.getSeaLevel()) {
                        blockpos = new BlockPos(blockpos.getX(), worldIn.getSeaLevel() - 1, blockpos.getZ());
                    }

                    while (blockpos.getY() >= worldIn.getSeaLevel() - 1) {
                        IBlockState iblockstate4 = worldIn.getBlockState(blockpos);

                        if (iblockstate4.getBlock() == Blocks.GRASS && worldIn.isAirBlock(blockpos.up())) {
                            worldIn.setBlockState(blockpos, iblockstate, 2);
                            break;
                        }

                        if (iblockstate4.getMaterial().isLiquid()) {
                            worldIn.setBlockState(blockpos, iblockstate1, 2);
                            break;
                        }

                        if (iblockstate4.getBlock() == Blocks.SAND || iblockstate4.getBlock() == Blocks.SANDSTONE || iblockstate4.getBlock() == Blocks.RED_SANDSTONE) {
                            worldIn.setBlockState(blockpos, iblockstate2, 2);
                            worldIn.setBlockState(blockpos.down(), iblockstate3, 2);
                            break;
                        }

                        blockpos = blockpos.down();
                    }
                }
            }
        }

        return true;
    }

    public int getCaveLayer(World world, BlockPos pos) {
        BlockPos cur = new BlockPos(pos.getX(), Reference.MIN_CAVE_Y, pos.getZ());

        int air_count = 0;
        int last_solid = 0;

        for (; cur.getY() < Reference.MAX_CAVE_Y; ) {
            IBlockState state = world.getBlockState(cur);

            if (state.getMaterial().blocksMovement() && !state.getBlock().isLeaves(state, world, cur) && !state.getBlock().isFoliage(world, cur)) {
                last_solid = cur.getY();
            } else {
                air_count++;
            }

            if (air_count >= 3) {
                return last_solid + 1;
            }

            cur = cur.up();
        }

        return cur.getY();
    }
}
