package net.reversteam.strangematter.energy;
/** Energy storage owner; block capability exposure is registered by EnergyAccess. */
public class ResonanceEnergyCapability {
 private final ResonanceEnergyStorage energyStorage;
 public ResonanceEnergyCapability(int capacity) { energyStorage = new ResonanceEnergyStorage(capacity); }
 public ResonanceEnergyCapability(int capacity, int maxReceive, int maxExtract) { energyStorage = new ResonanceEnergyStorage(capacity,maxReceive,maxExtract); }
 public ResonanceEnergyStorage getEnergyStorage() { return energyStorage; }
}
