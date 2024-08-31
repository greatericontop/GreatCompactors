package io.github.greatericontop.greatcompactors;

import io.github.greatericontop.greatcompactors.internal.CompactorRecipe;
import io.github.greatericontop.greatcompactors.internal.PlayerdataManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GreatCompactors extends JavaPlugin {
    private static final String PLAYERDATA_YML = "playerdata.yml";

    public int personalCompactorMaxSlots = -1;
    private int autoSaveInterval = -1;
    public Map<Material, List<CompactorRecipe>> recipes = new HashMap<>();

    public YamlConfiguration playerdata = null;

    public PlayerdataManager playerdataManager = null;

    @Override
    public void onEnable() {

        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        loadConfig();

        File playerdataFile = new File(this.getDataFolder(), PLAYERDATA_YML);
        playerdata = YamlConfiguration.loadConfiguration(playerdataFile);

        playerdataManager = new PlayerdataManager(this);

        Bukkit.getScheduler().runTaskTimer(this, this::saveAll, autoSaveInterval, autoSaveInterval);

    }

    @Override
    public void onDisable() {
        saveAll();
    }

    public void saveAll() {
        this.saveConfig();
        try {
            playerdata.save(new File(this.getDataFolder(), PLAYERDATA_YML));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadConfig() {
        int rawMaxSlots = this.getConfig().getInt("personal-compactor-max-slots");
        if (rawMaxSlots < 1) {
            this.getLogger().warning("personal-compactor-max-slots must be >= 1");
            rawMaxSlots = 1;
        }
        if (rawMaxSlots > 54) {
            this.getLogger().warning("personal-compactor-max-slots must be <= 54");
            rawMaxSlots = 54;
        }
        personalCompactorMaxSlots = rawMaxSlots;

        int rawAutoSaveInterval = this.getConfig().getInt("autosave-interval");
        if (rawAutoSaveInterval < 20) {
            this.getLogger().warning("autosave-interval must be >= 20t (Saving every second is still not recommended!)");
            rawAutoSaveInterval = 20;
        }
        autoSaveInterval = rawAutoSaveInterval;

        // RECIPES
        recipes.clear();
        List<Map<String, Object>> rawCompactorRecipes;
        try {
            rawCompactorRecipes = (List<Map<String, Object>>) this.getConfig().getList("personal-compactor-recipes");
        } catch (ClassCastException e) {
            this.getLogger().severe("Malformed personal-compactor-recipes! Please make sure your config.yml is formatted properly! Assuming no recipes exist.");
            rawCompactorRecipes = new ArrayList<>();
        }
        if (rawCompactorRecipes == null) {
            this.getLogger().severe("personal-compactor-recipes section of config.yml is null or nonexistent! Assuming no recipes exist.");
            rawCompactorRecipes = new ArrayList<>();
        }
        for (Map<String, Object> recipeData : rawCompactorRecipes) {
            Object rawResultName = recipeData.get("result");
            if (!(rawResultName instanceof String)) {
                this.getLogger().severe("Result name in compactor recipe must be a string! Skipping!");
                continue;
            }
            Material resultMat;
            try {
                resultMat = Material.valueOf((String) rawResultName);
            } catch (IllegalArgumentException e) {
                this.getLogger().severe(String.format("Invalid result name (%s)! Make sure it is in the Material enum! Skipping!", rawResultName));
                continue;
            }
            Object rawResultCount = recipeData.get("result-count");
            if (!(rawResultCount instanceof Integer)) {
                this.getLogger().severe("Result count in compactor recipe must be an integer! Skipping!");
                continue;
            }
            int resultCount = (int) rawResultCount;
            Map<String, Integer> ingredients;
            try {
                ingredients = (Map<String, Integer>) recipeData.get("ingredients");
            } catch (ClassCastException e) {
                this.getLogger().severe("Malformed ingredients! Please make sure your config.yml is formatted properly! Skipping!");
                continue;
            }
            if (ingredients == null) {
                this.getLogger().severe("Ingredients in compactor recipe are null or nonexistent! Skipping!");
                continue;
            }
            Map<Material, Integer> requiredIngredients = new HashMap<>();
            for (Map.Entry<String, Integer> rawIngredient : ingredients.entrySet()) {
                String ingredientName = rawIngredient.getKey();
                int ingredientCount = rawIngredient.getValue();
                Material ingredientMat;
                try {
                    ingredientMat = Material.valueOf(ingredientName);
                } catch (IllegalArgumentException e) {
                    this.getLogger().severe(String.format("Invalid ingredient name (%s)! Make sure it is in the Material enum! Skipping!", ingredientName));
                    continue;
                }
                requiredIngredients.put(ingredientMat, ingredientCount);
            }
            // Add it
            CompactorRecipe recipe = new CompactorRecipe(resultMat, resultCount, requiredIngredients);
            if (!recipes.containsKey(resultMat)) {
                recipes.put(resultMat, new ArrayList<>());
            }
            recipes.get(resultMat).add(recipe);
        }

    }

}
