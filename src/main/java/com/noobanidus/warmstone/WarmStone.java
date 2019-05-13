package com.noobanidus.warmstone;

import com.noobanidus.warmstone.world.GrassGenerator;
import com.noobanidus.warmstone.world.GravelStopper;
import net.minecraft.init.Biomes;
import net.minecraft.world.gen.structure.MapGenVillage;
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
    }

    @EventHandler
    public static void init(FMLInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new GravelStopper(), Integer.MAX_VALUE - 1);
        GameRegistry.registerWorldGenerator(new GrassGenerator(), Integer.MAX_VALUE);

        MapGenVillage.VILLAGE_SPAWN_BIOMES = new ArrayList<>(MapGenVillage.VILLAGE_SPAWN_BIOMES);
        MapGenVillage.VILLAGE_SPAWN_BIOMES.add(Biomes.OCEAN);
    }

    @EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
    }

    @EventHandler
    public static void serverStarted(FMLServerStartedEvent event) {
    }

    @EventHandler
    public static void loadComplete(FMLLoadCompleteEvent event) {
    }
}
