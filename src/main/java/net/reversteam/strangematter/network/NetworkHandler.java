package net.reversteam.strangematter.network;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import java.util.function.*;
public class NetworkHandler {
 public static final Channel INSTANCE = new Channel();
 public static void registerPayloads(RegisterPayloadHandlersEvent event) {
  PayloadRegistrar registrar = event.registrar("1");
  registrar.playToClient(ResearchSyncPacket.TYPE, codec(ResearchSyncPacket::encode, ResearchSyncPacket::new), (packet, context) -> packet.handle(() -> context));
  registrar.playToClient(ResearchGainPacket.TYPE, codec(ResearchGainPacket::encode, ResearchGainPacket::new), (packet, context) -> packet.handle(() -> context));
  registrar.playToClient(ResearchMachineSyncPacket.TYPE, codec(ResearchMachineSyncPacket::encode, ResearchMachineSyncPacket::new_), (packet, context) -> ResearchMachineSyncPacket.handle(packet, () -> context));
  registrar.playToServer(SpendResearchPointsPacket.TYPE, codec(SpendResearchPointsPacket::encode, SpendResearchPointsPacket::new_), (packet, context) -> SpendResearchPointsPacket.handle(packet, () -> context));
  registrar.playToServer(UpdateMinigameStatesPacket.TYPE, codec(UpdateMinigameStatesPacket::encode, UpdateMinigameStatesPacket::new_), (packet, context) -> UpdateMinigameStatesPacket.handle(packet, () -> context));
  registrar.playToServer(ResearchCompletionPacket.TYPE, codec(ResearchCompletionPacket::encode, ResearchCompletionPacket::new_), (packet, context) -> ResearchCompletionPacket.handle(packet, () -> context));
  registrar.playToServer(MinigameStatePacket.TYPE, codec(MinigameStatePacket::encode, MinigameStatePacket::new_), (packet, context) -> MinigameStatePacket.handle(packet, () -> context));
  registrar.playToServer(RequestResearchMachineStatePacket.TYPE, codec(RequestResearchMachineStatePacket::encode, RequestResearchMachineStatePacket::decode), (packet, context) -> RequestResearchMachineStatePacket.handle(packet, () -> context));
  registrar.playToClient(GravitySyncPacket.TYPE, codec(GravitySyncPacket::encode, GravitySyncPacket::new), (packet, context) -> packet.handle(() -> context));
  registrar.playToClient(TimeDilationSyncPacket.TYPE, codec(TimeDilationSyncPacket::encode, TimeDilationSyncPacket::new), (packet, context) -> packet.handle(() -> context));
  registrar.playToServer(WarpGunShootPacket.TYPE, codec(WarpGunShootPacket::encode, WarpGunShootPacket::new), (packet, context) -> packet.handle(() -> context));
  registrar.playToServer(EjectShardsPacket.TYPE, codec(EjectShardsPacket::encode, EjectShardsPacket::new), (packet, context) -> packet.handle(() -> context));
  registrar.playToClient(EchoVacuumBeamPacket.TYPE, codec(EchoVacuumBeamPacket::encode, EchoVacuumBeamPacket::new), (packet, context) -> packet.handle(() -> context));
  registrar.playToClient(PlayerMorphSyncPacket.TYPE, codec(PlayerMorphSyncPacket::encode, PlayerMorphSyncPacket::decode), (packet, context) -> PlayerMorphSyncPacket.handle(packet, () -> context));
  registrar.playToClient(MobDisguiseSyncPacket.TYPE, codec(MobDisguiseSyncPacket::encode, MobDisguiseSyncPacket::decode), (packet, context) -> packet.handle(() -> context));
  registrar.playToServer(HoverboardJumpPacket.TYPE, codec(HoverboardJumpPacket::encode, HoverboardJumpPacket::new), (packet, context) -> packet.handle(() -> context));
 }
 private static <T> StreamCodec<FriendlyByteBuf,T> codec(BiConsumer<T,FriendlyByteBuf> encoder, Function<FriendlyByteBuf,T> decoder) {
  return StreamCodec.of((buf, value) -> encoder.accept(value, buf), decoder::apply);
 }
 public static class Channel {
  public void sendToServer(CustomPacketPayload payload) { net.neoforged.neoforge.network.PacketDistributor.sendToServer(payload); }
  public void send(Consumer<CustomPacketPayload> target, CustomPacketPayload payload) { target.accept(payload); }
 }
}
