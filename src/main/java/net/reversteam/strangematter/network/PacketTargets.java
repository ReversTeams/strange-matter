package net.reversteam.strangematter.network;
import java.util.function.*;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
/** Packet recipient adapters for the mod's existing call sites. */
public final class PacketTargets {
 private PacketTargets() {}
 public static final PlayerTarget PLAYER = new PlayerTarget();
 public static final AllTarget ALL = new AllTarget();
 public static final ChunkTarget TRACKING_CHUNK = new ChunkTarget();
 public static final NearTarget NEAR = new NearTarget();
 public static class PlayerTarget {
  public Consumer<CustomPacketPayload> with(Supplier<ServerPlayer> player) { return p -> PacketDistributor.sendToPlayer(player.get(), p); }
 }
 public static class AllTarget {
  public Consumer<CustomPacketPayload> noArg() { return p -> PacketDistributor.sendToAllPlayers(p); }
 }
 public static class ChunkTarget {
  public Consumer<CustomPacketPayload> with(Supplier<LevelChunk> chunk) { return p -> PacketDistributor.sendToPlayersTrackingChunk((net.minecraft.server.level.ServerLevel) chunk.get().getLevel(), chunk.get().getPos(), p); }
 }
 public record TargetPoint(double x, double y, double z, double radius, ResourceKey<Level> dimension) {
  public static Supplier<TargetPoint> p(double x, double y, double z, double radius, ResourceKey<Level> dimension) { return () -> new TargetPoint(x,y,z,radius,dimension); }
 }
 public static class NearTarget {
  public Consumer<CustomPacketPayload> with(Supplier<TargetPoint> point) {
   return payload -> {
    var target = point.get();
    var server = net.neoforged.neoforge.server.ServerLifecycleHooks.getCurrentServer();
    if (server == null) return;
    var level = server.getLevel(target.dimension());
    if (level != null) PacketDistributor.sendToPlayersNear(level, null, target.x(),target.y(),target.z(),target.radius(),payload);
   };
  }
 }
}
