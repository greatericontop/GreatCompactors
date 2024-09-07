package io.github.greatericontop.greatcompactors.internal;

/*
 * Copyright (C) 2024-present greateric.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty  of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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

    public static void removeWithoutRegardingIM(Inventory inv, Material mat, int amount) {
        int leftToRemove = amount;
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack stackHere = inv.getItem(i);
            if (stackHere == null || stackHere.getType() != mat)  continue;
            int amountHere = stackHere.getAmount();
            if (amountHere <= leftToRemove) {
                inv.clear(i);
                leftToRemove -= amountHere;
            } else {
                stackHere.setAmount(amountHere - leftToRemove);
                inv.setItem(i, stackHere);
                break;
            }
        }
    }

}
