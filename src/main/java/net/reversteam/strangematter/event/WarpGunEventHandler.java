package net.reversteam.strangematter.event;

import net.reversteam.strangematter.StrangeMatterMod;
import net.reversteam.strangematter.item.WarpGunItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@net.neoforged.fml.common.EventBusSubscriber(modid = StrangeMatterMod.MODID, bus = net.neoforged.fml.common.EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class WarpGunEventHandler {

    @SubscribeEvent
    public static void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = event.getEntity();
        ItemStack stack = player.getMainHandItem();

        // Check if the player is holding a warp gun
        if (stack.getItem() instanceof WarpGunItem) {
            System.out.println("Left click empty detected with warp gun!");
            // Send packet to server to handle left-click
            net.reversteam.strangematter.network.NetworkHandler.INSTANCE.sendToServer(
                new net.reversteam.strangematter.network.WarpGunShootPacket(false)
            );
        }
    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        ItemStack stack = player.getMainHandItem();

        // Check if the player is holding a warp gun
        if (stack.getItem() instanceof WarpGunItem) {
            System.out.println("Left click block detected with warp gun!");
            // Send packet to server to handle left-click
            net.reversteam.strangematter.network.NetworkHandler.INSTANCE.sendToServer(
                new net.reversteam.strangematter.network.WarpGunShootPacket(false)
            );
        }
    }
}
