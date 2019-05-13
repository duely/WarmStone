package com.noobanidus.warmstone.world;

import com.noobanidus.warmstone.WarmStone;
import com.noobanidus.warmstone.init.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

@Mod.EventBusSubscriber(modid = WarmStone.MODID)
@SuppressWarnings("unused")
public class PopulatePost {
    private static Random random = new Random();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPopulatePost(PopulateChunkEvent.Post event) {
        int chunkx = event.getChunkX();
        int chunkz = event.getChunkZ();
        World world = event.getWorld();


        Chunk chunk = world.getChunk(chunkx, chunkz);
        BlockPos bChunk = new BlockPos(chunkx * 16, 0, chunkz * 16).add(8, 0, 8);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                Block previous = null;

                List<IBlockState> states = new ArrayList<>();

                for (int y = Reference.MIN_CAVE_Y; y <= Reference.MAX_CAVE_Y; y++) {
                    IBlockState state = chunk.getBlockState(bChunk.add(x, y, z));
                    if (previous == Blocks.AIR && state.getBlock() == Blocks.GRAVEL) {
                        world.setBlockState(bChunk.add(x, y, z), Blocks.STONE.getDefaultState(), Constants.BlockFlags.NO_OBSERVERS | Constants.BlockFlags.SEND_TO_CLIENTS);
                        states.add(Blocks.STONE.getDefaultState());
                    } else {
                        states.add(state);
                    }

                    previous = state.getBlock();
                }
            }
        }
    }
}
