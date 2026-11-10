package net.reversteam.strangematter.network;

import net.reversteam.strangematter.entity.HoverboardEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public class HoverboardJumpPacket implements net.minecraft.network.protocol.common.custom.CustomPacketPayload {
 public static final Type<HoverboardJumpPacket> TYPE = new Type<>(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("strangematter", "hoverboard_jump_packet"));
 @Override public Type<HoverboardJumpPacket> type() { return TYPE; }
    private final int entityId;

    public HoverboardJumpPacket(int entityId) {
        this.entityId = entityId;
    }

    public HoverboardJumpPacket(FriendlyByteBuf buffer) {
        this.entityId = buffer.readInt();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(this.entityId);
    }

    public void handle(Supplier<IPayloadContext> contextSupplier) {
        IPayloadContext context = contextSupplier.get();
        context.enqueueWork(() -> {
            if ((context.player() instanceof net.minecraft.server.level.ServerPlayer sp ? sp : null) != null) {
                // Server side - handle the jump
                var level = (context.player() instanceof net.minecraft.server.level.ServerPlayer sp ? sp : null).level();
                Entity entity = level.getEntity(this.entityId);

                if (entity instanceof HoverboardEntity hoverboard) {
                    // Check if the player is riding this hoverboard
                    if (hoverboard.isVehicle()) {
                        Entity controllingPassenger = hoverboard.getControllingPassenger();
                        if (controllingPassenger == null && !hoverboard.getPassengers().isEmpty()) {
                            controllingPassenger = hoverboard.getPassengers().get(0);
                        }

                        if (controllingPassenger != null && controllingPassenger.getId() == (context.player() instanceof net.minecraft.server.level.ServerPlayer sp ? sp : null).getId()) {
                            // Player is riding, trigger jump
                            hoverboard.setJumpPressed(true);
                        }
                    }
                }
            }
        });

    }
}

