package com.noobanidus.warmstone.flat;

import com.bobmowzie.mowziesmobs.server.entity.barakoa.EntityBarakoa;
import com.noobanidus.warmstone.core.hooks.FallingHooks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.passive.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.structure.*;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public class ChunkGenerator extends ChunkGeneratorFlat {
    private World world;
    private MapGenBase caveGenerator = new MapGenCaves();
    private MapGenBase ravineGenerator = new MapGenRavine();

    private final boolean hasDecoration;
    private final boolean hasDungeons;
    private final boolean hasAnimals;
    private final boolean hasIce;
    private final boolean hasRavines;

    public ChunkGenerator(World worldIn, long seed, boolean generateStructures, String flatGeneratorSettings) {
        super(worldIn, seed, generateStructures, flatGeneratorSettings);

        this.world = worldIn;

        caveGenerator = net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(caveGenerator, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.CAVE);

        ravineGenerator = net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(ravineGenerator, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.RAVINE);

        this.flatWorldGenInfo = FlatGeneratorInfo.createFlatGeneratorFromString(flatGeneratorSettings);
        Map<String, Map<String, String>> map = this.flatWorldGenInfo.getWorldFeatures();

        if (generateStructures) {
            if (map.containsKey("village")) {
                Map<String, String> map1 = map.get("village");

                if (!map1.containsKey("size")) {
                    map1.put("size", "1");
                }

                MapGenVillage temp = new MapGenVillage(map1);
                MapGenVillage villageGenerator = (MapGenVillage) net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(temp, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.VILLAGE);
                this.structureGenerators.put("Village", villageGenerator);
            }

            if (map.containsKey("biome_1")) {
                MapGenScatteredFeature temp = new MapGenScatteredFeature(map.get("biome_1"));
                MapGenScatteredFeature scatteredFeatureGenerator = (MapGenScatteredFeature) net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(temp, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.SCATTERED_FEATURE);
                this.structureGenerators.put("Temple", scatteredFeatureGenerator);
            }

            if (map.containsKey("mineshaft")) {
                MapGenMineshaft temp = new MapGenMineshaft(map.get("mineshaft"));
                MapGenMineshaft mineshaftGenerator = (MapGenMineshaft) net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(temp, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.MINESHAFT);
                this.structureGenerators.put("Mineshaft", mineshaftGenerator);
            }

            if (map.containsKey("stronghold")) {
                MapGenStronghold temp = new MapGenStronghold(map.get("stronghold"));
                MapGenStronghold strongholdGenerator = (MapGenStronghold) net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(temp, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.STRONGHOLD);
                this.structureGenerators.put("Stronghold", strongholdGenerator);
            }

            if (map.containsKey("oceanmonument")) {
                StructureOceanMonument temp = new StructureOceanMonument(map.get("oceanmonument"));
                StructureOceanMonument oceanMonumentGenerator = (StructureOceanMonument) net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(temp, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.OCEAN_MONUMENT);
                this.structureGenerators.put("Monument", oceanMonumentGenerator);
            }
        }

        this.hasRavines = map.containsKey("ravines");
        this.hasDungeons = map.containsKey("dungeon");
        this.hasDecoration = map.containsKey("decoration");
        this.hasAnimals = map.containsKey("animals");
        this.hasIce = map.containsKey("ice");
    }

    private static List<Biome.SpawnListEntry> possibles = null;

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        if (possibles == null) {
            Biome biome = this.world.getBiome(pos);
            possibles = biome.getSpawnableList(creatureType);
            if (creatureType == EnumCreatureType.AMBIENT) {
                possibles.addAll(biome.getSpawnableList(EnumCreatureType.CREATURE));
                possibles.addAll(biome.getSpawnableList(EnumCreatureType.WATER_CREATURE));
                possibles.add(new Biome.SpawnListEntry(EntityRabbit.class, 2, 1, 1));
                possibles.add(new Biome.SpawnListEntry(EntityRabbit.class, 2, 1, 3));
                possibles.add(new Biome.SpawnListEntry(EntityLlama.class, 2, 1, 3));
                possibles.add(new Biome.SpawnListEntry(EntityOcelot.class, 2, 1, 1));
                possibles.add(new Biome.SpawnListEntry(EntityParrot.class, 2, 1, 2));
                possibles.add(new Biome.SpawnListEntry(EntityMooshroom.class, 2, 1, 3));
                possibles.add(new Biome.SpawnListEntry(EntityPolarBear.class, 1, 1, 2));
                possibles.add(new Biome.SpawnListEntry(EntityStray.class, 10, 4, 4));
                possibles.add(new Biome.SpawnListEntry(EntityHusk.class, 10, 4, 4));

                possibles.add(new Biome.SpawnListEntry((Class<? extends EntityLiving>) EntityList.getClass(new ResourceLocation("mowziesmobs", "foliaath")), 4, 1, 3));
                possibles.add(new Biome.SpawnListEntry((Class<? extends EntityLiving>) EntityList.getClass(new ResourceLocation("mowziesmobs", "barakoana")), 5, 1, 3));
                possibles.add(new Biome.SpawnListEntry((Class<? extends EntityLiving>) EntityList.getClass(new ResourceLocation("mowziesmobs", "frostmaw")), 1, 1, 3));
                possibles.add(new Biome.SpawnListEntry((Class<? extends EntityLiving>) EntityList.getClass(new ResourceLocation("mowziesmobs", "lantern")), 3, 1, 3));
                possibles.add(new Biome.SpawnListEntry((Class<? extends EntityLiving>) EntityList.getClass(new ResourceLocation("mowziesmobs", "naga")), 1, 1, 3));
            }
        }
        if (creatureType == EnumCreatureType.AMBIENT) {
            return possibles;
        } else {
            return super.getPossibleCreatures(creatureType, pos);
        }
    }

    @Override
    @Nonnull
    public Chunk generateChunk(int x, int z) {
        FallingHooks.fallingEnabled = false;
        ChunkPrimer chunkprimer = new ChunkPrimer();

        for (int i = 0; i < this.cachedBlockIDs.length; ++i) {
            IBlockState iblockstate = this.cachedBlockIDs[i];

            if (iblockstate != null) {
                for (int j = 0; j < 16; ++j) {
                    for (int k = 0; k < 16; ++k) {
                        chunkprimer.setBlockState(j, i, k, iblockstate);
                    }
                }
            }
        }

        caveGenerator.generate(this.world, x, z, chunkprimer);

        if (hasRavines) {
            ravineGenerator.generate(this.world, x, z, chunkprimer);
        }

        for (MapGenBase mapgenbase : this.structureGenerators.values()) {
            mapgenbase.generate(this.world, x, z, chunkprimer);
        }

        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        Biome[] abiome = this.world.getBiomeProvider().getBiomes(null, x * 16, z * 16, 16, 16);
        byte[] abyte = chunk.getBiomeArray();

        for (int l = 0; l < abyte.length; ++l) {
            abyte[l] = (byte) Biome.getIdForBiome(abiome[l]);
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int x, int z) {
        net.minecraft.block.BlockFalling.fallInstantly = true;
        int i = x * 16;
        int j = z * 16;
        BlockPos blockpos = new BlockPos(i, 0, j);
        Biome biome = this.world.getBiome(new BlockPos(i + 16, 0, j + 16));
        boolean flag = false;
        this.random.setSeed(this.world.getSeed());
        long k = this.random.nextLong() / 2L * 2L + 1L;
        long l = this.random.nextLong() / 2L * 2L + 1L;
        this.random.setSeed((long) x * k + (long) z * l ^ this.world.getSeed());
        ChunkPos chunkpos = new ChunkPos(x, z);

        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.world, this.random, x, z, flag);

        for (MapGenStructure mapgenstructure : this.structureGenerators.values()) {
            boolean flag1 = mapgenstructure.generateStructure(this.world, this.random, chunkpos);

            if (mapgenstructure instanceof MapGenVillage) {
                flag |= flag1;
            }
        }

        if (this.hasDungeons) {
            if (net.minecraftforge.event.terraingen.TerrainGen.populate(this, this.world, this.random, x, z, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.DUNGEON)) {
                for (int i1 = 0; i1 < 8; ++i1) {
                    (new WorldGenDungeons()).generate(this.world, this.random, blockpos.add(this.random.nextInt(16) + 8, this.random.nextInt(256), this.random.nextInt(16) + 8));
                }
            }
        }

        if (this.hasDecoration) {
            biome.decorate(this.world, this.random, blockpos);
        }

        if (this.hasAnimals) {
            if (net.minecraftforge.event.terraingen.TerrainGen.populate(this, this.world, this.random, x, z, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ANIMALS))
                WorldEntitySpawner.performWorldGenSpawning(this.world, biome, i + 8, j + 8, 16, 16, this.random);
        }

        if (this.hasIce) {
            net.minecraftforge.event.terraingen.TerrainGen.populate(this, this.world, this.random, x, z, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ICE);
        }

        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(false, this, this.world, this.random, x, z, flag);
        net.minecraft.block.BlockFalling.fallInstantly = false;
    }
}
