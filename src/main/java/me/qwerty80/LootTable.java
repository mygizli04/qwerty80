package me.qwerty80;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LootTable {
    private Lootable[] regularLoot = {
        new Lootable(new ItemStack(Material.WOODEN_SWORD), 45),
        new Lootable(new ItemStack(Material.STONE_SWORD), 45),
        new Lootable(new ItemStack(Material.GOLDEN_SWORD), 45)
    };
    Loot regular = new Loot(regularLoot);
}
