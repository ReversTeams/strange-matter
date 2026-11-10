package net.reversteam.strangematter.network;

import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;

import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public class TimeDilationSyncPacket implements net.minecraft.network.protocol.common.custom.CustomPacketPayload {
 public static final Type<TimeDilationSyncPacket> TYPE = new Type<>(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("strangematter", "time_dilation_sync_packet"));
 @Override public Type<TimeDilationSyncPacket> type() { return TYPE; }
    private final double slowdownFactor;

    public TimeDilationSyncPacket(double slowdownFactor) {
        this.slowdownFactor = slowdownFactor;
    }

    public TimeDilationSyncPacket(FriendlyByteBuf buffer) {
        this.slowdownFactor = buffer.readDouble();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeDouble(this.slowdownFactor);
    }

    public void handle(Supplier<IPayloadContext> contextSupplier) {
        IPayloadContext context = contextSupplier.get();

        context.enqueueWork(() -> {
            if (context.flow().isClientbound()) {
                double factor = this.slowdownFactor;
                net.reversteam.strangematter.client.network.ClientPacketHandlers.handleTimeDilationSync(factor);
            }
        });

    }

    public double getSlowdownFactor() {
        return slowdownFactor;
    }
}

