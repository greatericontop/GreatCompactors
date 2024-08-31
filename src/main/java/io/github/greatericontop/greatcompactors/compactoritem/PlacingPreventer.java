package io.github.greatericontop.greatcompactors.compactoritem;

import io.github.greatericontop.greatcompactors.internal.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlacingPreventer implements Listener {

    @EventHandler()
    public void onPlace(BlockPlaceEvent event) {
        if (
                (event.getHand() == EquipmentSlot.HAND && Util.checkItem(event.getPlayer().getInventory().getItemInMainHand()))
                || (event.getHand() == EquipmentSlot.OFF_HAND && Util.checkItem(event.getPlayer().getInventory().getItemInOffHand()))
        ) {
            event.setCancelled(true);
        }
    }

}
