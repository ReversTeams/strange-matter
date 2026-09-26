package net.reversteam.strangematter.worldgen;

import net.reversteam.strangematter.Config;
import net.reversteam.strangematter.StrangeMatterMod;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.WorldGenLevel;

/**
 * Utility class for efficient world generation operations.
 * Provides optimized methods for common world generation tasks.
 */
public class WorldGenUtils {
    
    /**
     * Efficiently checks if a block state represents solid ground.
     * Avoids expensive string operations by using block tags and direct comparisons.
     * Excludes all tree-related blocks (logs, leaves, etc.)
     */
    public static boolean isSolidGround(BlockState state) {
        if (!state.canOcclude() || state.isAir()) {
            return false;
        }
        return true;
        // Exclude all tree-related blocks (logs, leaves, etc.)
        //return !state.is(Blocks.OAK_LEAVES) && 
        //       !state.is(Blocks.ACACIA_LEAVES) &&
        //       !state.is(Blocks.BIRCH_LEAVES) &&
        //       !state.is(Blocks.DARK_OAK_LEAVES) &&
        //       !state.is(Blocks.JUNGLE_LEAVES) &&
        //       !state.is(Blocks.SPRUCE_LEAVES) &&
        //       !state.is(Blocks.CHERRY_LEAVES) &&
        //       !state.is(Blocks.MANGROVE_LEAVES) &&
        //       // Exclude all log types
        //       !state.is(Blocks.OAK_LOG) &&
        //       !state.is(Blocks.ACACIA_LOG) &&
        //       !state.is(Blocks.BIRCH_LOG) &&
        //       !state.is(Blocks.DARK_OAK_LOG) &&
        //       !state.is(Blocks.JUNGLE_LOG) &&
        //       !state.is(Blocks.SPRUCE_LOG) &&
        //       !state.is(Blocks.CHERRY_LOG) &&
        //       !state.is(Blocks.MANGROVE_LOG) &&
        //       // Exclude stripped logs
        //       !state.is(Blocks.STRIPPED_OAK_LOG) &&
        //       !state.is(Blocks.STRIPPED_ACACIA_LOG) &&
        //       !state.is(Blocks.STRIPPED_BIRCH_LOG) &&
        //       !state.is(Blocks.STRIPPED_DARK_OAK_LOG) &&
        //       !state.is(Blocks.STRIPPED_JUNGLE_LOG) &&
        //       !state.is(Blocks.STRIPPED_SPRUCE_LOG) &&
        //       !state.is(Blocks.STRIPPED_CHERRY_LOG) &&
        //       !state.is(Blocks.STRIPPED_MANGROVE_LOG) &&
        //       // Exclude wood blocks
        //       !state.is(Blocks.OAK_WOOD) &&
        //       !state.is(Blocks.ACACIA_WOOD) &&
        //       !state.is(Blocks.BIRCH_WOOD) &&
        //       !state.is(Blocks.DARK_OAK_WOOD) &&
        //       !state.is(Blocks.JUNGLE_WOOD) &&
        //       !state.is(Blocks.SPRUCE_WOOD) &&
        //       !state.is(Blocks.CHERRY_WOOD) &&
        //       !state.is(Blocks.MANGROVE_WOOD) &&
        //       // Exclude stripped wood
        //       !state.is(Blocks.STRIPPED_OAK_WOOD) &&
        //       !state.is(Blocks.STRIPPED_ACACIA_WOOD) &&
        //       !state.is(Blocks.STRIPPED_BIRCH_WOOD) &&
        //       !state.is(Blocks.STRIPPED_DARK_OAK_WOOD) &&
        //       !state.is(Blocks.STRIPPED_JUNGLE_WOOD) &&
        //       !state.is(Blocks.STRIPPED_SPRUCE_WOOD) &&
        //       !state.is(Blocks.STRIPPED_CHERRY_WOOD) &&
        //       !state.is(Blocks.STRIPPED_MANGROVE_WOOD);
    }
    
    /**
     * Checks if a block state is a surface block that should be preserved (snow, leaves, etc.)
     * These blocks should not be replaced by anomalous grass.
     */
    public static boolean isSurfaceBlock(BlockState state) {
        return state.is(Blocks.SNOW) ||
               state.is(Blocks.OAK_LEAVES) ||
               state.is(Blocks.ACACIA_LEAVES) ||
               state.is(Blocks.BIRCH_LEAVES) ||
               state.is(Blocks.DARK_OAK_LEAVES) ||
               state.is(Blocks.JUNGLE_LEAVES) ||
               state.is(Blocks.SPRUCE_LEAVES) ||
               state.is(Blocks.CHERRY_LEAVES) ||
               state.is(Blocks.MANGROVE_LEAVES);
    }
    
