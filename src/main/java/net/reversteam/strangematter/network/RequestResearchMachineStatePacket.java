package net.reversteam.strangematter.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public class RequestResearchMachineStatePacket implements net.minecraft.network.protocol.common.custom.CustomPacketPayload {
 public static final Type<RequestResearchMachineStatePacket> TYPE = new Type<>(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("strangematter", "request_research_machine_state_packet"));
 @Override public Type<RequestResearchMachineStatePacket> type() { return TYPE; }
    private final BlockPos pos;

    public RequestResearchMachineStatePacket(BlockPos pos) {
        this.pos = pos;
    }

    public static void encode(RequestResearchMachineStatePacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
    }

    public static RequestResearchMachineStatePacket decode(FriendlyByteBuf buffer) {
        BlockPos pos = buffer.readBlockPos();
        return new RequestResearchMachineStatePacket(pos);
    }

    public static void handle(RequestResearchMachineStatePacket packet, Supplier<IPayloadContext> contextSupplier) {
        IPayloadContext context = contextSupplier.get();
        context.enqueueWork(() -> {
            if ((context.player() instanceof net.minecraft.server.level.ServerPlayer sp ? sp : null) != null) {
                // Server side - send current state back to client
                var player = (context.player() instanceof net.minecraft.server.level.ServerPlayer sp ? sp : null);
                var level = player.level();
                var blockEntity = level.getBlockEntity(packet.pos);

                if (blockEntity instanceof net.reversteam.strangematter.block.ResearchMachineBlockEntity researchMachine) {
                    // Trigger a sync packet to be sent to all players tracking this chunk
                    // This will update the client-side BlockEntity with the latest server state
                    researchMachine.sendSyncPacket();
                }
            }
        });

    }
}
