package net.reversteam.strangematter.client;

import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.reversteam.strangematter.StrangeMatterMod;

@net.neoforged.fml.common.EventBusSubscriber(modid = StrangeMatterMod.MODID, bus = net.neoforged.fml.common.EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ResearchOverlayEventHandler {

    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiEvent.Post event) {
        GuiGraphics guiGraphics = event.getGuiGraphics();
        float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(false);

        // Render our research gain overlay
        ResearchOverlay.render(guiGraphics, partialTick);
    }

}
