package me.qwerty80;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class ChestGUI implements Listener {

    ArrayList<PlayerInventory> inventories = new ArrayList<PlayerInventory>();

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

            inventories.add(new PlayerInventory(event.getPlayer(), inventory));
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        inventories.forEach((inventory) -> {
            if (inventory.player == event.getPlayer()) {
                inventory.inventory.forEach((item) -> {
                    if (item == null) {
                        return;
                    }
                    event.getPlayer().getWorld().dropItem(event.getPlayer().getTargetBlock(null, 5).getLocation().add(new Location(event.getPlayer().getWorld(), 0, 1, 0)), item);
                });

                try {
                    inventories.remove(inventory);
                }
                catch (Exception err) {}
            }
        });
    }
}

class PlayerInventory {
    Player player;
    Inventory inventory;

    public PlayerInventory(Player _player, Inventory _inventory) {
        player = _player;
        inventory = _inventory;
    }
}