    /**
     * Checks if a block state is suitable for placing anomalous grass.
     * Returns true if the block is solid ground and not a surface block that should be preserved.
     */
    public static boolean isSuitableForAnomalousGrass(BlockState state) {
        return true;//isSolidGround(state) && !isSurfaceBlock(state);
    }
    
    /**
     * Checks if a block state is actual ground (dirt, grass, stone, etc.) that can support grass.
     * This excludes leaves, logs, snow, and other non-ground blocks.
     */
    public static boolean isActualGround(BlockState state) {
        if (!state.canOcclude() || state.isAir()) {
            return false;
        }
        
        // First check if it's a surface block that should be excluded
        if (isSurfaceBlock(state)) {
            return false;
        }

        return true;
    }
    
    /**
     * Efficiently finds solid ground below a given position.
     * Stops at a reasonable depth to avoid infinite loops.
     * 
     * @param level The world generation level
     * @param startPos The starting position to search from
     * @return The position of solid ground, or null if none found
     */
    public static BlockPos findSolidGround(WorldGenLevel level, BlockPos startPos) {
        BlockPos pos = startPos;
        int minY = level.getMinBuildHeight() + 10;
        
        while (pos.getY() > minY) {
            BlockState state = level.getBlockState(pos);
            if (isSolidGround(state)) {
                return pos;
            }
            pos = pos.below();
        }
        
        return null; // No solid ground found
    }
    
    /**
     * Finds the surface position and solid ground in one efficient operation.
     * 
     * @param level The world generation level
     * @param x X coordinate
     * @param z Z coordinate
     * @return SurfaceInfo containing both surface and ground positions, or null if invalid
     */
    public static SurfaceInfo findSurfaceAndGround(WorldGenLevel level, int x, int z) {
        int surfaceY = level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);
        BlockPos surfacePos = new BlockPos(x, surfaceY, z);
        
        BlockPos groundPos = findSolidGround(level, surfacePos);
        if (groundPos == null) {
            return null;
        }
        
