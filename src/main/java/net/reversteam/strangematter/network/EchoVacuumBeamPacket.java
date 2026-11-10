package net.reversteam.strangematter.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.reversteam.strangematter.network.PacketTargets;
import net.reversteam.strangematter.StrangeMatterMod;

import java.util.function.Supplier;

public class EchoVacuumBeamPacket implements net.minecraft.network.protocol.common.custom.CustomPacketPayload {
 public static final Type<EchoVacuumBeamPacket> TYPE = new Type<>(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("strangematter", "echo_vacuum_beam_packet"));
 @Override public Type<EchoVacuumBeamPacket> type() { return TYPE; }
    private final int playerId;
    private final boolean isActive;

    public EchoVacuumBeamPacket(int playerId, boolean isActive) {
        this.playerId = playerId;
        this.isActive = isActive;
    }

    public EchoVacuumBeamPacket(FriendlyByteBuf buffer) {
        this.playerId = buffer.readInt();
        this.isActive = buffer.readBoolean();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(this.playerId);
        buffer.writeBoolean(this.isActive);
    }

    public boolean handle(Supplier<IPayloadContext> context) {
        context.get().enqueueWork(() -> {
            if (!context.get().flow().isClientbound()) return;

            int id = this.playerId;
            boolean active = this.isActive;
            net.reversteam.strangematter.client.network.ClientPacketHandlers.handleEchoVacuumBeam(id, active)
            ;
        });
        return true;
    }

    public static void sendToNearbyPlayers(Player sourcePlayer, boolean isActive) {
        Level level = sourcePlayer.level();
        if (!level.isClientSide) {
            EchoVacuumBeamPacket packet = new EchoVacuumBeamPacket(sourcePlayer.getId(), isActive);
            NetworkHandler.INSTANCE.send(
                PacketTargets.NEAR.with(
                    PacketTargets.TargetPoint.p(
                        sourcePlayer.getX(), sourcePlayer.getY(), sourcePlayer.getZ(),
                        64.0, // 64 block radius
                        level.dimension()
                    )
                ),
                packet
            );
        }
    }
}
