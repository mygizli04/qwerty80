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

    Qwerty80 main;

    public ChestGUI(Qwerty80 main) {
        this.main = main;
    }

    ArrayList<PlayerInventory> inventories = new ArrayList<PlayerInventory>();

    boolean isChest(Material type) {
        return (type == Material.CHEST) || (type == Material.ENDER_CHEST);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if (!Utils.playerIsInAGame(event.getPlayer())) {
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().getWorld().getName().endsWith("_GAME_island_water") && isChest(event.getClickedBlock().getType())) {
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

            inventories.add(new PlayerInventory(event.getPlayer(), inventory, event.getClickedBlock().getLocation()));
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!Utils.playerIsInAGame((Player) event.getPlayer())) {
            return;
        }
        
        ArrayList<PlayerInventory> toRemove = new ArrayList<PlayerInventory>();
        
        inventories.forEach((inventory) -> {
            if (inventory.player == event.getPlayer()) {
                ((Player) event.getPlayer()).playSound(inventory.location, Sound.BLOCK_CHEST_CLOSE, 100, 1);
                inventory.inventory.forEach((item) -> {
                    if (item == null) {
                        return;
                    }
                    event.getPlayer().getWorld().dropItem(inventory.location, item);
                });

                toRemove.add(inventory);
            }
        });

        toRemove.forEach((remove) -> {
            inventories.remove(remove);
        });
    }
}

class PlayerInventory {
    Player player;
    Inventory inventory;
    Location location;

    public PlayerInventory(Player _player, Inventory _inventory, Location _location) {
        player = _player;
        inventory = _inventory;
        location = _location;
    }
}