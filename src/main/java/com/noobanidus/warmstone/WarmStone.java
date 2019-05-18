package com.noobanidus.warmstone;

import com.noobanidus.warmstone.flat.WorldProviderCaves;
import com.noobanidus.warmstone.flat.WorldTypeFlat;
import com.noobanidus.warmstone.world.BiomeEvent;
import com.noobanidus.warmstone.world.GrassGenerator;
import net.minecraft.init.Biomes;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

@Mod(modid = WarmStone.MODID, name = WarmStone.NAME, version = WarmStone.VERSION)
public class WarmStone {
    public static final String MODID = "warmstone";
    public static final String NAME = "Warm Stone";
    public static final String VERSION = "1.0.0";

    @Mod.Instance(MODID)
    public static WarmStone instance;

    public static Logger LOG;

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        LOG = event.getModLog();
        WorldTypeFlat.createFlatWorld();
        BiomeEvent handler = new BiomeEvent();
        MinecraftForge.TERRAIN_GEN_BUS.register(handler);
        MinecraftForge.ORE_GEN_BUS.register(handler);
    }

    @EventHandler
    public static void init(FMLInitializationEvent event) {
        try {
            DimensionManager.unregisterDimension(0);
            DimensionManager.registerDimension(0, DimensionType.register("OVERWORLD_CAVES", "", 0, WorldProviderCaves.class, true));
        } catch (Exception e) {
            LOG.error("Unable to replace overworld provider with overworld_caves provider. Void spawning will now be problematic.");
            e.printStackTrace();
        }

        GameRegistry.registerWorldGenerator(new GrassGenerator(), Integer.MAX_VALUE);

        MapGenVillage.VILLAGE_SPAWN_BIOMES = new ArrayList<>(MapGenVillage.VILLAGE_SPAWN_BIOMES);
        MapGenVillage.VILLAGE_SPAWN_BIOMES.add(Biomes.OCEAN);
    }

    @EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        World world = DimensionManager.getWorld(0);
        world.setSeaLevel(23);
    }

    @EventHandler
    public static void loadComplete(FMLLoadCompleteEvent event) {
    }
}
