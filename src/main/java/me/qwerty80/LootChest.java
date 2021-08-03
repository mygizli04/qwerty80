package me.qwerty80;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LootChest {
    public Loot loot;
    public Location block;

    public LootChest(Loot _loot, Location _block) {
        loot = _loot;
        block = _block;
    }

    public void generate() {
        // Decide which items to put in the chest
        ItemStack[] newItems = new ItemStack[4];

        for (int i = 0; i <= 3; i++) {
            newItems[i] = loot.generate();
        }

        // Replace whatever's there with a chest
        block.getBlock().setType(Material.CHEST);

        // Add the items to the chest
        Inventory inventory = ((Chest) block.getBlock().getState()).getInventory();
        for (int i = 0; i < newItems.length; i++) {
            inventory.setItem(new Random().nextInt(inventory.getSize()), newItems[i]);
        }
    }
}
