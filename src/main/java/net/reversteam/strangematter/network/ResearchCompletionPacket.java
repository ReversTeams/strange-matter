package net.reversteam.strangematter.network;

import net.reversteam.strangematter.block.ResearchMachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public class ResearchCompletionPacket implements net.minecraft.network.protocol.common.custom.CustomPacketPayload {
 public static final Type<ResearchCompletionPacket> TYPE = new Type<>(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("strangematter", "research_completion_packet"));
 @Override public Type<ResearchCompletionPacket> type() { return TYPE; }
    private final BlockPos pos;
    private final boolean success;

    public ResearchCompletionPacket(BlockPos pos, boolean success) {
        this.pos = pos;
        this.success = success;
    }

    public static void encode(ResearchCompletionPacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeBoolean(packet.success);
    }

    public static ResearchCompletionPacket new_(FriendlyByteBuf buffer) {
        BlockPos pos = buffer.readBlockPos();
        boolean success = buffer.readBoolean();
        return new ResearchCompletionPacket(pos, success);
    }

    public static void handle(ResearchCompletionPacket packet, Supplier<IPayloadContext> contextSupplier) {
        IPayloadContext context = contextSupplier.get();
        context.enqueueWork(() -> {
            if ((context.player() instanceof net.minecraft.server.level.ServerPlayer sp ? sp : null) != null) {
                // Server side - handle research completion/failure
                var player = (context.player() instanceof net.minecraft.server.level.ServerPlayer sp ? sp : null);
                var level = player.level();
                var blockEntity = level.getBlockEntity(packet.pos);

                if (blockEntity instanceof ResearchMachineBlockEntity researchMachine) {
                    if (packet.success) {
                        researchMachine.handleResearchCompletion();
                    } else {
                        researchMachine.handleResearchFailure();
                    }
                }
            }
        });

    }
}
