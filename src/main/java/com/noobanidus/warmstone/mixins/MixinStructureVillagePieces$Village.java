package com.noobanidus.warmstone.mixins;

import com.noobanidus.warmstone.init.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(StructureVillagePieces.Village.class)
@SuppressWarnings("unused")
public abstract class MixinStructureVillagePieces$Village extends StructureComponent {
    /**
     * @author Noobanidus
     */
    @Overwrite
    protected int getAverageGroundLevel(World worldIn, StructureBoundingBox structurebb) {
        int i = 0;
        int j = 0;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int k = ((StructureVillagePieces.Village) (Object) this).boundingBox.minZ; k <= ((StructureVillagePieces.Village) (Object) this).boundingBox.maxZ; ++k) {
            for (int l = ((StructureVillagePieces.Village) (Object) this).boundingBox.minX; l <= ((StructureVillagePieces.Village) (Object) this).boundingBox.maxX; ++l) {
                blockpos$mutableblockpos.setPos(l, 64, k);

                if (structurebb.isVecInside(blockpos$mutableblockpos)) {
                    i += Math.max(getCaveLayer(worldIn, blockpos$mutableblockpos), worldIn.provider.getAverageGroundLevel() - 1);
                    ++j;
                }
            }
        }

        if (j == 0) {
            return -1;
        } else {
            return i / j;
        }
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
