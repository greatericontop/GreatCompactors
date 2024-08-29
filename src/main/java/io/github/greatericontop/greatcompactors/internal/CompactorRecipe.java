package io.github.greatericontop.greatcompactors.internal;

import org.bukkit.Material;

import java.util.Map;

public record CompactorRecipe(
        Material result,
        int resultCount,
        Map<Material, Integer> ingredients
) {
}
