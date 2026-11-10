package net.reversteam.strangematter.item;
import java.util.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.*;
import net.reversteam.strangematter.StrangeMatterMod;
public final class TinfoilHatArmorMaterial {
 public static final DeferredRegister<ArmorMaterial> MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, StrangeMatterMod.MODID);
 public static final DeferredHolder<ArmorMaterial,ArmorMaterial> TINFOIL_HAT = MATERIALS.register("tinfoil_hat", () -> new ArmorMaterial(
  Map.of(ArmorItem.Type.BOOTS,1,ArmorItem.Type.LEGGINGS,2,ArmorItem.Type.CHESTPLATE,3,ArmorItem.Type.HELMET,1),15,SoundEvents.ARMOR_EQUIP_LEATHER,
  () -> Ingredient.of(StrangeMatterMod.RESONITE_INGOT.get()), List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(StrangeMatterMod.MODID,"tinfoil_hat"))),0,0));
}
