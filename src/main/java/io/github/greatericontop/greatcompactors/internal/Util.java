package io.github.greatericontop.greatcompactors.internal;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;

public class Util {

    public static final NamespacedKey pdcKey = new NamespacedKey("greatcompactors", "compactor");

    public static boolean checkItem(@Nullable ItemStack item) {
        if (item == null)  return false;
        ItemMeta im = item.getItemMeta();
        return im != null && im.getPersistentDataContainer().has(pdcKey);
    }

    public static void setAsCompactor(ItemMeta im) {
        im.getPersistentDataContainer().set(pdcKey, PersistentDataType.BYTE, (byte) 1);
    }

    public static void addIncrementally(Inventory inv, Material mat, int amount) {
        int maxStack = mat.getMaxStackSize();
        while (amount > 0) {
            int toAdd = Math.min(amount, maxStack);
            amount -= toAdd;
            inv.addItem(new ItemStack(mat, toAdd));
        }
    }

    public static void removeIncrementally(Inventory inv, Material mat, int amount) {
        int maxStack = mat.getMaxStackSize();
        while (amount > 0) {
            int toRemove = Math.min(amount, maxStack);
            amount -= toRemove;
            inv.removeItem(new ItemStack(mat, toRemove));
        }
    }

}
