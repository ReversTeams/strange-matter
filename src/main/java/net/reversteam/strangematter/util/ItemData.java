package net.reversteam.strangematter.util;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.nbt.*;
/** Copy-on-write access to the mod's legacy custom item data. */
public final class ItemData {
 private ItemData() {}
 public static boolean has(ItemStack stack) { return stack.has(DataComponents.CUSTOM_DATA); }
 public static CompoundTag get(ItemStack stack) { var data = stack.get(DataComponents.CUSTOM_DATA); return data == null ? null : data.copyTag(); }
 public static CompoundTag getOrCreate(ItemStack stack) { var tag = get(stack); return tag == null ? new CompoundTag() : tag; }
 public static void set(ItemStack stack, CompoundTag tag) { if(tag == null || tag.isEmpty()) stack.remove(DataComponents.CUSTOM_DATA); else stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag)); }
 public static void remove(ItemStack stack, CompoundTag tag, String key) { tag.remove(key); set(stack, tag); }
 public static Tag put(ItemStack stack, CompoundTag tag, String key, Tag value) { var old = tag.put(key, value); set(stack, tag); return old; }
 public static void putUUID(ItemStack stack, CompoundTag tag, String key, java.util.UUID value) { tag.putUUID(key, value); set(stack, tag); }
 public static void putInt(ItemStack stack, CompoundTag tag, String key, int value) { tag.putInt(key, value); set(stack, tag); }
 public static void putBoolean(ItemStack stack, CompoundTag tag, String key, boolean value) { tag.putBoolean(key, value); set(stack, tag); }
 public static void putString(ItemStack stack, CompoundTag tag, String key, String value) { tag.putString(key, value); set(stack, tag); }
 public static void putLong(ItemStack stack, CompoundTag tag, String key, long value) { tag.putLong(key, value); set(stack, tag); }
}
