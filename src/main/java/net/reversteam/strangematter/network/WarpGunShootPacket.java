package net.reversteam.strangematter.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.reversteam.strangematter.item.WarpGunItem;

import java.util.function.Supplier;

public class WarpGunShootPacket implements net.minecraft.network.protocol.common.custom.CustomPacketPayload {
 public static final Type<WarpGunShootPacket> TYPE = new Type<>(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("strangematter", "warp_gun_shoot_packet"));
 @Override public Type<WarpGunShootPacket> type() { return TYPE; }
    private final boolean isPurple;

    public WarpGunShootPacket(boolean isPurple) {
        this.isPurple = isPurple;
    }

    public WarpGunShootPacket(FriendlyByteBuf buffer) {
        this.isPurple = buffer.readBoolean();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.isPurple);
    }

    public boolean handle(Supplier<IPayloadContext> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = (context.get().player() instanceof net.minecraft.server.level.ServerPlayer sp ? sp : null);
            if (player != null && player.getMainHandItem().getItem() instanceof WarpGunItem) {
                WarpGunItem.shootProjectileStatic(player, this.isPurple);
            }
        });
        return true;
    }
}
