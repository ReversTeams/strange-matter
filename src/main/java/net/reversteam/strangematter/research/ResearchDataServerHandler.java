package net.reversteam.strangematter.research;

import net.reversteam.strangematter.network.NetworkHandler;
import net.reversteam.strangematter.network.ResearchSyncPacket;
import net.minecraft.server.level.ServerPlayer;

public class ResearchDataServerHandler {
    public static void syncResearchDataToClient(ServerPlayer player, ResearchData data) {
        NetworkHandler.INSTANCE.send(
            net.minecraftforge.network.PacketDistributor.PLAYER.with(() -> player), 
            new ResearchSyncPacket(data)
        );
    }
}
