package net.reversteam.strangematter.advancement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import java.util.Optional;
public class CompleteResearchCategoryTrigger extends SimpleCriterionTrigger<CompleteResearchCategoryTrigger.Instance> {
 @Override public Codec<Instance> codec() { return Instance.CODEC; }
 public void trigger(ServerPlayer player, String category) { trigger(player, instance -> instance.filter().isEmpty() || instance.filter().get().equals(category)); }
 public record Instance(Optional<ContextAwarePredicate> player, Optional<String> filter) implements SimpleInstance {
  public static final Codec<Instance> CODEC = RecordCodecBuilder.create(i -> i.group(
   EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Instance::player),
   Codec.STRING.optionalFieldOf("research_category").forGetter(Instance::filter)
  ).apply(i, Instance::new));
 }
}
