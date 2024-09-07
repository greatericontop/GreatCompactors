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

import io.github.greatericontop.greatcompactors.GreatCompactors;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class PlayerdataManager {

    private final GreatCompactors plugin;
    public PlayerdataManager(GreatCompactors plugin) {
        this.plugin = plugin;
    }

    public List<ItemStack> getCompactorInventory(UUID player) {
        String key = player.toString();
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

    public void setCompactorInventory(UUID player, List<ItemStack> playerdata) {
        String key = player.toString();
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        yamlConfiguration.set("compactorinventory", playerdata);
        plugin.playerdata.set(key, yamlConfiguration.saveToString());
    }

}
