package io.github.greatericontop.greatcompactors;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GreatCompactors extends JavaPlugin {

    @Override
    public void onEnable() {

//        this.saveDefaultConfig();
//        this.getConfig().options().copyDefaults(true);
//        this.saveConfig();
//        loadConfig();

    }

    public void loadConfig() {
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
            // Input & input validation (pain)
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
            //

            // TODO: do something
        }

    }

}
