package io.github.greatericontop.greatcompactors.internal;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class Util {

    public static final NamespacedKey pdcKey = new NamespacedKey("greatcompactors", "compactor");

    public static boolean checkItem(ItemStack item) {
        ItemMeta im = item.getItemMeta();
        return im != null && im.getPersistentDataContainer().has(pdcKey);
    }

    public static void setAsCompactor(ItemMeta im) {
        im.getPersistentDataContainer().set(pdcKey, PersistentDataType.BYTE, (byte) 1);
    }

}
