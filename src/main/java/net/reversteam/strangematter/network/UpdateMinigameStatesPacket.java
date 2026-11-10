package net.reversteam.strangematter.network;

import net.reversteam.strangematter.block.ResearchMachineBlockEntity;
import net.reversteam.strangematter.research.ResearchType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class UpdateMinigameStatesPacket implements net.minecraft.network.protocol.common.custom.CustomPacketPayload {
 public static final Type<UpdateMinigameStatesPacket> TYPE = new Type<>(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("strangematter", "update_minigame_states_packet"));
 @Override public Type<UpdateMinigameStatesPacket> type() { return TYPE; }
    private final BlockPos pos;
    private final Map<ResearchType, Boolean> minigameStates;

    public UpdateMinigameStatesPacket(BlockPos pos, Map<ResearchType, Boolean> minigameStates) {
        this.pos = pos;
        this.minigameStates = minigameStates;
    }

    public static void encode(UpdateMinigameStatesPacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeInt(packet.minigameStates.size());
        for (Map.Entry<ResearchType, Boolean> entry : packet.minigameStates.entrySet()) {
            buffer.writeEnum(entry.getKey());
            buffer.writeBoolean(entry.getValue());
        }
    }

    public static UpdateMinigameStatesPacket new_(FriendlyByteBuf buffer) {
        BlockPos pos = buffer.readBlockPos();
        int size = buffer.readInt();
        Map<ResearchType, Boolean> minigameStates = new HashMap<>();
        for (int i = 0; i < size; i++) {
            ResearchType type = buffer.readEnum(ResearchType.class);
            boolean isStable = buffer.readBoolean();
            minigameStates.put(type, isStable);
        }
        return new UpdateMinigameStatesPacket(pos, minigameStates);
    }

    public static void handle(UpdateMinigameStatesPacket packet, Supplier<IPayloadContext> contextSupplier) {
        IPayloadContext context = contextSupplier.get();
        context.enqueueWork(() -> {
            if ((context.player() instanceof net.minecraft.server.level.ServerPlayer sp ? sp : null) != null) {
                // Server side - update the research machine
                var player = (context.player() instanceof net.minecraft.server.level.ServerPlayer sp ? sp : null);
                var level = player.level();
                var blockEntity = level.getBlockEntity(packet.pos);

                if (blockEntity instanceof ResearchMachineBlockEntity researchMachine) {
                    // This packet is no longer used since we moved to client-side instability calculation
                    // Keeping the packet for potential future use
                }
            }
        });

    }
}
