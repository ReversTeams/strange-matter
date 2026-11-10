package net.reversteam.strangematter.advancement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import java.util.Optional;
public class AnomalyEffectTrigger extends SimpleCriterionTrigger<AnomalyEffectTrigger.Instance> {
 @Override public Codec<Instance> codec() { return Instance.CODEC; }
 public void trigger(ServerPlayer player) { trigger(player, instance -> true); }
 public record Instance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
  public static final Codec<Instance> CODEC = RecordCodecBuilder.create(i -> i.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Instance::player)).apply(i, Instance::new));
 }
}
