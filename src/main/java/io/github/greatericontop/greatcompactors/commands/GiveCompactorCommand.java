package io.github.greatericontop.greatcompactors.commands;

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

import io.github.greatericontop.greatcompactors.internal.Util;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GiveCompactorCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cSorry, players only!");
            return true;
        }
        ItemStack compactor = new ItemStack(Material.DROPPER, 1);
        ItemMeta im = compactor.getItemMeta();
        im.setDisplayName("§aPersonal Compactor");
        im.addEnchant(Enchantment.LUCK_OF_THE_SEA, 1, true);
        Util.setAsCompactor(im);
        compactor.setItemMeta(im);
        player.getInventory().addItem(compactor);
        player.sendMessage("§3Gave you a compactor");
        return true;
    }

}
