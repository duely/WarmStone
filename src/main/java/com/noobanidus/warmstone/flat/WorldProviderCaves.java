package com.noobanidus.warmstone.flat;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class WorldProviderCaves extends WorldProviderSurface {
    @Override
    protected void init() {
        super.init();

        // TODO: Make this a config
        this.biomeProvider = new BiomeProviderSingle(Biomes.PLAINS);
    }

    @Nullable
    @SideOnly(Side.CLIENT)
    @Override
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
        return null;
    }

    @Override
    public BlockPos getRandomizedSpawnPoint() {
        return super.getRandomizedSpawnPoint();
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        return 0.5f;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isSkyColored() {
        return false;
    }

    @Override
    public int getAverageGroundLevel() {
        return 23;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean doesXZShowFog(int x, int z) {
        return false;
    }

    @Override
    public void getLightmapColors(float partialTicks, float sunBrightness, float skyLight, float blockLight, float[] colors) {
        colors[0] = blockLight * 0.8f;
        colors[1] = blockLight * 0.90f;
        colors[2] = blockLight * 1f;
    }

    @Override
    public boolean isDaytime() {
        return false;
    }

    @Override
    public float getSunBrightnessFactor(float partialTicks) {
        return 0.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float partialTicks) {
        return 0.0F;
    }

    @Override
    public boolean canDoLightning(Chunk chunk) {
        return false;
    }

    @Override
    public boolean canDoRainSnowIce(Chunk chunk) {
        return false;
    }
}
