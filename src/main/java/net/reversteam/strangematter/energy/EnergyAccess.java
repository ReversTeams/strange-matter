package net.reversteam.strangematter.energy;
import java.util.Optional;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.reversteam.strangematter.StrangeMatterMod;
import net.reversteam.strangematter.block.*;
public final class EnergyAccess {
 private EnergyAccess() {}
 public static Optional<IEnergyStorage> get(BlockEntity entity, Direction side) {
  return entity.getLevel() == null ? Optional.empty() : Optional.ofNullable(entity.getLevel().getCapability(Capabilities.EnergyStorage.BLOCK, entity.getBlockPos(), side));
 }
 public static void register(RegisterCapabilitiesEvent event) {
  StrangeMatterMod.BLOCK_ENTITY_TYPES.getEntries().forEach(holder -> registerType(event, holder.get()));
 }
 private static <T extends BlockEntity> void registerType(RegisterCapabilitiesEvent event, BlockEntityType<T> type) {
  event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, type, (entity, side) -> {
   if (entity instanceof BaseMachineBlockEntity machine) return machine.energyForSide(side);
   if (entity instanceof ParadoxicalEnergyCellBlockEntity cell) return cell.getEnergyStorage();
   return null;
  });
 }
}
