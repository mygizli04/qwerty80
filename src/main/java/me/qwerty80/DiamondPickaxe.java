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

        main.debug("Event triggered!", player, "DiamondPickaxe");

        if (main.debugModeEnabled && event.getHotbarButton() != -1) {
            main.debug("Hotbar key " + event.getHotbarButton() + " pressed!", player, "DiamondPickaxe");
        }

        if (event.getHotbarButton() != -1 && player.getInventory().getItem(event.getHotbarButton()).getType() == Material.DIAMOND_PICKAXE) {
            main.debug("Tried to move diamond pickaxe with hotbar keys!", player, "DiamondPickaxe");
            event.setCancelled(true);
            return;
        }
        else if (main.debugModeEnabled) {
            if (event.getHotbarButton() != -1) {
                main.debug("Tried to use hotbar button " + event.getHotbarButton(), player, "DiamondPickaxe");
                main.debug("The item is " + player.getInventory().getItem(event.getHotbarButton()).getType().name(), player, "DiamondPickaxe");
            }
            else {
                main.debug("Not hotbar button.", player, "DiamondPickaxe");
            }
        }

        if (event.getCurrentItem() == null) {
            main.debug("Current item is null!", player, "DiamondPickaxe");
            return;
        }

        if (event.getCurrentItem().getType() != Material.DIAMOND_PICKAXE) {
            main.debug("Current item not diamond pickaxe", player, "DiamondPickaxe");
            return;
        }

        if (event.getInventory().getType() != InventoryType.CRAFTING) {
            main.debug("Inventory type is not player, it is " + event.getInventory().getType().name(), player, "DiamondPickaxe");
            event.setCancelled(true);
            return;
        }

        int[] emptySlots = Utils.getEmptySlotsInInventory(player.getInventory());
        int emptySlotCount = emptySlots.length - 5; // 5 unusable slots in inv after pick up

        if (emptySlotCount == 0) {
            main.debug("Player has no available empty slots (cancel)", player, "DiamondPickaxe");
            event.setCancelled(true);
        }
        else if (main.debugModeEnabled) {
            main.debug("Player has " + emptySlotCount + " empty slots.", player, "DiamondPickaxe");
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().getType() == Material.DIAMOND_PICKAXE) {
            event.setCancelled(true);
        }
    }
}
