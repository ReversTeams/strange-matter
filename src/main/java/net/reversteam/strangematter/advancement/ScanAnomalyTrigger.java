package net.reversteam.strangematter.advancement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import java.util.Optional;
public class ScanAnomalyTrigger extends SimpleCriterionTrigger<ScanAnomalyTrigger.Instance> {
 @Override public Codec<Instance> codec() { return Instance.CODEC; }
 public void trigger(ServerPlayer player, net.minecraft.world.entity.Entity anomaly) { trigger(player, instance -> instance.filter().isEmpty() || instance.filter().get().equals(net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.getKey(anomaly.getType()).toString())); }
 public record Instance(Optional<ContextAwarePredicate> player, Optional<String> filter) implements SimpleInstance {
  public static final Codec<Instance> CODEC = RecordCodecBuilder.create(i -> i.group(
   EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Instance::player),
   Codec.STRING.optionalFieldOf("anomaly_type").forGetter(Instance::filter)
  ).apply(i, Instance::new));
 }
}
