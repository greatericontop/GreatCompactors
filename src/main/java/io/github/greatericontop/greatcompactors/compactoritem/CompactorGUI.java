package io.github.greatericontop.greatcompactors.compactoritem;

import io.github.greatericontop.greatcompactors.GreatCompactors;
import io.github.greatericontop.greatcompactors.internal.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;

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

        //int size = plugin.personalCompactorMaxSlots;
        //Inventory gui = Bukkit.createInventory(player, round up to x9, TITLE);
    }

}
