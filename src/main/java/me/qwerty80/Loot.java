package me.qwerty80;

import java.util.Random;

import org.bukkit.inventory.ItemStack;

public class Loot {
    Lootable[] loot;
    
    public Loot(Lootable[] _loot) {
        loot = _loot;
    }

    // Thanks, stackoverflow!
    // https://stackoverflow.com/questions/1761626/weighted-random-numbers
    public ItemStack generate() {
        int sum = 0;

        for (Lootable item : loot) {
            sum += item.weight;
        }

        int random = new Random().nextInt(sum);

        for (Lootable item : loot) {
            if (item.weight < random) {
                return item.item;
            }
            else {
                random -= item.weight;
            }
        }

        return null; // Should never happen.
    }
}

class Lootable {
    public int weight = 50; // % chance of generating
    public ItemStack item;

    public Lootable(ItemStack _item, int _weight) {
        item = _item;
        weight = _weight;
    }

    public Lootable(ItemStack _item) {
        item = _item;
    }
}