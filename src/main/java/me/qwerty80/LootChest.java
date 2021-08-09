package me.qwerty80;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;


public class LootChest {
    public LootTable loot;
    boolean rare;

    public LootChest(LootTable _loot, boolean _rare) {
        loot = _loot;
        rare = _rare;
    }


    public Inventory generate() {
        // Decide which items to put in the chest
        ItemStack[] newItems = loot.getItemStack();
        Component name;

        if (rare) {
            name = Component.text("Rare Chest");
        }
        else {
            name = Component.text("Regular Chest");
        }

        // Add the items to the chest
        Inventory inventory = Bukkit.getServer().createInventory(null, 27, name);

        Integer[] chosenSlots = new Integer[newItems.length];
        for (int i = 0; i < newItems.length; i++) {
            Random random = new Random();
            Integer chosenSlot = random.nextInt(inventory.getSize());
            while (Utils.arrayIncludes(chosenSlots, chosenSlot)) {
                chosenSlot = random.nextInt(inventory.getSize());
            }
            chosenSlots[i] = chosenSlot;
            inventory.setItem(chosenSlot, newItems[i]);
        }

        return inventory;
    }
}
