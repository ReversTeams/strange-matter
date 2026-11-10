package net.reversteam.strangematter.network;

import net.reversteam.strangematter.research.ResearchType;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;

import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public class ResearchGainPacket implements net.minecraft.network.protocol.common.custom.CustomPacketPayload {
 public static final Type<ResearchGainPacket> TYPE = new Type<>(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("strangematter", "research_gain_packet"));
 @Override public Type<ResearchGainPacket> type() { return TYPE; }
    private final String typeId;
    private final int amount;

    public ResearchGainPacket(String typeId, int amount) {
        this.typeId = typeId;
        this.amount = amount;
    }

    public ResearchGainPacket(ResearchType type, int amount) {
        this(type.getName(), amount);
    }

    public ResearchGainPacket(FriendlyByteBuf buf) {
        this.typeId = buf.readUtf();
        this.amount = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(typeId);
        buf.writeInt(amount);
    }

    public void handle(Supplier<IPayloadContext> contextSupplier) {
        IPayloadContext context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (context.flow().isClientbound()) {
                String finalTypeId = typeId;
                int finalAmount = amount;
                net.reversteam.strangematter.client.network.ClientPacketHandlers.handleResearchGain(finalTypeId, finalAmount);
            }
        });

    }
}
