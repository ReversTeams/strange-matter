package net.reversteam.strangematter.network;

import net.reversteam.strangematter.research.ResearchData;
import net.reversteam.strangematter.research.ResearchType;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;

import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public class ResearchSyncPacket implements net.minecraft.network.protocol.common.custom.CustomPacketPayload {
 public static final Type<ResearchSyncPacket> TYPE = new Type<>(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("strangematter", "research_sync_packet"));
 @Override public Type<ResearchSyncPacket> type() { return TYPE; }
    private final ResearchData researchData;

    public ResearchSyncPacket(ResearchData researchData) {
        this.researchData = researchData;
    }

    public ResearchSyncPacket(FriendlyByteBuf buf) {
        this.researchData = new ResearchData();
        this.researchData.deserializeNBT(buf.readNbt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeNbt(researchData.serializeNBT());
    }

    public void handle(Supplier<IPayloadContext> contextSupplier) {
        IPayloadContext context = contextSupplier.get();
        context.enqueueWork(() -> {
            // This packet is handled on the client side
            if (context.flow().isClientbound()) {
                ResearchData finalData = researchData;
                net.reversteam.strangematter.client.network.ClientPacketHandlers.handleResearchSync(finalData);
            }
        });

    }
}
