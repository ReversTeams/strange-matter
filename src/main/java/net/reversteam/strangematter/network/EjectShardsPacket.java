package net.reversteam.strangematter.network;

import net.reversteam.strangematter.block.RealityForgeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public class EjectShardsPacket implements net.minecraft.network.protocol.common.custom.CustomPacketPayload {
 public static final Type<EjectShardsPacket> TYPE = new Type<>(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("strangematter", "eject_shards_packet"));
 @Override public Type<EjectShardsPacket> type() { return TYPE; }
    private final BlockPos pos;

    public EjectShardsPacket(BlockPos pos) {
        this.pos = pos;
    }

    public EjectShardsPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public void handle(Supplier<IPayloadContext> contextSupplier) {
        IPayloadContext context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (context.flow().isServerbound()) {
                ServerPlayer player = (context.player() instanceof net.minecraft.server.level.ServerPlayer sp ? sp : null);
                if (player != null) {
                    BlockEntity blockEntity = player.level().getBlockEntity(pos);
                    if (blockEntity instanceof RealityForgeBlockEntity realityForge) {
                        realityForge.ejectShards(player);
                    }
                }
            }
        });

    }
}
