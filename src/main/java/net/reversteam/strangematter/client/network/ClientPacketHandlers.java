package net.reversteam.strangematter.client.network;

import net.reversteam.strangematter.StrangeMatterMod;
import net.reversteam.strangematter.block.ResearchMachineBlockEntity;
import net.reversteam.strangematter.research.ResearchData;
import net.reversteam.strangematter.research.ResearchType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

import java.util.Set;
import java.util.UUID;

/**
 * Client-only handlers for packet application and UI/renderer side effects.
 * Keep all net.minecraft.client.* references isolated here to ensure dedicated server safety.
 */
public final class ClientPacketHandlers {
    private ClientPacketHandlers() {}

    // Research data cache used by the research tablet UI.
    private static ResearchData clientResearchData = new ResearchData();

    public static void handleResearchSync(ResearchData data) {
        clientResearchData = data;
    }

    public static void handleResearchGain(String typeId, int amount) {
        clientResearchData.addResearchPoints(typeId, amount);
        net.reversteam.strangematter.client.ResearchOverlay.showResearchGain(typeId, amount);
    }

    public static ResearchData getClientResearchData() {
        return clientResearchData;
    }

    public static void handleEchoVacuumBeam(int playerId, boolean isActive) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) return;

        var entity = minecraft.level.getEntity(playerId);
        if (entity instanceof Player player) {
            StrangeMatterMod.setPlayerBeamState(player, isActive);
        }
    }

    public static void handleResearchMachineSync(
            BlockPos pos,
            ResearchMachineBlockEntity.MachineState state,
            String researchId,
            Set<ResearchType> activeTypes,
            float instabilityLevel,
            int researchTicks
    ) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) return;

        var blockEntity = minecraft.level.getBlockEntity(pos);
        if (blockEntity instanceof ResearchMachineBlockEntity researchMachine) {
            researchMachine.setClientState(state, researchId, activeTypes, instabilityLevel, researchTicks);
        }

        if (minecraft.screen instanceof net.reversteam.strangematter.client.screen.ResearchMachineScreen screen) {
            screen.handleStateSync(state, researchId, activeTypes, instabilityLevel, researchTicks);
        }
    }

    public static void handleTimeDilationSync(double slowdownFactor) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null) return;

        player.getPersistentData().putDouble("strangematter.time_dilation_factor", slowdownFactor);
        net.reversteam.strangematter.TimeDilationData.setPlayerSlowdownFactor(player.getUUID(), slowdownFactor);
    }

    public static void handleGravitySync(double gravityForce) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null) return;

        player.getPersistentData().putDouble("strangematter.gravity_force", gravityForce);
    }

    public static void handleMobDisguiseSync(UUID mobUUID, boolean clearDisguise, String disguiseType, int disguiseDuration) {
        if (clearDisguise) {
            net.reversteam.strangematter.entity.ThoughtwellEntity.removeDisguise(mobUUID);
            net.reversteam.strangematter.client.CognitiveDisguiseRenderer.cleanupDisguise(mobUUID);
        } else {
            net.reversteam.strangematter.entity.ThoughtwellEntity.setDisguise(mobUUID, disguiseType, disguiseDuration);
        }
    }

    public static void cleanupMorphEntity(UUID playerUUID) {
        net.reversteam.strangematter.client.PlayerMorphRenderer.cleanupMorphEntity(playerUUID);
    }

    public static void openResearchTabletScreen() {
        Minecraft.getInstance().setScreen(new net.reversteam.strangematter.client.screen.ResearchTabletScreen());
    }
}
