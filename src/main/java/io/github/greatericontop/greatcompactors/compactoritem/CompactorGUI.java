package io.github.greatericontop.greatcompactors.compactoritem;

import io.github.greatericontop.greatcompactors.GreatCompactors;
import io.github.greatericontop.greatcompactors.internal.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CompactorGUI implements Listener {
    public static final String TITLE = "§2Personal Compactor";

    private final GreatCompactors plugin;
    public CompactorGUI(GreatCompactors plugin) {
        this.plugin = plugin;
    }

    @EventHandler()
    public void onRightClickCompactor(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND)  return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR)  return;
        if (!Util.checkItem(event.getItem())) return;
        Player player = event.getPlayer();

        int size = plugin.personalCompactorMaxSlots;
        int invSize = ((size + 8) / 9) * 9; // round up to multiple of 9
        Inventory gui = Bukkit.createInventory(player, invSize, TITLE);
        for (int i = size; i < invSize; i++) {
            gui.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
        }
        List<ItemStack> savedItems = plugin.playerdataManager.getCompactorInventory(player.getUniqueId());
        if (savedItems == null) {
            savedItems = new ArrayList<>();
        }
        for (int i = 0; i < savedItems.size() && i < size; i++) {
            gui.setItem(i, savedItems.get(i));
        }
        if (savedItems.size() > size) {
            // Drop the rest of the items
            for (int i = savedItems.size() - 1; i >= size; i--) {
                player.getWorld().dropItem(player.getLocation(), savedItems.get(i));
            }
        }
        player.openInventory(gui);
    }

    @EventHandler()
    public void onClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(TITLE))  return;

        // Cancel transactions involving the dead slots
        if (event.getClickedInventory() == event.getView().getTopInventory()) {
            if (event.getSlot() >= plugin.personalCompactorMaxSlots) {
                event.setCancelled(true);
            }
        }

        // Save on every click
        Player player = (Player) event.getWhoClicked();
        List<ItemStack> compactorInventory = plugin.playerdataManager.getCompactorInventory(player.getUniqueId());
        for (int i = 0; i < plugin.personalCompactorMaxSlots; i++) {
            compactorInventory.set(i, event.getView().getTopInventory().getItem(i));
        }
        if (compactorInventory.size() > plugin.personalCompactorMaxSlots) {
            compactorInventory.subList(plugin.personalCompactorMaxSlots, compactorInventory.size()).clear();
        }
        plugin.playerdataManager.setCompactorInventory(player.getUniqueId(), compactorInventory);

    }

}
