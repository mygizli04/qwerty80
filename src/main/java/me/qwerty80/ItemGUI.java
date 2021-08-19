package me.qwerty80;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

public class ItemGUI implements Listener {

    Qwerty80 main;

    public ItemGUI(Qwerty80 _main) {
        main = _main;
    }

    static void openAnvilInventory(Player player) {
        // Create anvil
        Inventory inventory = Bukkit.getServer().createInventory(null, 27, Component.text("Anvil"));

        // Add things to anvil
        inventory.setItem(11, Utils.createNamedItem(Material.NAME_TAG, Component.text("Rename!").decoration(TextDecoration.ITALIC, false)));
        inventory.setItem(0, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(2, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(4, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(6, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(8, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(0, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(0, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(0, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(0, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, Component.text("")));

        // Show anvil to user
        player.openInventory(inventory);
    }

    @EventHandler
    public void useAnvil(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!Utils.playerIsInAGame(player, main.games)) {
            return;
        }

        if (player.getInventory().getHeldItemSlot() == 6) {
            switch (event.getClickedBlock().getType()) {
                case CHEST:
                    break;
                case ENDER_CHEST:
                    break;
                default:
                    openAnvilInventory(player);
                    event.setCancelled(true);
                    return;
            }
        }
    }
}