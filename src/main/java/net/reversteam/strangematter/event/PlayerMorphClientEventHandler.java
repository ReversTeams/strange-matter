package net.reversteam.strangematter.event;

import net.reversteam.strangematter.client.PlayerMorphRenderer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

/**
 * Handles client-side player morph rendering cleanup
 */
@net.neoforged.fml.common.EventBusSubscriber(modid = "strangematter", value = Dist.CLIENT)
public class PlayerMorphClientEventHandler {

    /**
     * Clear client-side cached morph entities when player changes dimension/respawns
     */
    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) {
            PlayerMorphRenderer.cleanupMorphEntity(player.getUUID());
        }
    }

    /**
     * Clear client-side cached morph entities on respawn
     */
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) {
            PlayerMorphRenderer.cleanupMorphEntity(player.getUUID());
        }
    }
}


