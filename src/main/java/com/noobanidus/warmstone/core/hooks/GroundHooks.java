package com.noobanidus.warmstone.core.hooks;

import com.noobanidus.warmstone.Util;
import com.noobanidus.warmstone.WarmStone;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureVillagePieces;

public class GroundHooks {
    public static int getAverageGroundLevel(StructureVillagePieces.Village village, World worldIn, StructureBoundingBox structurebb) {
        int i = 0;
        int j = 0;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int k = village.boundingBox.minZ; k <= village.boundingBox.maxZ; ++k) {
            for (int l = village.boundingBox.minX; l <= village.boundingBox.maxX; ++l) {
                blockpos$mutableblockpos.setPos(l, 64, k);

                if (structurebb.isVecInside(blockpos$mutableblockpos)) {
                    int bottom = Util.getCaveY(worldIn, blockpos$mutableblockpos);
                    i += Math.max(bottom, worldIn.provider.getAverageGroundLevel() - 1);
                    ++j;
                }
            }
        }

        if (j == 0) {
            return -1;
        } else {
            int avg = i / j;
            return avg;
        }
    }

}
