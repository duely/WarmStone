package com.noobanidus.warmstone;

import com.noobanidus.warmstone.init.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Util {
    public static int getCaveY(World world, BlockPos pos) {
        int airCount = 0;
        int solid = 0;

        for (int y = Reference.MIN_CAVE_Y - 5; y <= Reference.MAX_CAVE_Y; y++) {
            BlockPos nPos = new BlockPos(pos.getX(), y, pos.getZ());

            IBlockState state = world.getBlockState(nPos);
            if (state.getBlock() == Blocks.AIR) {
                if (solid != 0) {
                    airCount++;
                    if (airCount >= 3) {
                        return solid + 1;
                    }
                }
            } else {
                solid = y;
            }
        }

        return 256;
    }
}
