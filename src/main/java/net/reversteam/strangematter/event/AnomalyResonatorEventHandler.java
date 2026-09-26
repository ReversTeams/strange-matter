package net.reversteam.strangematter.event;

import net.reversteam.strangematter.StrangeMatterMod;
import net.reversteam.strangematter.entity.GravityAnomalyEntity;
import net.reversteam.strangematter.entity.BaseAnomalyEntity;
import net.reversteam.strangematter.entity.WarpGateAnomalyEntity;
import net.reversteam.strangematter.item.AnomalyResonatorItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StrangeMatterMod.MODID)
public class AnomalyResonatorEventHandler {
    
    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof BaseAnomalyEntity anomaly) {
            Player player = event.getEntity();
            
            // Check both hands for the anomaly resonator
            ItemStack mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
            ItemStack offHand = player.getItemInHand(InteractionHand.OFF_HAND);
            
            AnomalyResonatorItem resonator = null;
            ItemStack resonatorStack = null;
            
            if (mainHand.getItem() instanceof AnomalyResonatorItem) {
                resonator = (AnomalyResonatorItem) mainHand.getItem();
                resonatorStack = mainHand;
            } else if (offHand.getItem() instanceof AnomalyResonatorItem) {
                resonator = (AnomalyResonatorItem) offHand.getItem();
                resonatorStack = offHand;
            }
            
            if (resonator != null && resonatorStack != null) {
                // Call the resonator's method to sync with this anomaly
                resonator.onInteractWithAnomaly(resonatorStack, player, anomaly);
                event.setCanceled(true); // Prevent default interaction
            }
        }
    }
}
