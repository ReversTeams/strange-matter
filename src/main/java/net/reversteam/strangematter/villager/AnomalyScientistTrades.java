package net.reversteam.strangematter.villager;

import net.reversteam.strangematter.StrangeMatterMod;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@net.neoforged.fml.common.EventBusSubscriber(modid = StrangeMatterMod.MODID, bus = net.neoforged.fml.common.EventBusSubscriber.Bus.GAME)
public class AnomalyScientistTrades {

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() == StrangeMatterMod.ANOMALY_SCIENTIST.get()) {

            // Novice (Level 1) trades
            event.getTrades().get(1).add((trader, random) -> new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(Items.EMERALD, 8), java.util.Optional.empty(),
                new ItemStack(StrangeMatterMod.RAW_RESONITE.get(), 4),
                12, 2, 0.05f
            ));

            event.getTrades().get(1).add((trader, random) -> new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(StrangeMatterMod.RAW_RESONITE.get(), 6), java.util.Optional.empty(),
                new ItemStack(Items.EMERALD, 1),
                16, 2, 0.05f
            ));

            // Apprentice (Level 2) trades
            event.getTrades().get(2).add((trader, random) -> new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(Items.EMERALD, 12), java.util.Optional.empty(),
                new ItemStack(StrangeMatterMod.RESONITE_INGOT.get(), 3),
                12, 5, 0.05f
            ));

            event.getTrades().get(2).add((trader, random) -> new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(StrangeMatterMod.RESONITE_INGOT.get(), 2), java.util.Optional.empty(),
                new ItemStack(Items.EMERALD, 1),
                16, 5, 0.05f
            ));

            event.getTrades().get(2).add((trader, random) -> new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(Items.EMERALD, 5), java.util.Optional.empty(),
                new ItemStack(StrangeMatterMod.RESONITE_NUGGET.get(), 9),
                16, 5, 0.05f
            ));

            // Journeyman (Level 3) trades
            event.getTrades().get(3).add((trader, random) -> new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(Items.EMERALD, 20), java.util.Optional.empty(),
                new ItemStack(StrangeMatterMod.RESONANT_COIL.get(), 1),
                8, 10, 0.05f
            ));

            event.getTrades().get(3).add((trader, random) -> new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(Items.EMERALD, 16), java.util.Optional.empty(),
                new ItemStack(StrangeMatterMod.GRAVITIC_SHARD.get(), 1),
                4, 10, 0.05f
            ));

            event.getTrades().get(3).add((trader, random) -> new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(Items.EMERALD, 16), java.util.Optional.empty(),
                new ItemStack(StrangeMatterMod.SPATIAL_SHARD.get(), 1),
                4, 10, 0.05f
            ));

            // Expert (Level 4) trades
            event.getTrades().get(4).add((trader, random) -> new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(Items.EMERALD, 24), java.util.Optional.empty(),
                new ItemStack(StrangeMatterMod.STABILIZED_CORE.get(), 1),
                6, 15, 0.05f
            ));

            event.getTrades().get(4).add((trader, random) -> new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(Items.EMERALD, 18), java.util.Optional.empty(),
                new ItemStack(StrangeMatterMod.CHRONO_SHARD.get(), 1),
                4, 15, 0.05f
            ));

            event.getTrades().get(4).add((trader, random) -> new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(Items.EMERALD, 18), java.util.Optional.empty(),
                new ItemStack(StrangeMatterMod.ENERGETIC_SHARD.get(), 1),
                4, 15, 0.05f
            ));

            // Master (Level 5) trades
            event.getTrades().get(5).add((trader, random) -> new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(Items.EMERALD, 30), java.util.Optional.empty(),
                new ItemStack(StrangeMatterMod.RESONANT_CIRCUIT.get(), 1),
                4, 30, 0.05f
            ));

            event.getTrades().get(5).add((trader, random) -> new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(Items.EMERALD, 20), java.util.Optional.empty(),
                new ItemStack(StrangeMatterMod.SHADE_SHARD.get(), 1),
                3, 30, 0.05f
            ));

            event.getTrades().get(5).add((trader, random) -> new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(Items.EMERALD, 20), java.util.Optional.empty(),
                new ItemStack(StrangeMatterMod.INSIGHT_SHARD.get(), 1),
                3, 30, 0.05f
            ));

            event.getTrades().get(5).add((trader, random) -> new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(Items.EMERALD, 48), java.util.Optional.empty(),
                new ItemStack(StrangeMatterMod.RESONITE_BLOCK_ITEM.get(), 1),
                2, 30, 0.05f
            ));
        }
    }
}

