package net.reversteam.strangematter.client;

import net.reversteam.strangematter.entity.HoverboardEntity;
import net.reversteam.strangematter.network.HoverboardJumpPacket;
import net.reversteam.strangematter.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@net.neoforged.fml.common.EventBusSubscriber(value = Dist.CLIENT)
public class HoverboardInputHandler {
    private static boolean wasJumpPressed = false;

    @SubscribeEvent
    public static void onClientTick(net.neoforged.neoforge.client.event.ClientTickEvent.Pre event) {

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        if (player != null && player.isPassenger()) {
            // Check if player is riding a hoverboard
            if (player.getVehicle() instanceof HoverboardEntity hoverboard) {
                // Check if jump key is pressed
                boolean jumpPressed = mc.options.keyJump.isDown();

                // Only send packet on the rising edge (when key is first pressed)
                // This prevents continuous jumping while the key is held
                if (jumpPressed && !wasJumpPressed) {
                    // Key was just pressed - send jump packet to server
                    HoverboardJumpPacket packet = new HoverboardJumpPacket(hoverboard.getId());
                    NetworkHandler.INSTANCE.sendToServer(packet);
                }

                wasJumpPressed = jumpPressed;
            } else {
                wasJumpPressed = false;
            }
        } else {
            wasJumpPressed = false;
        }
    }
}

