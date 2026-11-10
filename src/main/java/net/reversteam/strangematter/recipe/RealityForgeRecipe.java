package net.reversteam.strangematter.recipe;

import com.google.gson.JsonObject;
import net.reversteam.strangematter.block.RealityForgeBlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class RealityForgeRecipe implements Recipe<RealityForgeRecipe.Input> {

    private final ResourceLocation id;
    private final ItemStack result;
    private final NonNullList<Ingredient> ingredients;
    private final Map<String, Integer> shardRequirements;
    private final String requiredResearch;

    public RealityForgeRecipe(ResourceLocation id, ItemStack result, NonNullList<Ingredient> ingredients, Map<String, Integer> shardRequirements, String requiredResearch) {
        this.id = id;
        this.result = result;
        this.ingredients = ingredients;
        this.shardRequirements = shardRequirements;
        this.requiredResearch = requiredResearch;
    }

    @Override
    public boolean matches(Input input, Level level) {
        Container container = input.machine();
        // Check if the crafting grid matches
        for (int i = 0; i < 9; i++) {
            if (!ingredients.get(i).test(container.getItem(i))) {
                return false;
            }
        }

        // Check shard requirements
        if (container instanceof RealityForgeBlockEntity realityForge) {
            Map<String, Integer> storedShards = realityForge.getStoredShards();
            for (Map.Entry<String, Integer> requirement : shardRequirements.entrySet()) {
                String shardType = requirement.getKey();
                int required = requirement.getValue();
                int available = storedShards.getOrDefault(shardType, 0);
                if (available < required) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Check if the recipe can be crafted by a specific player
     */
    public boolean canCraftByPlayer(Player player) {
        // If no research requirement, anyone can craft
        if (requiredResearch == null || requiredResearch.isEmpty()) {
            return true;
        }

        // Check if player has unlocked the required research
        net.reversteam.strangematter.research.ResearchData researchData = net.reversteam.strangematter.research.ResearchData.get(player);
        return researchData.hasUnlockedResearch(requiredResearch);
    }

    @Override
    public ItemStack assemble(Input container, net.minecraft.core.HolderLookup.Provider registryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public ItemStack getResultItem(net.minecraft.core.HolderLookup.Provider registryAccess) {
        return result;
    }

    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return net.reversteam.strangematter.StrangeMatterMod.REALITY_FORGE_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return net.reversteam.strangematter.StrangeMatterMod.REALITY_FORGE_RECIPE_TYPE.get();
    }

    public Map<String, Integer> getShardRequirements() {
        return new HashMap<>(shardRequirements);
    }

    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getRequiredResearch() {
        return requiredResearch;
    }

    public record Input(RealityForgeBlockEntity machine) implements RecipeInput {
        public ItemStack getItem(int slot) { return machine.getItem(slot); }
        public int size() { return 9; }
    }
    public boolean matches(RealityForgeBlockEntity machine, Level level) { return matches(new Input(machine), level); }
    public static class Serializer implements RecipeSerializer<RealityForgeRecipe> {
        private record Data(ItemStack result, java.util.List<String> pattern, Map<String,Ingredient> key, Map<String,Integer> shards, String research) {
            static final com.mojang.serialization.MapCodec<Data> CODEC = com.mojang.serialization.codecs.RecordCodecBuilder.mapCodec(i -> i.group(
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(Data::result),
                com.mojang.serialization.Codec.STRING.listOf().fieldOf("pattern").forGetter(Data::pattern),
                com.mojang.serialization.Codec.unboundedMap(com.mojang.serialization.Codec.STRING, Ingredient.CODEC_NONEMPTY).fieldOf("key").forGetter(Data::key),
                com.mojang.serialization.Codec.unboundedMap(com.mojang.serialization.Codec.STRING, com.mojang.serialization.Codec.INT).optionalFieldOf("shards", Map.of()).forGetter(Data::shards),
                com.mojang.serialization.Codec.STRING.optionalFieldOf("required_research", "").forGetter(Data::research)
            ).apply(i,Data::new));
            RealityForgeRecipe recipe() {
                if(pattern.size()!=3 || pattern.stream().anyMatch(row -> row.length()!=3)) throw new IllegalArgumentException("Reality Forge pattern must be 3x3");
                var ingredients = NonNullList.withSize(9,Ingredient.EMPTY);
                for(int row=0;row<3;row++) for(int col=0;col<3;col++) {
                    char c=pattern.get(row).charAt(col);
                    if(c!=' ') {
                        Ingredient ingredient=key.get(String.valueOf(c));
                        if(ingredient==null) throw new IllegalArgumentException("Missing pattern key: "+c);
                        ingredients.set(row*3+col,ingredient);
                    }
                }
                return new RealityForgeRecipe(net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(result.getItem()),result,ingredients,shards,research);
            }
            static Data from(RealityForgeRecipe recipe) {
                var pattern=new java.util.ArrayList<String>();var key=new java.util.HashMap<String,Ingredient>();
                for(int row=0;row<3;row++) { var line=new StringBuilder();for(int col=0;col<3;col++) {
                    int slot=row*3+col;var ingredient=recipe.ingredients.get(slot);char c=ingredient.isEmpty()?' ':(char)('A'+slot);line.append(c);
                    if(c!=' ')key.put(String.valueOf(c),ingredient);
                } pattern.add(line.toString()); }
                return new Data(recipe.result,pattern,key,recipe.shardRequirements,recipe.requiredResearch==null?"":recipe.requiredResearch);
            }
        }
        public static final com.mojang.serialization.MapCodec<RealityForgeRecipe> CODEC=Data.CODEC.xmap(Data::recipe,Data::from);
        @Override public com.mojang.serialization.MapCodec<RealityForgeRecipe> codec() { return CODEC; }
        @Override public net.minecraft.network.codec.StreamCodec<net.minecraft.network.RegistryFriendlyByteBuf,RealityForgeRecipe> streamCodec() {
            return net.minecraft.network.codec.ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());
        }
    }
}
