package io.github.greatericontop.greatcompactors.internal;

import io.github.greatericontop.greatcompactors.GreatCompactors;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerdataManager {

    private final GreatCompactors plugin;
    public PlayerdataManager(GreatCompactors plugin) {
        this.plugin = plugin;
    }

    public List<ItemStack> getCompactorInventory(String key) {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        String string = plugin.playerdata.getString(key);
        if (string == null) {
            return null;
        }
        try {
            yamlConfiguration.loadFromString(string);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return (List<ItemStack>) yamlConfiguration.get("compactorinventory");
    }

    public void setCompactorInventory(String key, List<ItemStack> playerdata) {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        yamlConfiguration.set("compactorinventory", playerdata);
        plugin.playerdata.set(key, yamlConfiguration.saveToString());
    }

    public void saveAll() {
        plugin.playerdata.save();
    }

}
