package com.noobanidus.warmstone.world;

import com.noobanidus.warmstone.Util;
import com.noobanidus.warmstone.WarmStone;
import com.noobanidus.warmstone.init.Reference;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Mod.EventBusSubscriber(modid = WarmStone.MODID)
@SuppressWarnings("unused")
public class PopulatePost {
    private static Random random = new Random();
    private static Map<ChunkPos, Long> villageChunks = new Object2LongOpenHashMap<>();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPopulatePost(PopulateChunkEvent.Post event) {
        int chunkx = event.getChunkX();
        int chunkz = event.getChunkZ();
        World world = event.getWorld();


        Chunk chunk = world.getChunk(chunkx, chunkz);
        BlockPos bChunk = new BlockPos(chunkx * 16, 0, chunkz * 16).add(8, 0, 8);

        underpinGravel(world, chunk, bChunk);

        if (event.isHasVillageGenerated()) {
            ChunkPos cpos = new ChunkPos(chunkx, chunkz);
            if (villageChunks.isEmpty() || villageChunks.get(cpos) == null) {
                BlockPos nearest = ((WorldServer) world).getChunkProvider().getNearestStructurePos(world, "Village", new BlockPos(chunkx << 4, 0, chunkz << 4), false);
                if (nearest != null) {
                    villageChunks.put(cpos, nearest.toLong());
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onDecorate(DecorateBiomeEvent.Decorate event) {
        if (villageChunks.get(event.getChunkPos()) != null && event.getType().equals(DecorateBiomeEvent.Decorate.EventType.FLOWERS)) {
            World world = event.getWorld();
            ChunkPos cpos = event.getChunkPos();
            List<ChunkPos> chunks = new ArrayList<>();
            if (villageChunks.containsKey(cpos)) {
                long seed = villageChunks.get(cpos);
                for (Map.Entry<ChunkPos, Long> pos : villageChunks.entrySet()) {
                    if (pos.getValue() == seed) {
                        chunks.add(pos.getKey());
                    }
                }

                chunks.forEach((c) -> villageChunks.remove(c));
            }

            if (chunks.isEmpty()) return;

            for (ChunkPos c : chunks) {
                BlockPos pos = new BlockPos(c.x << 4, 0, c.z << 4);
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        for (int y = Reference.MIN_CAVE_Y - 5; y <= Reference.MAX_CAVE_Y; y++) {
                            IBlockState state = world.getBlockState(pos.add(x, y, z));
                            if (state.getMaterial().isLiquid() && (state.getBlock() == Blocks.LAVA || state.getBlock() == Blocks.FLOWING_LAVA)) {
                                world.setBlockState(pos.add(x, y, z), Blocks.STONE.getDefaultState(), Constants.BlockFlags.NO_OBSERVERS | Constants.BlockFlags.SEND_TO_CLIENTS);
                            }
                        }

                        int y = Util.getCaveY(world, pos.add(x, 0, z));
                        int lightLevel = world.getLightFor(EnumSkyBlock.BLOCK, pos);
                        if (lightLevel > 10) {
                            tryPlacingTorch(pos.add(x, y, z), world);
                        }
                    }
                }

            }
        }
    }

    private static void tryPlacingTorch(BlockPos pos, World world) {
        IBlockState state = world.getBlockState(pos.down());
        if (state.getBlock().canPlaceTorchOnTop(state, world, pos.down())) {
            world.setBlockState(pos, Blocks.TORCH.getDefaultState(), Constants.BlockFlags.NO_OBSERVERS | Constants.BlockFlags.SEND_TO_CLIENTS);
        }
    }

    public static void underpinGravel(World world, Chunk chunk, BlockPos bChunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                Block previous = null;

                for (int y = Reference.MIN_CAVE_Y; y <= Reference.MAX_CAVE_Y; y++) {
                    IBlockState state = chunk.getBlockState(bChunk.add(x, y, z));
                    if (previous == Blocks.AIR && state.getBlock() == Blocks.GRAVEL) {
                        world.setBlockState(bChunk.add(x, y, z), Blocks.STONE.getDefaultState(), Constants.BlockFlags.NO_OBSERVERS | Constants.BlockFlags.SEND_TO_CLIENTS);
                    }

                    previous = state.getBlock();
                }
            }
        }
    }
}
