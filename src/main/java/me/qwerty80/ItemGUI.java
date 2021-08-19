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
        inventory.setItem(0, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(1, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(2, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(3, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(4, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(5, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(6, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(7, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(8, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(9, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(10, Utils.createNamedItem(Material.YELLOW_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(11, Utils.createNamedItem(Material.NAME_TAG, "Rename!"));
        inventory.setItem(12, Utils.createNamedItem(Material.YELLOW_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(13, Utils.createNamedItem(Material.NETHER_STAR, Component.text("§cC§3h§aa§2n§1g§be §aC§6o§3l§4o§er").decoration(TextDecoration.ITALIC, false)));
        inventory.setItem(14, Utils.createNamedItem(Material.YELLOW_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(15, Utils.createNamedItem(Material.ENCHANTED_BOOK, Component.text("§kL§r§LFormating Text§r§kL").decoration(TextDecoration.ITALIC, false)));
        inventory.setItem(16, Utils.createNamedItem(Material.YELLOW_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(17, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(18, Utils.createNamedItem(Material.RED_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(19, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(20, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(21, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(22, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(23, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(24, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(25, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, Component.text("")));
        inventory.setItem(26, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, Component.text("")));

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