package net.reversteam.strangematter;

import net.reversteam.strangematter.entity.GravityAnomalyEntity;
import net.reversteam.strangematter.entity.EchoingShadowEntity;
import net.reversteam.strangematter.entity.WarpGateAnomalyEntity;
import net.reversteam.strangematter.entity.ThoughtwellEntity;
import net.reversteam.strangematter.entity.ThrowableContainmentCapsuleEntity;
import net.reversteam.strangematter.command.AnomalyCommand;
import net.reversteam.strangematter.command.ResearchCommand;
import net.reversteam.strangematter.sound.StrangeMatterSounds;
import net.reversteam.strangematter.block.AnomalousGrassBlock;
import net.reversteam.strangematter.block.ResoniteOreBlock;
import net.reversteam.strangematter.block.ResoniteBlock;
import net.reversteam.strangematter.block.ResoniteTileBlock;
import net.reversteam.strangematter.block.ResoniteTileStairsBlock;
import net.reversteam.strangematter.block.ResoniteTileSlabBlock;
import net.reversteam.strangematter.block.FancyResoniteTileBlock;
import net.reversteam.strangematter.block.ResonitePillarBlock;
import net.reversteam.strangematter.block.ResoniteDoorBlock;
import net.reversteam.strangematter.block.ResoniteTrapdoorBlock;
import net.reversteam.strangematter.block.GraviticShardOreBlock;
import net.reversteam.strangematter.block.ChronoShardOreBlock;
import net.reversteam.strangematter.block.SpatialShardOreBlock;
import net.reversteam.strangematter.block.ShadeShardOreBlock;
import net.reversteam.strangematter.block.InsightShardOreBlock;
import net.reversteam.strangematter.block.EnergeticShardOreBlock;
import net.reversteam.strangematter.block.ResearchMachineBlock;
import net.reversteam.strangematter.block.ResearchMachineBlockEntity;
import net.reversteam.strangematter.block.ResonanceCondenserBlock;
import net.reversteam.strangematter.block.ResonanceCondenserBlockEntity;
import net.reversteam.strangematter.item.AnomalousGrassItem;
import net.reversteam.strangematter.item.AnomalyResonatorItem;
import net.reversteam.strangematter.item.EchoformImprinterItem;
import net.reversteam.strangematter.item.FieldScannerItem;
import net.reversteam.strangematter.item.RawResoniteItem;
import net.reversteam.strangematter.item.ResoniteIngotItem;
import net.reversteam.strangematter.item.ResoniteNuggetItem;
import net.reversteam.strangematter.item.ResonantCoilItem;
import net.reversteam.strangematter.item.StabilizedCoreItem;
import net.reversteam.strangematter.item.ResonantCircuitItem;
import net.reversteam.strangematter.item.ResonanceCondenserItem;
import net.reversteam.strangematter.item.ResearchTabletItem;
import net.reversteam.strangematter.item.GraviticShardItem;
import net.reversteam.strangematter.item.ChronoShardItem;
import net.reversteam.strangematter.item.SpatialShardItem;
import net.reversteam.strangematter.item.ShadeShardItem;
import net.reversteam.strangematter.item.InsightShardItem;
import net.reversteam.strangematter.item.EnergeticShardItem;
import net.reversteam.strangematter.item.WarpGunItem;
import net.reversteam.strangematter.item.EchoVacuumItem;
import net.reversteam.strangematter.item.ContainmentCapsuleItem;
import net.reversteam.strangematter.item.GravitonHammerItem;
import net.reversteam.strangematter.item.ChronoBlisterItem;
import net.reversteam.strangematter.item.TinfoilHatItem;
import net.reversteam.strangematter.block.LevitationPadBlock;
import net.reversteam.strangematter.block.LevitationPadBlockEntity;
import net.reversteam.strangematter.block.TimeDilationBlock;
import net.reversteam.strangematter.block.TimeDilationBlockEntity;
import net.reversteam.strangematter.network.EchoVacuumBeamPacket;
import net.reversteam.strangematter.entity.WarpProjectileEntity;
import net.reversteam.strangematter.entity.MiniWarpGateEntity;
import net.reversteam.strangematter.entity.ChronoBlisterProjectileEntity;
import net.reversteam.strangematter.item.HoverboardItem;
import net.reversteam.strangematter.entity.HoverboardEntity;
import net.reversteam.strangematter.worldgen.GravityAnomalyConfiguredFeature;
import net.reversteam.strangematter.worldgen.EchoingShadowConfiguredFeature;
import net.reversteam.strangematter.worldgen.ThoughtwellConfiguredFeature;
import net.reversteam.strangematter.worldgen.WarpGateAnomalyConfiguredFeature;
import net.reversteam.strangematter.network.NetworkHandler;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import com.mojang.serialization.Codec;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(StrangeMatterMod.MODID)
public class StrangeMatterMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "strangematter";
    
    // Attribute modifier ID for low gravity effect
    public static final java.util.UUID LOW_GRAVITY_MODIFIER_ID = java.util.UUID.fromString("12345678-1234-1234-1234-123456789abc");
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "strangematter" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "strangematter" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "strangematter" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    // Create a Deferred Register to hold EntityTypes
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);
    // Create a Deferred Register to hold BlockEntityTypes
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    // Create a Deferred Register to hold Attributes
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MODID);
    // Create a Deferred Register to hold Features
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, MODID);
    // Create a Deferred Register to hold PlacementModifierTypes
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, MODID);
    
    // Register custom placement modifiers for config-driven world generation
    public static final RegistryObject<PlacementModifierType<net.reversteam.strangematter.worldgen.ConfiguredRarityFilter>> CONFIGURED_RARITY_FILTER =
        PLACEMENT_MODIFIERS.register("configured_rarity_filter", 
            () -> () -> net.reversteam.strangematter.worldgen.ConfiguredRarityFilter.CODEC);
    
    public static final RegistryObject<PlacementModifierType<net.reversteam.strangematter.worldgen.ConfiguredCountPlacement>> CONFIGURED_COUNT_PLACEMENT =
        PLACEMENT_MODIFIERS.register("configured_count_placement", 
            () -> () -> net.reversteam.strangematter.worldgen.ConfiguredCountPlacement.CODEC);
    // Create a Deferred Register to hold StructureTypes
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, MODID);
    
    // Register Structure Types
    public static final RegistryObject<StructureType<net.reversteam.strangematter.worldgen.WarpGateAnomalyStructure>> WARP_GATE_ANOMALY_STRUCTURE = 
        STRUCTURE_TYPES.register("warp_gate_anomaly", () -> () -> net.reversteam.strangematter.worldgen.WarpGateAnomalyStructure.CODEC);
    // Create a Deferred Register to hold ParticleTypes
    public static final DeferredRegister<net.minecraft.core.particles.ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MODID);
    // Create a Deferred Register to hold MenuTypes
    public static final DeferredRegister<net.minecraft.world.inventory.MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);
    // Create a Deferred Register to hold RecipeTypes
    public static final DeferredRegister<net.minecraft.world.item.crafting.RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, MODID);
    // Create a Deferred Register to hold RecipeSerializers
    public static final DeferredRegister<net.minecraft.world.item.crafting.RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);
    // Create a Deferred Register to hold BiomeModifier Codecs
    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(Keys.BIOME_MODIFIER_SERIALIZERS, MODID);
    // Create a Deferred Register to hold Villager Professions
    public static final DeferredRegister<net.minecraft.world.entity.npc.VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, MODID);
    // Create a Deferred Register to hold POI Types
    public static final DeferredRegister<net.minecraft.world.entity.ai.village.poi.PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, MODID);

    // Register the configurable biome modifier codec
    public static final RegistryObject<Codec<net.reversteam.strangematter.worldgen.ConfiguredBiomeModifier>> CONFIGURED_BIOME_MODIFIER = 
        BIOME_MODIFIER_SERIALIZERS.register("configured_biome_modifier", () -> net.reversteam.strangematter.worldgen.ConfiguredBiomeModifier.CODEC);
    

    // Creates a new research item with the id "strangematter:field_scanner"
    public static final RegistryObject<Item> FIELD_SCANNER = ITEMS.register("field_scanner", FieldScannerItem::new);
    
    
    // Anomaly Resonator - compass for finding anomalies
    public static final RegistryObject<Item> ANOMALY_RESONATOR = ITEMS.register("anomaly_resonator", AnomalyResonatorItem::new);
    
    // Echoform Imprinter - scanner for morphing into mobs
    public static final RegistryObject<Item> ECHOFORM_IMPRINTER = ITEMS.register("echoform_imprinter", EchoformImprinterItem::new);
    
    // Research Notes - basic research item
    public static final RegistryObject<Item> RESEARCH_NOTES = ITEMS.register("research_notes", () -> new net.reversteam.strangematter.item.ResearchNoteItem(new Item.Properties()));

    // Anomalous Grass Block
    public static final RegistryObject<Block> ANOMALOUS_GRASS_BLOCK = BLOCKS.register("anomalous_grass", AnomalousGrassBlock::new);
    public static final RegistryObject<Item> ANOMALOUS_GRASS_ITEM = ITEMS.register("anomalous_grass", () -> new AnomalousGrassItem((AnomalousGrassBlock) ANOMALOUS_GRASS_BLOCK.get()));

    // Resonite Ore Block
    public static final RegistryObject<Block> RESONITE_ORE_BLOCK = BLOCKS.register("resonite_ore", ResoniteOreBlock::new);
    
    // Resonite Block
    public static final RegistryObject<Block> RESONITE_BLOCK = BLOCKS.register("resonite_block", ResoniteBlock::new);
    public static final RegistryObject<Item> RESONITE_BLOCK_ITEM = ITEMS.register("resonite_block", () -> new BlockItem(RESONITE_BLOCK.get(), new Item.Properties()));
    
    // Resonite Decoration Blocks
    public static final RegistryObject<Block> RESONITE_TILE_BLOCK = BLOCKS.register("resonite_tile", ResoniteTileBlock::new);
    public static final RegistryObject<Item> RESONITE_TILE_ITEM = ITEMS.register("resonite_tile", () -> new BlockItem(RESONITE_TILE_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> RESONITE_TILE_STAIRS_BLOCK = BLOCKS.register("resonite_tile_stairs", ResoniteTileStairsBlock::new);
    public static final RegistryObject<Item> RESONITE_TILE_STAIRS_ITEM = ITEMS.register("resonite_tile_stairs", () -> new BlockItem(RESONITE_TILE_STAIRS_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> FANCY_RESONITE_TILE_BLOCK = BLOCKS.register("fancy_resonite_tile", FancyResoniteTileBlock::new);
    public static final RegistryObject<Item> FANCY_RESONITE_TILE_ITEM = ITEMS.register("fancy_resonite_tile", () -> new BlockItem(FANCY_RESONITE_TILE_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> RESONITE_PILLAR_BLOCK = BLOCKS.register("resonite_pillar", ResonitePillarBlock::new);
    public static final RegistryObject<Item> RESONITE_PILLAR_ITEM = ITEMS.register("resonite_pillar", () -> new BlockItem(RESONITE_PILLAR_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> RESONITE_DOOR_BLOCK = BLOCKS.register("resonite_door", ResoniteDoorBlock::new);
    public static final RegistryObject<Item> RESONITE_DOOR_ITEM = ITEMS.register("resonite_door", () -> new net.minecraft.world.item.DoubleHighBlockItem(RESONITE_DOOR_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> RESONITE_TILE_SLAB_BLOCK = BLOCKS.register("resonite_tile_slab", ResoniteTileSlabBlock::new);
    public static final RegistryObject<Item> RESONITE_TILE_SLAB_ITEM = ITEMS.register("resonite_tile_slab", () -> new BlockItem(RESONITE_TILE_SLAB_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> RESONITE_TRAPDOOR_BLOCK = BLOCKS.register("resonite_trapdoor", ResoniteTrapdoorBlock::new);
    public static final RegistryObject<Item> RESONITE_TRAPDOOR_ITEM = ITEMS.register("resonite_trapdoor", () -> new BlockItem(RESONITE_TRAPDOOR_BLOCK.get(), new Item.Properties()));
    
    // Shard Ore Blocks
    public static final RegistryObject<Block> GRAVITIC_SHARD_ORE_BLOCK = BLOCKS.register("gravitic_shard_ore", GraviticShardOreBlock::new);
    public static final RegistryObject<Block> CHRONO_SHARD_ORE_BLOCK = BLOCKS.register("chrono_shard_ore", ChronoShardOreBlock::new);
    public static final RegistryObject<Block> SPATIAL_SHARD_ORE_BLOCK = BLOCKS.register("spatial_shard_ore", SpatialShardOreBlock::new);
    public static final RegistryObject<Block> SHADE_SHARD_ORE_BLOCK = BLOCKS.register("shade_shard_ore", ShadeShardOreBlock::new);
    public static final RegistryObject<Block> INSIGHT_SHARD_ORE_BLOCK = BLOCKS.register("insight_shard_ore", InsightShardOreBlock::new);
    public static final RegistryObject<Block> ENERGETIC_SHARD_ORE_BLOCK = BLOCKS.register("energetic_shard_ore", EnergeticShardOreBlock::new);
    
    // Research Machine - advanced research device
    public static final RegistryObject<Block> RESEARCH_MACHINE_BLOCK = BLOCKS.register("research_machine", ResearchMachineBlock::new);
    public static final RegistryObject<Item> RESEARCH_MACHINE_ITEM = ITEMS.register("research_machine", () -> new BlockItem(RESEARCH_MACHINE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<ResearchMachineBlockEntity>> RESEARCH_MACHINE_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("research_machine", 
        () -> BlockEntityType.Builder.of((pos, state) -> new ResearchMachineBlockEntity(pos, state), RESEARCH_MACHINE_BLOCK.get()).build(null));
    public static final RegistryObject<Item> RESONITE_ORE_ITEM = ITEMS.register("resonite_ore", () -> new BlockItem(RESONITE_ORE_BLOCK.get(), new Item.Properties()));
    
    // Shard Ore Block Items
    public static final RegistryObject<Item> GRAVITIC_SHARD_ORE_ITEM = ITEMS.register("gravitic_shard_ore", () -> new BlockItem(GRAVITIC_SHARD_ORE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHRONO_SHARD_ORE_ITEM = ITEMS.register("chrono_shard_ore", () -> new BlockItem(CHRONO_SHARD_ORE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SPATIAL_SHARD_ORE_ITEM = ITEMS.register("spatial_shard_ore", () -> new BlockItem(SPATIAL_SHARD_ORE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADE_SHARD_ORE_ITEM = ITEMS.register("shade_shard_ore", () -> new BlockItem(SHADE_SHARD_ORE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> INSIGHT_SHARD_ORE_ITEM = ITEMS.register("insight_shard_ore", () -> new BlockItem(INSIGHT_SHARD_ORE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENERGETIC_SHARD_ORE_ITEM = ITEMS.register("energetic_shard_ore", () -> new BlockItem(ENERGETIC_SHARD_ORE_BLOCK.get(), new Item.Properties()));

    // Raw Resonite Item
    public static final RegistryObject<Item> RAW_RESONITE = ITEMS.register("raw_resonite", RawResoniteItem::new);

    // Resonite Ingot Item
    public static final RegistryObject<Item> RESONITE_INGOT = ITEMS.register("resonite_ingot", ResoniteIngotItem::new);

    // Resonite Nugget Item
    public static final RegistryObject<Item> RESONITE_NUGGET = ITEMS.register("resonite_nugget", ResoniteNuggetItem::new);

    // Resonant Coil Item
    public static final RegistryObject<Item> RESONANT_COIL = ITEMS.register("resonant_coil", ResonantCoilItem::new);

    // Stabilized Core Item
    public static final RegistryObject<Item> STABILIZED_CORE = ITEMS.register("stabilized_core", StabilizedCoreItem::new);

    // Resonant Circuit Item
    public static final RegistryObject<Item> RESONANT_CIRCUIT = ITEMS.register("resonant_circuit", ResonantCircuitItem::new);

    // Research Tablet Item
    public static final RegistryObject<Item> RESEARCH_TABLET = ITEMS.register("research_tablet", ResearchTabletItem::new);

    // Anomaly Shard Items
    public static final RegistryObject<Item> GRAVITIC_SHARD = ITEMS.register("gravitic_shard", GraviticShardItem::new);
    public static final RegistryObject<Item> CHRONO_SHARD = ITEMS.register("chrono_shard", ChronoShardItem::new);
    public static final RegistryObject<Item> SPATIAL_SHARD = ITEMS.register("spatial_shard", SpatialShardItem::new);
    public static final RegistryObject<Item> SHADE_SHARD = ITEMS.register("shade_shard", ShadeShardItem::new);
    public static final RegistryObject<Item> INSIGHT_SHARD = ITEMS.register("insight_shard", InsightShardItem::new);
    public static final RegistryObject<Item> ENERGETIC_SHARD = ITEMS.register("energetic_shard", EnergeticShardItem::new);
    
    // Shard Crystal Blocks
    public static final RegistryObject<Block> SHADE_SHARD_CRYSTAL = BLOCKS.register("shade_shard_crystal", net.reversteam.strangematter.block.ShardCrystalBlock::new);
    public static final RegistryObject<Item> SHADE_SHARD_CRYSTAL_ITEM = ITEMS.register("shade_shard_crystal", () -> new BlockItem(SHADE_SHARD_CRYSTAL.get(), new Item.Properties()));
    public static final RegistryObject<Block> GRAVITIC_SHARD_CRYSTAL = BLOCKS.register("gravitic_shard_crystal", net.reversteam.strangematter.block.ShardCrystalBlock::new);
    public static final RegistryObject<Item> GRAVITIC_SHARD_CRYSTAL_ITEM = ITEMS.register("gravitic_shard_crystal", () -> new BlockItem(GRAVITIC_SHARD_CRYSTAL.get(), new Item.Properties()));
    public static final RegistryObject<Block> ENERGETIC_SHARD_CRYSTAL = BLOCKS.register("energetic_shard_crystal", net.reversteam.strangematter.block.ShardCrystalBlock::new);
    public static final RegistryObject<Item> ENERGETIC_SHARD_CRYSTAL_ITEM = ITEMS.register("energetic_shard_crystal", () -> new BlockItem(ENERGETIC_SHARD_CRYSTAL.get(), new Item.Properties()));
    public static final RegistryObject<Block> INSIGHT_SHARD_CRYSTAL = BLOCKS.register("insight_shard_crystal", net.reversteam.strangematter.block.ShardCrystalBlock::new);
    public static final RegistryObject<Item> INSIGHT_SHARD_CRYSTAL_ITEM = ITEMS.register("insight_shard_crystal", () -> new BlockItem(INSIGHT_SHARD_CRYSTAL.get(), new Item.Properties()));
    public static final RegistryObject<Block> CHRONO_SHARD_CRYSTAL = BLOCKS.register("chrono_shard_crystal", net.reversteam.strangematter.block.ShardCrystalBlock::new);
    public static final RegistryObject<Item> CHRONO_SHARD_CRYSTAL_ITEM = ITEMS.register("chrono_shard_crystal", () -> new BlockItem(CHRONO_SHARD_CRYSTAL.get(), new Item.Properties()));
    public static final RegistryObject<Block> SPATIAL_SHARD_CRYSTAL = BLOCKS.register("spatial_shard_crystal", net.reversteam.strangematter.block.ShardCrystalBlock::new);
    public static final RegistryObject<Item> SPATIAL_SHARD_CRYSTAL_ITEM = ITEMS.register("spatial_shard_crystal", () -> new BlockItem(SPATIAL_SHARD_CRYSTAL.get(), new Item.Properties()));
    
    // Shard Lamp Blocks
    public static final RegistryObject<Block> SHADE_SHARD_LAMP = BLOCKS.register("shade_shard_lamp", net.reversteam.strangematter.block.ShardLampBlock::new);
    public static final RegistryObject<Item> SHADE_SHARD_LAMP_ITEM = ITEMS.register("shade_shard_lamp", () -> new BlockItem(SHADE_SHARD_LAMP.get(), new Item.Properties()));
    public static final RegistryObject<Block> GRAVITIC_SHARD_LAMP = BLOCKS.register("gravitic_shard_lamp", net.reversteam.strangematter.block.ShardLampBlock::new);
    public static final RegistryObject<Item> GRAVITIC_SHARD_LAMP_ITEM = ITEMS.register("gravitic_shard_lamp", () -> new BlockItem(GRAVITIC_SHARD_LAMP.get(), new Item.Properties()));
    public static final RegistryObject<Block> ENERGETIC_SHARD_LAMP = BLOCKS.register("energetic_shard_lamp", net.reversteam.strangematter.block.ShardLampBlock::new);
    public static final RegistryObject<Item> ENERGETIC_SHARD_LAMP_ITEM = ITEMS.register("energetic_shard_lamp", () -> new BlockItem(ENERGETIC_SHARD_LAMP.get(), new Item.Properties()));
    public static final RegistryObject<Block> INSIGHT_SHARD_LAMP = BLOCKS.register("insight_shard_lamp", net.reversteam.strangematter.block.ShardLampBlock::new);
    public static final RegistryObject<Item> INSIGHT_SHARD_LAMP_ITEM = ITEMS.register("insight_shard_lamp", () -> new BlockItem(INSIGHT_SHARD_LAMP.get(), new Item.Properties()));
    public static final RegistryObject<Block> CHRONO_SHARD_LAMP = BLOCKS.register("chrono_shard_lamp", net.reversteam.strangematter.block.ShardLampBlock::new);
    public static final RegistryObject<Item> CHRONO_SHARD_LAMP_ITEM = ITEMS.register("chrono_shard_lamp", () -> new BlockItem(CHRONO_SHARD_LAMP.get(), new Item.Properties()));
    public static final RegistryObject<Block> SPATIAL_SHARD_LAMP = BLOCKS.register("spatial_shard_lamp", net.reversteam.strangematter.block.ShardLampBlock::new);
    public static final RegistryObject<Item> SPATIAL_SHARD_LAMP_ITEM = ITEMS.register("spatial_shard_lamp", () -> new BlockItem(SPATIAL_SHARD_LAMP.get(), new Item.Properties()));
    
    // Shard Lantern Blocks
    public static final RegistryObject<Block> SHADE_SHARD_LANTERN = BLOCKS.register("shade_shard_lantern", net.reversteam.strangematter.block.ShardLanternBlock::new);
    public static final RegistryObject<Item> SHADE_SHARD_LANTERN_ITEM = ITEMS.register("shade_shard_lantern", () -> new BlockItem(SHADE_SHARD_LANTERN.get(), new Item.Properties()));
    public static final RegistryObject<Block> GRAVITIC_SHARD_LANTERN = BLOCKS.register("gravitic_shard_lantern", net.reversteam.strangematter.block.ShardLanternBlock::new);
    public static final RegistryObject<Item> GRAVITIC_SHARD_LANTERN_ITEM = ITEMS.register("gravitic_shard_lantern", () -> new BlockItem(GRAVITIC_SHARD_LANTERN.get(), new Item.Properties()));
    public static final RegistryObject<Block> ENERGETIC_SHARD_LANTERN = BLOCKS.register("energetic_shard_lantern", net.reversteam.strangematter.block.ShardLanternBlock::new);
    public static final RegistryObject<Item> ENERGETIC_SHARD_LANTERN_ITEM = ITEMS.register("energetic_shard_lantern", () -> new BlockItem(ENERGETIC_SHARD_LANTERN.get(), new Item.Properties()));
    public static final RegistryObject<Block> INSIGHT_SHARD_LANTERN = BLOCKS.register("insight_shard_lantern", net.reversteam.strangematter.block.ShardLanternBlock::new);
    public static final RegistryObject<Item> INSIGHT_SHARD_LANTERN_ITEM = ITEMS.register("insight_shard_lantern", () -> new BlockItem(INSIGHT_SHARD_LANTERN.get(), new Item.Properties()));
    public static final RegistryObject<Block> CHRONO_SHARD_LANTERN = BLOCKS.register("chrono_shard_lantern", net.reversteam.strangematter.block.ShardLanternBlock::new);
    public static final RegistryObject<Item> CHRONO_SHARD_LANTERN_ITEM = ITEMS.register("chrono_shard_lantern", () -> new BlockItem(CHRONO_SHARD_LANTERN.get(), new Item.Properties()));
    public static final RegistryObject<Block> SPATIAL_SHARD_LANTERN = BLOCKS.register("spatial_shard_lantern", net.reversteam.strangematter.block.ShardLanternBlock::new);
    public static final RegistryObject<Item> SPATIAL_SHARD_LANTERN_ITEM = ITEMS.register("spatial_shard_lantern", () -> new BlockItem(SPATIAL_SHARD_LANTERN.get(), new Item.Properties()));

    // Warp Gun Item
    public static final RegistryObject<Item> WARP_GUN = ITEMS.register("warp_gun", WarpGunItem::new);

    // Chrono Blister Item
    public static final RegistryObject<Item> CHRONO_BLISTER = ITEMS.register("chrono_blister", ChronoBlisterItem::new);

    // Echo Vacuum Item
    public static final RegistryObject<Item> ECHO_VACUUM = ITEMS.register("echo_vacuum", EchoVacuumItem::new);

    // Graviton Hammer Item
    public static final RegistryObject<Item> GRAVITON_HAMMER = ITEMS.register("graviton_hammer", GravitonHammerItem::new);

    // Tinfoil Hat Item
    public static final RegistryObject<Item> TINFOIL_HAT = ITEMS.register("tinfoil_hat", TinfoilHatItem::new);

    // Containment Capsule Items
    public static final RegistryObject<Item> CONTAINMENT_CAPSULE = ITEMS.register("containment_capsule", () -> new ContainmentCapsuleItem(ContainmentCapsuleItem.AnomalyType.NONE));
    public static final RegistryObject<Item> CONTAINMENT_CAPSULE_GRAVITY = ITEMS.register("containment_capsule_gravity", () -> new ContainmentCapsuleItem(ContainmentCapsuleItem.AnomalyType.GRAVITY));
    public static final RegistryObject<Item> CONTAINMENT_CAPSULE_ENERGETIC = ITEMS.register("containment_capsule_energetic", () -> new ContainmentCapsuleItem(ContainmentCapsuleItem.AnomalyType.ENERGETIC));
    public static final RegistryObject<Item> CONTAINMENT_CAPSULE_ECHOING_SHADOW = ITEMS.register("containment_capsule_echoing_shadow", () -> new ContainmentCapsuleItem(ContainmentCapsuleItem.AnomalyType.ECHOING_SHADOW));
    public static final RegistryObject<Item> CONTAINMENT_CAPSULE_TEMPORAL_BLOOM = ITEMS.register("containment_capsule_temporal_bloom", () -> new ContainmentCapsuleItem(ContainmentCapsuleItem.AnomalyType.TEMPORAL_BLOOM));
    public static final RegistryObject<Item> CONTAINMENT_CAPSULE_THOUGHTWELL = ITEMS.register("containment_capsule_thoughtwell", () -> new ContainmentCapsuleItem(ContainmentCapsuleItem.AnomalyType.THOUGHTWELL));
    public static final RegistryObject<Item> CONTAINMENT_CAPSULE_WARP_GATE = ITEMS.register("containment_capsule_warp_gate", () -> new ContainmentCapsuleItem(ContainmentCapsuleItem.AnomalyType.WARP_GATE));

    // Set static references for ContainmentCapsuleItem
    static {
        ContainmentCapsuleItem.EMPTY_CAPSULE = CONTAINMENT_CAPSULE;
        ContainmentCapsuleItem.GRAVITY_CAPSULE = CONTAINMENT_CAPSULE_GRAVITY;
        ContainmentCapsuleItem.ENERGETIC_CAPSULE = CONTAINMENT_CAPSULE_ENERGETIC;
        ContainmentCapsuleItem.ECHOING_SHADOW_CAPSULE = CONTAINMENT_CAPSULE_ECHOING_SHADOW;
        ContainmentCapsuleItem.TEMPORAL_BLOOM_CAPSULE = CONTAINMENT_CAPSULE_TEMPORAL_BLOOM;
        ContainmentCapsuleItem.THOUGHTWELL_CAPSULE = CONTAINMENT_CAPSULE_THOUGHTWELL;
        ContainmentCapsuleItem.WARP_GATE_CAPSULE = CONTAINMENT_CAPSULE_WARP_GATE;
    }

    // Reality Forge Block
    public static final RegistryObject<Block> REALITY_FORGE_BLOCK = BLOCKS.register("reality_forge", net.reversteam.strangematter.block.RealityForgeBlock::new);
    public static final RegistryObject<Item> REALITY_FORGE_ITEM = ITEMS.register("reality_forge", () -> new BlockItem(REALITY_FORGE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<net.reversteam.strangematter.block.RealityForgeBlockEntity>> REALITY_FORGE_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("reality_forge", 
        () -> BlockEntityType.Builder.of((pos, state) -> new net.reversteam.strangematter.block.RealityForgeBlockEntity(pos, state), REALITY_FORGE_BLOCK.get()).build(null));

    // Resonance Condenser Block
    public static final RegistryObject<Block> RESONANCE_CONDENSER_BLOCK = BLOCKS.register("resonance_condenser", ResonanceCondenserBlock::new);
    public static final RegistryObject<Item> RESONANCE_CONDENSER_ITEM = ITEMS.register("resonance_condenser", () -> new ResonanceCondenserItem((ResonanceCondenserBlock) RESONANCE_CONDENSER_BLOCK.get()));
    public static final RegistryObject<BlockEntityType<ResonanceCondenserBlockEntity>> RESONANCE_CONDENSER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("resonance_condenser", 
        () -> BlockEntityType.Builder.of((pos, state) -> new ResonanceCondenserBlockEntity(pos, state), RESONANCE_CONDENSER_BLOCK.get()).build(null));
    
    // Resonant Burner
    public static final RegistryObject<Block> RESONANT_BURNER_BLOCK = BLOCKS.register("resonant_burner", net.reversteam.strangematter.block.ResonantBurnerBlock::new);
    public static final RegistryObject<Item> RESONANT_BURNER_ITEM = ITEMS.register("resonant_burner", () -> new BlockItem(RESONANT_BURNER_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<net.reversteam.strangematter.block.ResonantBurnerBlockEntity>> RESONANT_BURNER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("resonant_burner", 
        () -> BlockEntityType.Builder.of((pos, state) -> new net.reversteam.strangematter.block.ResonantBurnerBlockEntity(pos, state), RESONANT_BURNER_BLOCK.get()).build(null));

    // Paradoxical Energy Cell Block
    public static final RegistryObject<Block> PARADOXICAL_ENERGY_CELL_BLOCK = BLOCKS.register("paradoxical_energy_cell", net.reversteam.strangematter.block.ParadoxicalEnergyCellBlock::new);
    public static final RegistryObject<Item> PARADOXICAL_ENERGY_CELL_ITEM = ITEMS.register("paradoxical_energy_cell", () -> new net.reversteam.strangematter.item.ParadoxicalEnergyCellItem((net.reversteam.strangematter.block.ParadoxicalEnergyCellBlock) PARADOXICAL_ENERGY_CELL_BLOCK.get()));
    public static final RegistryObject<BlockEntityType<net.reversteam.strangematter.block.ParadoxicalEnergyCellBlockEntity>> PARADOXICAL_ENERGY_CELL_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("paradoxical_energy_cell", 
        () -> BlockEntityType.Builder.of((pos, state) -> new net.reversteam.strangematter.block.ParadoxicalEnergyCellBlockEntity(pos, state), PARADOXICAL_ENERGY_CELL_BLOCK.get()).build(null));

    // Rift Stabilizer Block
    public static final RegistryObject<Block> RIFT_STABILIZER_BLOCK = BLOCKS.register("rift_stabilizer", net.reversteam.strangematter.block.RiftStabilizerBlock::new);
    public static final RegistryObject<Item> RIFT_STABILIZER_ITEM = ITEMS.register("rift_stabilizer", () -> new BlockItem(RIFT_STABILIZER_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<net.reversteam.strangematter.block.RiftStabilizerBlockEntity>> RIFT_STABILIZER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("rift_stabilizer", 
        () -> BlockEntityType.Builder.of((pos, state) -> new net.reversteam.strangematter.block.RiftStabilizerBlockEntity(pos, state), RIFT_STABILIZER_BLOCK.get()).build(null));

    // Stasis Projector Block
    public static final RegistryObject<Block> STASIS_PROJECTOR_BLOCK = BLOCKS.register("stasis_projector", net.reversteam.strangematter.block.StasisProjectorBlock::new);
    public static final RegistryObject<Item> STASIS_PROJECTOR_ITEM = ITEMS.register("stasis_projector", () -> new BlockItem(STASIS_PROJECTOR_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<net.reversteam.strangematter.block.StasisProjectorBlockEntity>> STASIS_PROJECTOR_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("stasis_projector", 
        () -> BlockEntityType.Builder.of((pos, state) -> new net.reversteam.strangematter.block.StasisProjectorBlockEntity(pos, state), STASIS_PROJECTOR_BLOCK.get()).build(null));

    // Resonant Conduit Block
    public static final RegistryObject<Block> RESONANT_CONDUIT_BLOCK = BLOCKS.register("resonant_conduit", net.reversteam.strangematter.block.ResonantConduitBlock::new);
    public static final RegistryObject<Item> RESONANT_CONDUIT_ITEM = ITEMS.register("resonant_conduit", () -> new net.reversteam.strangematter.item.ResonantConduitItem((net.reversteam.strangematter.block.ResonantConduitBlock) RESONANT_CONDUIT_BLOCK.get()));
    public static final RegistryObject<BlockEntityType<net.reversteam.strangematter.block.ResonantConduitBlockEntity>> RESONANT_CONDUIT_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("resonant_conduit", 
        () -> BlockEntityType.Builder.of((pos, state) -> new net.reversteam.strangematter.block.ResonantConduitBlockEntity(pos, state), RESONANT_CONDUIT_BLOCK.get()).build(null));

    // Levitation Pad Block
    public static final RegistryObject<Block> LEVITATION_PAD_BLOCK = BLOCKS.register("levitation_pad", LevitationPadBlock::new);
    public static final RegistryObject<Item> LEVITATION_PAD_ITEM = ITEMS.register("levitation_pad", () -> new BlockItem(LEVITATION_PAD_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<LevitationPadBlockEntity>> LEVITATION_PAD_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("levitation_pad", 
        () -> BlockEntityType.Builder.of((pos, state) -> new LevitationPadBlockEntity(pos, state), LEVITATION_PAD_BLOCK.get()).build(null));

    // Time Dilation Block
    public static final RegistryObject<Block> TIME_DILATION_BLOCK = BLOCKS.register("time_dilation_block", TimeDilationBlock::new);
    public static final RegistryObject<Item> TIME_DILATION_BLOCK_ITEM = ITEMS.register("time_dilation_block", () -> new BlockItem(TIME_DILATION_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<TimeDilationBlockEntity>> TIME_DILATION_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("time_dilation_block", 
        () -> BlockEntityType.Builder.of((pos, state) -> new TimeDilationBlockEntity(pos, state), TIME_DILATION_BLOCK.get()).build(null));

    // Anomaly Spawner Marker Block (used for world generation entity spawning)
    public static final RegistryObject<Block> ANOMALY_SPAWNER_MARKER_BLOCK = BLOCKS.register("anomaly_spawner_marker", net.reversteam.strangematter.block.AnomalySpawnerMarkerBlock::new);
    public static final RegistryObject<BlockEntityType<net.reversteam.strangematter.block.AnomalySpawnerMarkerBlockEntity>> ANOMALY_SPAWNER_MARKER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("anomaly_spawner_marker", 
        () -> BlockEntityType.Builder.of((pos, state) -> new net.reversteam.strangematter.block.AnomalySpawnerMarkerBlockEntity(pos, state), ANOMALY_SPAWNER_MARKER_BLOCK.get()).build(null));

    // Register POI Type for Research Machine (villager job site)
    // We'll use a lazy supplier to get block states when they're actually available
    public static final RegistryObject<net.minecraft.world.entity.ai.village.poi.PoiType> RESEARCH_MACHINE_POI = POI_TYPES.register("research_machine_poi",
        () -> {
            try {
                var blockStates = RESEARCH_MACHINE_BLOCK.get().getStateDefinition().getPossibleStates();
                return new net.minecraft.world.entity.ai.village.poi.PoiType(
                    com.google.common.collect.ImmutableSet.copyOf(blockStates),
                    1, 1);
            } catch (Exception e) {
                // Fallback to empty set if block not yet initialized
                return new net.minecraft.world.entity.ai.village.poi.PoiType(
                    com.google.common.collect.ImmutableSet.of(),
                    1, 1);
            }
        });

    // Register Anomaly Scientist Villager Profession
    public static final RegistryObject<net.minecraft.world.entity.npc.VillagerProfession> ANOMALY_SCIENTIST = VILLAGER_PROFESSIONS.register("anomaly_scientist",
        () -> {
            net.minecraft.resources.ResourceKey<net.minecraft.world.entity.ai.village.poi.PoiType> poiKey = 
                net.minecraft.resources.ResourceKey.create(
                    net.minecraft.core.registries.Registries.POINT_OF_INTEREST_TYPE,
                    new net.minecraft.resources.ResourceLocation(MODID, "research_machine_poi"));
            
            return new net.minecraft.world.entity.npc.VillagerProfession(
                "anomaly_scientist",
                holder -> holder.is(poiKey),
                holder -> holder.is(poiKey),
                com.google.common.collect.ImmutableSet.of(),
                com.google.common.collect.ImmutableSet.of(),
                net.minecraft.sounds.SoundEvents.VILLAGER_WORK_CLERIC);
        });

    // Particle Types
    public static final RegistryObject<net.minecraft.core.particles.SimpleParticleType> ENERGY_ABSORPTION_PARTICLE = PARTICLE_TYPES.register("energy_absorption", 
        () -> new net.minecraft.core.particles.SimpleParticleType(true));

    // Menu Types
    public static final RegistryObject<net.minecraft.world.inventory.MenuType<net.reversteam.strangematter.menu.ResonanceCondenserMenu>> RESONANCE_CONDENSER_MENU = MENU_TYPES.register("resonance_condenser",
        () -> net.minecraftforge.common.extensions.IForgeMenuType.create((windowId, inv, data) -> new net.reversteam.strangematter.menu.ResonanceCondenserMenu(windowId, inv, data)));
    
    // Resonant Burner Menu
    public static final RegistryObject<net.minecraft.world.inventory.MenuType<net.reversteam.strangematter.menu.ResonantBurnerMenu>> RESONANT_BURNER_MENU = MENU_TYPES.register("resonant_burner",
        () -> net.minecraftforge.common.extensions.IForgeMenuType.create((windowId, inv, data) -> new net.reversteam.strangematter.menu.ResonantBurnerMenu(windowId, inv, data)));

    // Reality Forge Menu
    public static final RegistryObject<net.minecraft.world.inventory.MenuType<net.reversteam.strangematter.menu.RealityForgeMenu>> REALITY_FORGE_MENU = MENU_TYPES.register("reality_forge",
        () -> net.minecraftforge.common.extensions.IForgeMenuType.create((windowId, inv, data) -> new net.reversteam.strangematter.menu.RealityForgeMenu(windowId, inv, data)));

    // Reality Forge Recipe Type and Serializer
    public static final RegistryObject<net.minecraft.world.item.crafting.RecipeType<net.reversteam.strangematter.recipe.RealityForgeRecipe>> REALITY_FORGE_RECIPE_TYPE = RECIPE_TYPES.register("reality_forge",
        () -> new net.minecraft.world.item.crafting.RecipeType<net.reversteam.strangematter.recipe.RealityForgeRecipe>() {});
    public static final RegistryObject<net.minecraft.world.item.crafting.RecipeSerializer<net.reversteam.strangematter.recipe.RealityForgeRecipe>> REALITY_FORGE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("reality_forge",
        () -> new net.reversteam.strangematter.recipe.RealityForgeRecipe.Serializer());

        
    // Hoverboard Item
    public static final RegistryObject<Item> HOVERBOARD = ITEMS.register("hoverboard", HoverboardItem::new);

    // Hoverboard Entity
    public static final RegistryObject<EntityType<HoverboardEntity>> HOVERBOARD_ENTITY = ENTITY_TYPES.register("hoverboard", 
        () -> EntityType.Builder.<HoverboardEntity>of(HoverboardEntity::new, MobCategory.MISC)
            .sized(1.0f, 1.0f) // Size of the entity
            .build("hoverboard"));

    // Custom gravity attribute for low gravity effects
    public static final RegistryObject<Attribute> ENTITY_GRAVITY = ATTRIBUTES.register("entity_gravity", 
        () -> new RangedAttribute("strangematter.entity_gravity", 0.08D, -1.0D, 1.0D).setSyncable(true));

    // Gravity Anomaly Entity
    public static final RegistryObject<EntityType<GravityAnomalyEntity>> GRAVITY_ANOMALY = ENTITY_TYPES.register("gravity_anomaly", 
        () -> EntityType.Builder.<GravityAnomalyEntity>of(GravityAnomalyEntity::new, MobCategory.MISC)
            .sized(1.0f, 1.0f) // Size of the entity
            .build("gravity_anomaly"));
    
    // Energetic Rift Entity
    public static final RegistryObject<EntityType<net.reversteam.strangematter.entity.EnergeticRiftEntity>> ENERGETIC_RIFT = ENTITY_TYPES.register("energetic_rift", 
        () -> EntityType.Builder.<net.reversteam.strangematter.entity.EnergeticRiftEntity>of(
            (entityType, level) -> new net.reversteam.strangematter.entity.EnergeticRiftEntity(entityType, level), MobCategory.MISC)
            .sized(1.0f, 1.0f) // Size of the entity
            .build("energetic_rift"));

    // Echoing Shadow Entity
    public static final RegistryObject<EntityType<EchoingShadowEntity>> ECHOING_SHADOW = ENTITY_TYPES.register("echoing_shadow", 
        () -> EntityType.Builder.<EchoingShadowEntity>of(EchoingShadowEntity::new, MobCategory.MISC)
            .sized(1.0f, 1.0f) // Size of the entity
            .build("echoing_shadow"));

    // Warp Gate Anomaly Entity
    public static final RegistryObject<EntityType<WarpGateAnomalyEntity>> WARP_GATE_ANOMALY_ENTITY = ENTITY_TYPES.register("warp_gate_anomaly", 
        () -> EntityType.Builder.<WarpGateAnomalyEntity>of(WarpGateAnomalyEntity::new, MobCategory.MISC)
            .sized(2.0f, 3.0f) // Larger size for warp gate
            .build("warp_gate_anomaly"));

    // Temporal Bloom Entity
    public static final RegistryObject<EntityType<net.reversteam.strangematter.entity.TemporalBloomEntity>> TEMPORAL_BLOOM = ENTITY_TYPES.register("temporal_bloom", 
        () -> EntityType.Builder.<net.reversteam.strangematter.entity.TemporalBloomEntity>of(
            (entityType, level) -> new net.reversteam.strangematter.entity.TemporalBloomEntity(entityType, level), MobCategory.MISC)
            .sized(1.0f, 1.0f) // Size of the entity
            .build("temporal_bloom"));

    // Thoughtwell Entity
    public static final RegistryObject<EntityType<net.reversteam.strangematter.entity.ThoughtwellEntity>> THOUGHTWELL = ENTITY_TYPES.register("thoughtwell", 
        () -> EntityType.Builder.<net.reversteam.strangematter.entity.ThoughtwellEntity>of(
            (entityType, level) -> new net.reversteam.strangematter.entity.ThoughtwellEntity(entityType, level), MobCategory.MISC)
            .sized(1.0f, 1.0f) // Size of the entity
            .build("thoughtwell"));

    // Warp Projectile Entity
    public static final RegistryObject<EntityType<WarpProjectileEntity>> WARP_PROJECTILE_ENTITY = ENTITY_TYPES.register("warp_projectile", 
        () -> EntityType.Builder.<WarpProjectileEntity>of(WarpProjectileEntity::new, MobCategory.MISC)
            .sized(0.25f, 0.25f) // Small projectile
            .build("warp_projectile"));

    // Chrono Blister Projectile Entity
    public static final RegistryObject<EntityType<ChronoBlisterProjectileEntity>> CHRONO_BLISTER_PROJECTILE_ENTITY = ENTITY_TYPES.register("chrono_blister_projectile", 
        () -> EntityType.Builder.<ChronoBlisterProjectileEntity>of(ChronoBlisterProjectileEntity::new, MobCategory.MISC)
            .sized(0.25f, 0.25f) // Small projectile
            .build("chrono_blister_projectile"));

    // Mini Warp Gate Entity
    public static final RegistryObject<EntityType<MiniWarpGateEntity>> MINI_WARP_GATE_ENTITY = ENTITY_TYPES.register("mini_warp_gate", 
        () -> EntityType.Builder.<MiniWarpGateEntity>of(MiniWarpGateEntity::new, MobCategory.MISC)
            .sized(1.0f, 2.0f) // 1x1x2 hitbox
            .build("mini_warp_gate"));

    // Throwable Containment Capsule Entity
    public static final RegistryObject<EntityType<ThrowableContainmentCapsuleEntity>> THROWABLE_CONTAINMENT_CAPSULE = ENTITY_TYPES.register("throwable_containment_capsule", 
        () -> EntityType.Builder.<ThrowableContainmentCapsuleEntity>of(ThrowableContainmentCapsuleEntity::new, MobCategory.MISC)
            .sized(0.25f, 0.25f) // Small projectile size
            .build("throwable_containment_capsule"));

    // Advancement Triggers
    public static final net.reversteam.strangematter.advancement.AnomalyEffectTrigger ANOMALY_EFFECT_TRIGGER = new net.reversteam.strangematter.advancement.AnomalyEffectTrigger();
    public static final net.reversteam.strangematter.advancement.ScanAnomalyTrigger SCAN_ANOMALY_TRIGGER = new net.reversteam.strangematter.advancement.ScanAnomalyTrigger();
    public static final net.reversteam.strangematter.advancement.CompleteResearchCategoryTrigger COMPLETE_RESEARCH_CATEGORY_TRIGGER = new net.reversteam.strangematter.advancement.CompleteResearchCategoryTrigger();


    // Sound Events are now registered in StrangeMatterSounds class

    // World Generation Features
    public static final RegistryObject<Feature<net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration>> GRAVITY_ANOMALY_FEATURE = FEATURES.register("gravity_anomaly", 
        () -> new GravityAnomalyConfiguredFeature());
    
    public static final RegistryObject<Feature<net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration>> ENERGETIC_RIFT_FEATURE = FEATURES.register("energetic_rift", 
        () -> new net.reversteam.strangematter.worldgen.EnergeticRiftConfiguredFeature());
    
    public static final RegistryObject<Feature<net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration>> ECHOING_SHADOW_FEATURE = FEATURES.register("echoing_shadow", 
        () -> new EchoingShadowConfiguredFeature());
    
    public static final RegistryObject<Feature<net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration>> TEMPORAL_BLOOM_FEATURE = FEATURES.register("temporal_bloom", 
        () -> new net.reversteam.strangematter.worldgen.TemporalBloomConfiguredFeature());
    
    public static final RegistryObject<Feature<net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration>> THOUGHTWELL_FEATURE = FEATURES.register("thoughtwell", 
        () -> new net.reversteam.strangematter.worldgen.ThoughtwellConfiguredFeature());
    
    public static final RegistryObject<Feature<net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration>> WARP_GATE_ANOMALY_FEATURE = FEATURES.register("warp_gate_anomaly", 
        () -> new WarpGateAnomalyConfiguredFeature());


    // Creates a creative tab with the id "strangematter:strange_matter_tab" for the anomaly items
    public static final RegistryObject<CreativeModeTab> STRANGE_MATTER_TAB = CREATIVE_MODE_TABS.register("strange_matter_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .title(Component.translatable("itemGroup.strangematter.strange_matter_tab"))
            .icon(() -> FIELD_SCANNER.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                // Gadgets
                output.accept(FIELD_SCANNER.get());
                output.accept(RESEARCH_TABLET.get());
                output.accept(ANOMALY_RESONATOR.get());
                output.accept(ECHOFORM_IMPRINTER.get());
                output.accept(WARP_GUN.get());
                output.accept(CHRONO_BLISTER.get());
                output.accept(ECHO_VACUUM.get());
                output.accept(GRAVITON_HAMMER.get());
                output.accept(TINFOIL_HAT.get());
                output.accept(HOVERBOARD.get());
                
                // Containment Capsules
                output.accept(CONTAINMENT_CAPSULE.get());
                output.accept(CONTAINMENT_CAPSULE_GRAVITY.get());
                output.accept(CONTAINMENT_CAPSULE_ENERGETIC.get());
                output.accept(CONTAINMENT_CAPSULE_ECHOING_SHADOW.get());
                output.accept(CONTAINMENT_CAPSULE_TEMPORAL_BLOOM.get());
                output.accept(CONTAINMENT_CAPSULE_THOUGHTWELL.get());
                output.accept(CONTAINMENT_CAPSULE_WARP_GATE.get());
                
                // Machines
                output.accept(ANOMALOUS_GRASS_ITEM.get());
                output.accept(RESEARCH_MACHINE_ITEM.get());
                output.accept(REALITY_FORGE_ITEM.get());
                output.accept(RESONANT_BURNER_ITEM.get());
                output.accept(RESONANCE_CONDENSER_ITEM.get());
                output.accept(PARADOXICAL_ENERGY_CELL_ITEM.get());
                output.accept(RIFT_STABILIZER_ITEM.get());
                output.accept(STASIS_PROJECTOR_ITEM.get());
                output.accept(RESONANT_CONDUIT_ITEM.get());
                output.accept(LEVITATION_PAD_ITEM.get());
                output.accept(TIME_DILATION_BLOCK_ITEM.get());
                
                // Building Blocks
                output.accept(RESONITE_ORE_ITEM.get());
                output.accept(RESONITE_BLOCK_ITEM.get());
                output.accept(RESONITE_TILE_ITEM.get());
                output.accept(RESONITE_TILE_STAIRS_ITEM.get());
                output.accept(RESONITE_TILE_SLAB_ITEM.get());
                output.accept(FANCY_RESONITE_TILE_ITEM.get());
                output.accept(RESONITE_PILLAR_ITEM.get());
                output.accept(RESONITE_DOOR_ITEM.get());
                output.accept(RESONITE_TRAPDOOR_ITEM.get());
                output.accept(GRAVITIC_SHARD_ORE_ITEM.get());
                output.accept(CHRONO_SHARD_ORE_ITEM.get());
                output.accept(SPATIAL_SHARD_ORE_ITEM.get());
                output.accept(SHADE_SHARD_ORE_ITEM.get());
                output.accept(INSIGHT_SHARD_ORE_ITEM.get());
                output.accept(ENERGETIC_SHARD_ORE_ITEM.get());
                output.accept(SHADE_SHARD_LAMP_ITEM.get());
                output.accept(GRAVITIC_SHARD_LAMP_ITEM.get());
                output.accept(ENERGETIC_SHARD_LAMP_ITEM.get());
                output.accept(INSIGHT_SHARD_LAMP_ITEM.get());
                output.accept(CHRONO_SHARD_LAMP_ITEM.get());
                output.accept(SPATIAL_SHARD_LAMP_ITEM.get());
                output.accept(SHADE_SHARD_LAMP_ITEM.get());
                output.accept(GRAVITIC_SHARD_LAMP_ITEM.get());
                output.accept(ENERGETIC_SHARD_LAMP_ITEM.get());
                output.accept(INSIGHT_SHARD_LAMP_ITEM.get());
                output.accept(CHRONO_SHARD_LAMP_ITEM.get());
                output.accept(SPATIAL_SHARD_LAMP_ITEM.get());
                output.accept(SHADE_SHARD_LANTERN_ITEM.get());
                output.accept(GRAVITIC_SHARD_LANTERN_ITEM.get());
                output.accept(ENERGETIC_SHARD_LANTERN_ITEM.get());
                output.accept(INSIGHT_SHARD_LANTERN_ITEM.get());
                output.accept(CHRONO_SHARD_LANTERN_ITEM.get());
                output.accept(SPATIAL_SHARD_LANTERN_ITEM.get());
                
                // Materials/Resources
                output.accept(RAW_RESONITE.get());
                output.accept(RESONITE_INGOT.get());
                output.accept(RESONITE_NUGGET.get());
                output.accept(RESONANT_COIL.get());
                output.accept(STABILIZED_CORE.get());
                output.accept(RESONANT_CIRCUIT.get());
                output.accept(GRAVITIC_SHARD.get());
                output.accept(CHRONO_SHARD.get());
                output.accept(SPATIAL_SHARD.get());
                output.accept(SHADE_SHARD.get());
                output.accept(INSIGHT_SHARD.get());
                output.accept(ENERGETIC_SHARD.get());
                output.accept(SHADE_SHARD_CRYSTAL_ITEM.get());
                output.accept(GRAVITIC_SHARD_CRYSTAL_ITEM.get());
                output.accept(ENERGETIC_SHARD_CRYSTAL_ITEM.get());
                output.accept(INSIGHT_SHARD_CRYSTAL_ITEM.get());
                output.accept(CHRONO_SHARD_CRYSTAL_ITEM.get());
                output.accept(SPATIAL_SHARD_CRYSTAL_ITEM.get());
                output.accept(RESEARCH_NOTES.get());
            }).build());

    public StrangeMatterMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        
        // Register network handler
        NetworkHandler.register();
        
        // Client setup is now handled by ClientModEvents in separate file

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so entity types get registered
        ENTITY_TYPES.register(modEventBus);
        // Register the Deferred Register to the mod event bus so block entity types get registered
        BLOCK_ENTITY_TYPES.register(modEventBus);
        // Register StrangeMatterSounds
        StrangeMatterSounds.SOUND_EVENTS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so menu types get registered
        MENU_TYPES.register(modEventBus);
        // Register the Deferred Register to the mod event bus so recipe types get registered
        RECIPE_TYPES.register(modEventBus);
        // Register the Deferred Register to the mod event bus so recipe serializers get registered
        RECIPE_SERIALIZERS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so attributes get registered
        ATTRIBUTES.register(modEventBus);
        // Register the Deferred Register to the mod event bus so features get registered
        FEATURES.register(modEventBus);
        // Register the Deferred Register to the mod event bus so placement modifiers get registered
        PLACEMENT_MODIFIERS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so structure types get registered
        STRUCTURE_TYPES.register(modEventBus); // Register structure types
        // Register the Deferred Register to the mod event bus so particle types get registered
        PARTICLE_TYPES.register(modEventBus);
        // Register the Deferred Register to the mod event bus so biome modifier serializers get registered
        BIOME_MODIFIER_SERIALIZERS.register(modEventBus);
        // Register villager professions and POI types
        VILLAGER_PROFESSIONS.register(modEventBus);
        POI_TYPES.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("Strange Matter mod initialized - reality anomalies detected!");
        
        // Initialize scannable object registry and research nodes on both client and server
        event.enqueueWork(() -> {
            net.reversteam.strangematter.research.ScannableObjectRegistry.init();
            // Initialize research nodes on server as well (not just in client screens)
            net.reversteam.strangematter.research.ResearchNodeRegistry.initializeDefaultNodes();
            
            // Register custom advancement triggers
            net.minecraft.advancements.CriteriaTriggers.register(ANOMALY_EFFECT_TRIGGER);
            net.minecraft.advancements.CriteriaTriggers.register(SCAN_ANOMALY_TRIGGER);
            net.minecraft.advancements.CriteriaTriggers.register(COMPLETE_RESEARCH_CATEGORY_TRIGGER);
        });
    }
    
    // Client setup moved to net.reversteam.strangematter.client.ClientModEvents

    // Register commands
    @SubscribeEvent
    public void registerCommands(net.minecraftforge.event.RegisterCommandsEvent event) {
        try {
            AnomalyCommand.register(event.getDispatcher());
        } catch (Exception e) {
            System.err.println("Failed to register AnomalyCommand: " + e.getMessage());
            e.printStackTrace();
        }
        ResearchCommand.register(event.getDispatcher());
        net.reversteam.strangematter.command.ResearchPointsCommand.register(event.getDispatcher());
        
        // Register test command for gravity anomaly feature
        event.getDispatcher().register(Commands.literal("test_gravity_anomaly")
            .requires(source -> source.hasPermission(2))
            .executes(context -> {
                var source = context.getSource();
                var level = source.getLevel();
                var pos = source.getPosition();

                if (pos != null) {
                    // Test the gravity anomaly feature placement
                    int surfaceY = level.getHeight(net.minecraft.world.level.levelgen.Heightmap.Types.WORLD_SURFACE, (int)pos.x, (int)pos.z);
                    BlockPos surfacePos = new BlockPos((int)pos.x, surfaceY, (int)pos.z);
                    
                    // Find the actual solid ground below the surface
                    BlockPos groundPos = surfacePos;
                    while (groundPos.getY() > level.getMinBuildHeight() + 10) {
                        var blockState = level.getBlockState(groundPos);
                        if (blockState.isSolid() && !blockState.isAir() && 
                            !blockState.getBlock().getDescriptionId().contains("leaves")) {
                            break; // Found solid ground (grass, dirt, stone, sand, etc. are all fine)
                        }
                        groundPos = groundPos.below();
                    }
                    
                    // Check if we found valid solid ground
                    if (groundPos.getY() <= level.getMinBuildHeight() + 10) {
                        source.sendFailure(Component.literal("No solid ground found below surface at " + surfacePos));
                        return 1;
                    }
                    
                    // Spawn the anomaly a few blocks above the surface
                    int anomalyY = surfaceY + 3;
                    final BlockPos anomalyPos = new BlockPos((int)pos.x, anomalyY, (int)pos.z);
                    final BlockPos finalGroundPos = groundPos;

                    // Place anomalous grass in a patchy circle following terrain contour
                    int radius = 3;
                    for (int x = -radius; x <= radius; x++) {
                        for (int z = -radius; z <= radius; z++) {
                            double distance = Math.sqrt(x * x + z * z);
                            
                            // Only place grass within the circle and with some randomness for patchiness
                            if (distance <= radius && level.random.nextFloat() < 0.7f) { // 70% chance for patchiness
                                // Use WorldGenUtils to find the proper position for anomalous grass
                                BlockPos grassPos = net.reversteam.strangematter.worldgen.WorldGenUtils.findAnomalousGrassPosition(level, 
                                    (int)pos.x + x, (int)pos.z + z);
                                if (grassPos != null) {
                                    level.setBlock(grassPos, ANOMALOUS_GRASS_BLOCK.get().defaultBlockState(), 3);
                                }
                            }
                        }
                    }

                    // Spawn the gravity anomaly entity above the surface
                    GravityAnomalyEntity anomaly = new GravityAnomalyEntity(GRAVITY_ANOMALY.get(), level);
                    anomaly.moveTo(anomalyPos.getX() + 0.5, anomalyPos.getY(), anomalyPos.getZ() + 0.5, 0.0f, 0.0f);
                    
                    if (level.addFreshEntity(anomaly)) {
                        source.sendSuccess(() -> Component.literal("Test gravity anomaly placed at " + anomalyPos + " (ground at " + finalGroundPos + ")"), true);
                    } else {
                        source.sendFailure(Component.literal("Failed to place gravity anomaly at " + anomalyPos));
                    }
                }

                return 1;
            }));
    }

    // Add items to creative tabs
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
        {
            event.accept(ANOMALOUS_GRASS_ITEM.get());
            event.accept(RESONANCE_CONDENSER_ITEM.get());
            event.accept(REALITY_FORGE_ITEM.get());
            event.accept(PARADOXICAL_ENERGY_CELL_ITEM.get());
            event.accept(RIFT_STABILIZER_ITEM.get());
        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES)
        {
            event.accept(FIELD_SCANNER.get());
            event.accept(ANOMALY_RESONATOR.get());
            event.accept(ECHOFORM_IMPRINTER.get());
            event.accept(RESEARCH_TABLET.get());
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("Strange Matter anomalies are spreading across the server...");
    }
    
    @SubscribeEvent
    public void onServerStopping(net.minecraftforge.event.server.ServerStoppingEvent event)
    {
        // Server cleanup - sound manager cleanup is handled on client side
    }

    // Client events moved to separate class to prevent client class loading on server
    // See: net.reversteam.strangematter.client.ClientModEvents
    
    // Beam state tracking for multiplayer visibility
    private static final java.util.Map<net.minecraft.world.entity.player.Player, Boolean> playerBeamStates = new java.util.concurrent.ConcurrentHashMap<>();
    
    public static void setPlayerBeamState(net.minecraft.world.entity.player.Player player, boolean isActive) {
        if (isActive) {
            playerBeamStates.put(player, true);
        } else {
            playerBeamStates.remove(player);
        }
    }
    
    public static boolean isPlayerUsingBeam(net.minecraft.world.entity.player.Player player) {
        return playerBeamStates.containsKey(player);
    }
    
    public static java.util.Set<net.minecraft.world.entity.player.Player> getPlayersUsingBeam() {
        return playerBeamStates.keySet();
    }
}
