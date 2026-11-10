package net.reversteam.strangematter.network;

import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;

import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * Packet to sync mob cognitive disguise data from server to client
 */
public class MobDisguiseSyncPacket implements net.minecraft.network.protocol.common.custom.CustomPacketPayload {
 public static final Type<MobDisguiseSyncPacket> TYPE = new Type<>(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("strangematter", "mob_disguise_sync_packet"));
 @Override public Type<MobDisguiseSyncPacket> type() { return TYPE; }
    private final UUID mobUUID;
    private final String disguiseType;
    private final int disguiseDuration;
    private final boolean clearDisguise;

    public MobDisguiseSyncPacket(UUID mobUUID, String disguiseType, int disguiseDuration) {
        this.mobUUID = mobUUID;
        this.disguiseType = disguiseType;
        this.disguiseDuration = disguiseDuration;
        this.clearDisguise = false;
    }

    public MobDisguiseSyncPacket(UUID mobUUID) {
        this.mobUUID = mobUUID;
        this.disguiseType = "";
        this.disguiseDuration = 0;
        this.clearDisguise = true;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(mobUUID);
        buf.writeBoolean(clearDisguise);
        if (!clearDisguise) {
            buf.writeUtf(disguiseType);
            buf.writeInt(disguiseDuration);
        }
    }

    public static MobDisguiseSyncPacket decode(FriendlyByteBuf buf) {
        UUID mobUUID = buf.readUUID();
        boolean clearDisguise = buf.readBoolean();
        if (clearDisguise) {
            return new MobDisguiseSyncPacket(mobUUID);
        } else {
            String disguiseType = buf.readUtf();
            int disguiseDuration = buf.readInt();
            return new MobDisguiseSyncPacket(mobUUID, disguiseType, disguiseDuration);
        }
    }

    public void handle(Supplier<IPayloadContext> contextSupplier) {
        IPayloadContext context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (!context.flow().isClientbound()) return;

            UUID id = mobUUID;
            boolean clear = clearDisguise;
            String type = disguiseType;
            int duration = disguiseDuration;

            net.reversteam.strangematter.client.network.ClientPacketHandlers.handleMobDisguiseSync(id, clear, type, duration)
            ;
        });

    }
}

