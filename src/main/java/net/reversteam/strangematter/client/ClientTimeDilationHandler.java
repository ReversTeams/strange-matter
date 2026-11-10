package net.reversteam.strangematter.client;

import net.reversteam.strangematter.TimeDilationData;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@net.neoforged.fml.common.EventBusSubscriber(value = Dist.CLIENT)
public class ClientTimeDilationHandler {

    @SubscribeEvent
    public static void onLivingTick(net.neoforged.neoforge.event.tick.EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof Player player && player == Minecraft.getInstance().player) {
            // Check if player has time dilation slowdown data using static data
            if (TimeDilationData.hasPlayerSlowdownFactor(player.getUUID())) {
                double slowdownFactor = TimeDilationData.getPlayerSlowdownFactor(player.getUUID());

                // Apply slowdown on CLIENT SIDE
                Vec3 currentVelocity = player.getDeltaMovement();

                // Only apply if player has movement
                if (currentVelocity.lengthSqr() > 0.0001) {
                    Vec3 slowedVelocity = currentVelocity.scale(slowdownFactor);
                    player.setDeltaMovement(slowedVelocity);
                }
            }
        }
    }
}

