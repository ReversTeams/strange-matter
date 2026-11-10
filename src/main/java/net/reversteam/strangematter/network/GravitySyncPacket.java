package net.reversteam.strangematter.network;

import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;

import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public class GravitySyncPacket implements net.minecraft.network.protocol.common.custom.CustomPacketPayload {
 public static final Type<GravitySyncPacket> TYPE = new Type<>(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("strangematter", "gravity_sync_packet"));
 @Override public Type<GravitySyncPacket> type() { return TYPE; }
    private final double gravityForce;

    public GravitySyncPacket(double gravityForce) {
        this.gravityForce = gravityForce;
    }

    public GravitySyncPacket(FriendlyByteBuf buffer) {
        this.gravityForce = buffer.readDouble();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeDouble(this.gravityForce);
    }

    public void handle(Supplier<IPayloadContext> contextSupplier) {
        IPayloadContext context = contextSupplier.get();

        context.enqueueWork(() -> {
            if ((context.player() instanceof net.minecraft.server.level.ServerPlayer sp ? sp : null) != null) {
                // Server-side handling (shouldn't happen with our current setup)
                (context.player() instanceof net.minecraft.server.level.ServerPlayer sp ? sp : null).getPersistentData().putDouble("strangematter.gravity_force", this.gravityForce);
                return;
            }
            if (context.flow().isClientbound()) {
                double force = this.gravityForce;
                net.reversteam.strangematter.client.network.ClientPacketHandlers.handleGravitySync(force);
            }
        });

    }

    public double getGravityForce() {
        return gravityForce;
    }
}
