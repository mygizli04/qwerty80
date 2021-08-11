package me.qwerty80;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class ChestGUI implements Listener {

    boolean isChest(Material type) {
        return (type == Material.CHEST) || (type == Material.ENDER_CHEST);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().getWorld().getName().endsWith("_GAME_escape_new") && isChest(event.getClickedBlock().getType())) {
            LootTable table;
            switch (event.getClickedBlock().getType()) {
                case CHEST:
                    table = new LootTable(false);
                    table.rare = false;
                    break;
                case ENDER_CHEST:
                    table = new LootTable(true);
                    table.rare = true;
                    break;
                default:
                    // Impossible.
                    return;
            }
            Inventory inventory = new LootChest(table, table.rare).generate();

            event.getPlayer().playSound(event.getClickedBlock().getLocation(), Sound.BLOCK_CHEST_OPEN, 100, 1); 

            event.getPlayer().openInventory(inventory);
            event.getClickedBlock().setType(Material.AIR);
        }
    }
}