        return new SurfaceInfo(surfacePos, groundPos);
    }
    
    /**
     * Finds the best position for placing anomalous grass.
     * Tries to place at surface level if suitable, otherwise places on actual ground below.
     * 
     * @param level The world generation level
     * @param x X coordinate
     * @param z Z coordinate
     * @return The best position for anomalous grass, or null if none found
     */
    public static BlockPos findAnomalousGrassPosition(WorldGenLevel level, int x, int z) {
        int surfaceY = level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);
        BlockPos surfacePos = new BlockPos(x, surfaceY, z);
        BlockState surfaceState = level.getBlockState(surfacePos);
        
        // If the surface is actual ground (dirt, grass, stone, etc.), use it
        if (isActualGround(surfaceState)) {
            return surfacePos;
        }
        
        // If surface is snow or leaves, we need to find actual ground below
        // Keep searching deeper until we find actual ground or hit bedrock
        BlockPos currentPos = surfacePos;
        int minY = level.getMinBuildHeight() + 10;
        
        while (currentPos.getY() > minY) {
            currentPos = currentPos.below();
            BlockState state = level.getBlockState(currentPos);
            
            // If we find actual ground, use it
            if (isActualGround(state)) {
                return currentPos;
            }
        }
        return null; // No suitable position found
    }
    
    /**
     * Places anomalous grass in a patchy circle around the origin position.
     * Uses config values and the provided parameters for terrain modification.
     * 
     * @param level The world generation level
     * @param origin The center position for grass placement
     * @param radius The radius of the circle
     * @param chance The chance (0.0-1.0) for each block to have grass placed
     * @param random The random source for patchiness
     */
    public static void placeAnomalousGrassPatch(WorldGenLevel level, BlockPos origin, int radius, float chance, RandomSource random) {
        if (!Config.enableAnomalousGrass) {
            return;
        }
        
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                double distance = Math.sqrt(x * x + z * z);
                
                // Only place grass within the circle and with some randomness for patchiness
                if (distance <= radius && random.nextFloat() < chance) {
                    BlockPos grassPos = findAnomalousGrassPosition(level, 
                        origin.getX() + x, origin.getZ() + z);
                    
                    if (grassPos != null) {
                        level.setBlock(grassPos, StrangeMatterMod.ANOMALOUS_GRASS_BLOCK.get().defaultBlockState(), 3);
                    }
                }
            }
        }
    }
    
    /**
     * Places anomaly ores concentrated below the anomaly with depth-based probability.
     * 50% chance to generate a small crater with ores and a shard crystal on top.
     */
    public static void placeAnomalyOres(WorldGenLevel level, BlockPos anomalyPos, int radius, RandomSource random, Block shardOreBlock, Block shardCrystalBlock, boolean hasCrater) {
        int anomalyY = anomalyPos.getY();
        int craterRadius = 3;
        
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                double distance = Math.sqrt(x * x + z * z);
                if (distance > radius) continue;
                
                int cx = anomalyPos.getX() + x;
                int cz = anomalyPos.getZ() + z;
                
                if (hasCrater && distance <= craterRadius) {
                    // Crater logic: bowl shape depression
                    int surfaceY = level.getHeight(Heightmap.Types.WORLD_SURFACE, cx, cz);
                    int depth = (int) (craterRadius - distance) + 1;
                    
                    // Clear blocks to form the crater
                    for (int d = 0; d < depth; d++) {
                        BlockPos clearPos = new BlockPos(cx, surfaceY - d, cz);
                        level.setBlock(clearPos, Blocks.AIR.defaultBlockState(), 3);
                    }
                    
                    // Place ore at the bottom of the crater
                    int bottomY = surfaceY - depth;
                    BlockPos bottomPos = new BlockPos(cx, bottomY, cz);
                    BlockPos solidBelow = bottomPos.below();
                    
                    if (!level.getBlockState(solidBelow).canOcclude()) {
                        level.setBlock(solidBelow, Blocks.STONE.defaultBlockState(), 3);
                    }
                    
                    Block oreToPlace = random.nextBoolean() ? StrangeMatterMod.RESONITE_ORE_BLOCK.get() : shardOreBlock;
                    level.setBlock(bottomPos, oreToPlace.defaultBlockState(), 3);
                    
                    // Place shard crystal on top of the ore (center or 60% chance)
                    if (distance < 1.5 || random.nextFloat() < 0.6f) {
                        BlockPos crystalPos = bottomPos.above();
                        if (level.getBlockState(crystalPos).isAir()) {
                            level.setBlock(crystalPos, shardCrystalBlock.defaultBlockState(), 3);
                        }
                    }
                } else {
                    // Normal depth-based ore column logic
                    int startY = anomalyY - 1;
                    int endY = anomalyY - 40;
                    int minY = level.getMinBuildHeight();
                    if (endY < minY) endY = minY;
                    
                    BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos(cx, startY, cz);
                    
                    while (mutable.getY() >= endY) {
                        int depthBelowAnomaly = anomalyY - mutable.getY();
                        double chance = getOreChance(depthBelowAnomaly);
                        
                        if (random.nextDouble() < chance) {
                            BlockState state = level.getBlockState(mutable);
                            if (canReplaceWithOre(state)) {
                                Block oreToPlace = random.nextFloat() < 0.6f ? StrangeMatterMod.RESONITE_ORE_BLOCK.get() : shardOreBlock;
                                level.setBlock(mutable.immutable(), oreToPlace.defaultBlockState(), 3);
                                
                                // Place a short continuous column (1-3 blocks)
                                int columnLength = 1 + random.nextInt(3);
                                for (int i = 1; i < columnLength; i++) {
                                    mutable.move(0, -1, 0);
                                    if (mutable.getY() >= minY && canReplaceWithOre(level.getBlockState(mutable))) {
                                        level.setBlock(mutable.immutable(), oreToPlace.defaultBlockState(), 3);
                                    }
                                }
                                break; // Move to next x,z after placing a column
                            }
                        }
                        mutable.move(0, -1, 0);
                    }
                }
            }
        }
    }
    
    /**
     * Calculates ore spawn chance based on depth below the anomaly.
     * High chance (85%) from 1 to 15 blocks below, tapering down to 10% at 40 blocks below.
     */
    private static double getOreChance(int depthBelowAnomaly) {
        if (depthBelowAnomaly < 1) return 0.0;
        if (depthBelowAnomaly <= 15) return 0.85; 
        if (depthBelowAnomaly <= 40) {
            // Linearly decrease from 0.85 at depth 15 to 0.10 at depth 40
            return 0.85 - (0.75 * (depthBelowAnomaly - 15) / 25.0);
        }
        return 0.0;
    }
    
    private static boolean canReplaceWithOre(BlockState state) {
        return Config.anomalyOreReplacementBlocks.contains(state.getBlock());
    }
    
    public static class SurfaceInfo {
        public final BlockPos surfacePos;
        public final BlockPos groundPos;
        
        public SurfaceInfo(BlockPos surfacePos, BlockPos groundPos) {
            this.surfacePos = surfacePos;
            this.groundPos = groundPos;
        }
    }
}