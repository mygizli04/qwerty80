package me.qwerty80;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DiamondPickaxe implements Listener {
    Qwerty80 main;

    public DiamondPickaxe(Qwerty80 main) {
        this.main = main;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        player.sendMessage("Event triggered!");

        if (event.getCurrentItem().getType() != Material.DIAMOND_PICKAXE) {
            player.sendMessage("Current item not diamond pickaxe");
            return;
        }

        if (event.getInventory().getType() != InventoryType.CRAFTING) {
            player.sendMessage("Inventory type is not player, it is " + event.getInventory().getType().name());
            event.setCancelled(true);
            return;
        }

        int[] emptySlots = Utils.getEmptySlotsInInventory(player.getInventory());
        int emptySlotCount = emptySlots.length - 5; // 5 unusable slots in inv after pick up

        if (emptySlotCount == 0) {
            player.sendMessage("Player has no available empty slots (cancel)");
            event.setCancelled(true);
        }
        else {
            player.sendMessage("Player has " + emptySlotCount + " empty slots.");
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().getType() == Material.DIAMOND_PICKAXE) {
            event.setCancelled(true);
        }
    }
}
