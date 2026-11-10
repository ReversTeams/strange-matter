package net.reversteam.strangematter.block;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.core.BlockPos;

import java.util.List;
import java.util.Collections;
import javax.annotation.Nonnull;

/**
 * Base class for all shard ore blocks in the Strange Matter mod.
 * Provides common functionality for ore blocks that drop shards.
 */
public abstract class ShardOreBlock extends Block {

    public ShardOreBlock() {
        super(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_PURPLE)
            .strength(3.0f, 3.0f)
            .pushReaction(PushReaction.DESTROY)
            .requiresCorrectToolForDrops()
        );
    }


    @Override
    public SoundType getSoundType(@Nonnull BlockState state) {
        return SoundType.STONE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(@Nonnull BlockState state, @Nonnull LootParams.Builder builder) {
        // Make loot tables authoritative, but enforce "correct tool for drops" using the tool from loot context.
        // This keeps iron-tier gating via block tags (`needs_iron_tool`) while supporting modded tools that
        // correctly implement harvest logic but are not vanilla `TieredItem` (e.g., Tinkers tools).
        ItemStack tool = builder.getOptionalParameter(LootContextParams.TOOL);
        if (tool == null) tool = ItemStack.EMPTY;

        if (state.requiresCorrectToolForDrops() && !tool.isCorrectToolForDrops(state)) {
            return Collections.emptyList();
        }

        return super.getDrops(state, builder);
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.level.LevelAccessor level, BlockPos pos, net.minecraft.world.level.block.entity.BlockEntity blockEntity, net.minecraft.world.entity.Entity breaker, ItemStack tool) {
        // Drop experience like coal ore (0-2 experience)
        return tool.getEnchantmentLevel(level.registryAccess().lookupOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT).getOrThrow(net.minecraft.world.item.enchantment.Enchantments.SILK_TOUCH)) == 0 ? level.getRandom().nextInt(3) : 0;
    }
}
