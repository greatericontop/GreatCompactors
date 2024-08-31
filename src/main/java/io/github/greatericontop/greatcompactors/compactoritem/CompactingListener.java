package io.github.greatericontop.greatcompactors.compactoritem;

import io.github.greatericontop.greatcompactors.GreatCompactors;
import io.github.greatericontop.greatcompactors.internal.CompactorRecipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CompactingListener implements Listener {
    private final Map<UUID, Boolean> cooldowns = new HashMap<>();

    private final GreatCompactors plugin;
    public CompactingListener(GreatCompactors plugin) {
        this.plugin = plugin;
    }

    @EventHandler()
    public void onItemPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player))  return;
        if (cooldowns.getOrDefault(player.getUniqueId(), false))  return;

        // Get the recipes we're going to try to make
        List<CompactorRecipe> toMake = new ArrayList<>();
        for (ItemStack stack : plugin.playerdataManager.getCompactorInventory(player.getUniqueId())) {
            if (stack == null || stack.getType() == Material.AIR)  continue;
            toMake.addAll(plugin.recipes.get(stack.getType()));
        }

        // Greedily the recipes (in this order)
        for (CompactorRecipe recipe : toMake) {
            // Cache inventory contents
            Map<Material, Integer> inventoryContents = new HashMap<>();
            for (ItemStack stack : player.getInventory().getContents()) {
                if (stack == null)  continue;
                inventoryContents.put(stack.getType(), inventoryContents.getOrDefault(stack.getType(), 0) + stack.getAmount());
            }
            // Calculate amount possible
            Map<Material, Integer> quantitiesDemanded = recipe.ingredients();
            Map<Material, Integer> numberPossible = new HashMap<>(); // number possible (= in inventory / demanded) for each ingredient
            for (Map.Entry<Material, Integer> entry : quantitiesDemanded.entrySet()) {
                Material mat = entry.getKey();
                int inInventory = inventoryContents.getOrDefault(mat, 0);
                numberPossible.put(mat, inInventory / entry.getValue());
            }
            int amountToMake = numberPossible.values().stream().min(Integer::compareTo).orElse(0);
            if (amountToMake == 0) {
                continue;
            }
            // Make it
            for (Map.Entry<Material, Integer> entry : quantitiesDemanded.entrySet()) {
                Material mat = entry.getKey();
                int costEach = entry.getValue();
                int toRemove = costEach * amountToMake;
                player.getInventory().remove(new ItemStack(mat, toRemove));
            }
            ItemStack result = new ItemStack(recipe.result(), amountToMake * recipe.resultCount());
            player.getInventory().addItem(result);
            player.sendMessage(String.format("§7[Debug] compacted %s x%d (x%d=%d)", recipe.result(), recipe.resultCount(), amountToMake, amountToMake * recipe.resultCount()));
        }

        cooldowns.put(player.getUniqueId(), true);
        Bukkit.getScheduler().runTaskLater(plugin, () -> cooldowns.put(player.getUniqueId(), false), 8L);
    }

}
