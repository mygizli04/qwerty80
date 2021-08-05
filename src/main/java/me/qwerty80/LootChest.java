package me.qwerty80;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LootChest {
    public LootTable loot;
    public Location block;

    public LootChest(LootTable _loot, Location _block) {
        loot = _loot;
        block = _block;
    }


    public void generate() {
        // Decide which items to put in the chest
        ItemStack[] newItems = loot.getItemStack();

        // Replace whatever's there with a chest
        block.getBlock().setType(Material.CHEST);

        // Add the items to the chest
        Inventory inventory = ((Chest) block.getBlock().getState()).getInventory();
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
    }
}
