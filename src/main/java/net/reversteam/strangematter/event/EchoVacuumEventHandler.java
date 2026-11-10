package net.reversteam.strangematter.event;

import net.reversteam.strangematter.item.EchoVacuumItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

/**
 * Event handler for Echo Vacuum item - handles server-side tick logic
 */
@net.neoforged.fml.common.EventBusSubscriber(modid = "strangematter")
public class EchoVacuumEventHandler {

    @SubscribeEvent
    public static void onPlayerTick(net.neoforged.neoforge.event.tick.PlayerTickEvent.Post event) {

        Player player = event.getEntity();
        Level level = player.level();

        // Only run on server side
        if (level.isClientSide) return;

        // Call the item's static tick handler
        EchoVacuumItem.handlePlayerTick(player, level);
    }
}


