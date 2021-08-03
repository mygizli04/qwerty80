package me.qwerty80;

import org.bukkit.inventory.ItemStack;

public class Lootable {
    public int weight = 0; // More weight = more likely to generate
    public ItemStack item;

    public Lootable(ItemStack _item, int _weight) {
        item = _item;
        weight = _weight;
    }

    public Lootable(ItemStack _item) {
        item = _item;
    }
}