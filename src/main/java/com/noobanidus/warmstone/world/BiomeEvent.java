package com.noobanidus.warmstone.world;

import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BiomeEvent {
    @SubscribeEvent
    public void onDecorator(OreGenEvent.GenerateMinable event) {
        if (event.getType() == OreGenEvent.GenerateMinable.EventType.GRAVEL) {
            event.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public void onSandDecorate(DecorateBiomeEvent.Decorate event) {
        if (event.getType() == DecorateBiomeEvent.Decorate.EventType.SAND_PASS2)
        {
            event.setResult(Event.Result.DENY);
        }
    }
}
