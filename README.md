# GreatCompactors

GreatCompactors is a plugin that allows players to automatically compact items in their inventory, Hypixel Skyblock style.

## Quick Start for Server Owners

This plugin works out of the box. Simply add it to your plugins folder.

You can give yourself the personal compactor item using `/givecompactor`.
By default, these are unobtainable in survival.
You can make your own method of getting them, or add a crafting recipe with my other plugin [GreatCrafts](https://modrinth.com/plugin/greatcrafts).

The plugin ships by default with some built-in recipes, but you can add more.
See the configuring section below for how to add your own recipes.

## Quick Start for Players

Once you obtain the compactor, you can right click it to open the menu.
In each slot, you can place an item that you want to receive.
This is the output of the craft - for example, placing a gold block in the menu will automatically compact 9 gold ingots into 1 gold block.

Compacting is done automatically every time you pick up an item, with a short cooldown to prevent excessive lag.

## Configuring

The recipe format is as follows:

```
personal-compactor-recipes:

  ...

  - result: <Material>
    result-count: <number to give>
    ingredients:
      <Material 1>: <number required>
      <Material 2>: <number required>
      ...
```

For example, the default recipe for compacting 9 gold ingots into 1 gold block is:

```
  - result: GOLD_BLOCK
    result-count: 1
    ingredients:
      GOLD_INGOT: 9
```

**Important notes:**
- You receive `result-count` of `result` for each set of `ingredients`.
- Each entry in ingredients is the material (`GOLD_INGOT` here) and the amount required (`9` here).
- Each recipe can have as many ingredients as you'd like, and the ingredients required can exceed 64.
- Exact NBT matching is not checked for ingredients.
- For the material names (`result` and in `ingredients`), use the Material enum names. It's usually the Minecraft `/give` ID in all capitals, but you can reference https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html if unsure.

## Commands & Permissions

`/givecompactor` (requires `greatcompactors.givecompactor`) - Gives the player a personal compactor item.

## Bug Reports

Please report any bugs you find on the [issues page](https://github.com/greatericontop/GreatCompactors/issues)

## Compiling

`mvn package`
