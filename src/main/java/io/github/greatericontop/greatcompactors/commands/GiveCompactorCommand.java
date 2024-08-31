package io.github.greatericontop.greatcompactors.commands;

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
