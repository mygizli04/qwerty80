package me.qwerty80;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextDecoration;

public class ItemGUI implements Listener {

    Qwerty80 main;

    public ItemGUI(Qwerty80 _main) {
        main = _main;
    }

    ArrayList<Player> playersWithGuiOpen = new ArrayList<Player>();
    ArrayList<Player> playersRenamingItems = new ArrayList<Player>();

    void openAnvilInventory(Player player) {
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
        inventory.setItem(18, Utils.createNamedItem(Material.RED_STAINED_GLASS_PANE, Component.text("§l§cCancel!")));
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
        playersWithGuiOpen.add(player);
    }

    @EventHandler
    public void useAnvil(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!Utils.playerIsInAGame(player, main.games)) {
            return;
        }

        if (player.getInventory().getHeldItemSlot() == 6) {

            if (event.getClickedBlock() == null) {
                openAnvilInventory(player);
                event.setCancelled(true);
                return;
            }

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

    @EventHandler
    public void closeAnvil(InventoryCloseEvent event) {
        playersWithGuiOpen.remove(event.getPlayer());
    }

    @EventHandler
    public void clickAnvil(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (playersWithGuiOpen.contains((Player) event.getWhoClicked())) {
            switch (event.getRawSlot()) {
                case 11:
                    player.closeInventory();
                    if (!Utils.checkPermission(player, "escape.rename.normal")) {
                        player.sendMessage("You do not have permission to rename items!");
                        return;
                    }
                    player.sendMessage("§aHold the item you want to §e§lrename§a, and type it in chat! §bType §c§l\"Cancel\"§b to cancel.");
                    playersRenamingItems.add(player);
                    break;
                case 13:
                    event.getWhoClicked().sendMessage("Open change color menu?");
                    //create
                    Inventory color = Bukkit.getServer().createInventory(null, 36, Component.text("Anvil"));
                    //add stuff
                    color.setItem(10, Utils.createNamedItem(Material.RED_STAINED_GLASS_PANE, Component.text("§4Dark Red - &4").decoration(TextDecoration.ITALIC, false)));
                    color.setItem(19, Utils.createNamedItem(Material.RED_STAINED_GLASS_PANE, Component.text("§cLight Red - &c").decoration(TextDecoration.ITALIC, false)));
                    color.setItem(11, Utils.createNamedItem(Material.ORANGE_STAINED_GLASS_PANE, Component.text("§6Orange - &6").decoration(TextDecoration.ITALIC, false)));
                    color.setItem(20, Utils.createNamedItem(Material.YELLOW_STAINED_GLASS_PANE, Component.text("§eYellow - &e").decoration(TextDecoration.ITALIC, false)));
                    color.setItem(12, Utils.createNamedItem(Material.GREEN_STAINED_GLASS_PANE, Component.text("§2Dark Green - &2").decoration(TextDecoration.ITALIC, false)));
                    
                    player.openInventory(color);
                    break;
                case 15:
                    event.getWhoClicked().sendMessage("Open text formatting tips");
                    break;
                case 18:
                    event.getWhoClicked().closeInventory();
                    break;
            }
            
            if (event.getRawSlot() <= 26) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    @SuppressWarnings("deprecation") // I don't care, I can't get the message from the "recommended" method.
    public void renameItem(org.bukkit.event.player.AsyncPlayerChatEvent event) {
        if (playersRenamingItems.contains(event.getPlayer())) {

            playersRenamingItems.remove(event.getPlayer());
            event.setCancelled(true);

            if (!Utils.checkPermission(event.getPlayer(), "escape.rename.normal")) {
                event.getPlayer().sendMessage("You do not have permission to rename items!");
                return;
            }

            String rename = event.getMessage().replaceAll("§", "&");
            ArrayList<String> bannedColorCodes = new ArrayList<String>();

            // lol easter eggs & cancel
            switch (rename.toLowerCase()) {
                case "cancel":
                    event.getPlayer().sendMessage("§c§lCancelled.");
                    break;
                case "\"cancel\"":
                    event.getPlayer().sendMessage("... not with the quotes... but §c§lcancelled§r rename.");
                    break;
                case "&ccancel":
                    event.getPlayer().sendMessage("Haha you did the color code too... §c§lcancelled.§r");
                    break;
                case "&lcancel":
                    event.getPlayer().sendMessage("What a §lbold§r way to §c§lcancel§r a rename.");
                    break;
                case "&c\"cancel\"":
                    event.getPlayer().sendMessage("You forgot the bold. §c§lCancelled.§r");
                    break;
                case "&l&c\"cancel\"":
                    event.getPlayer().sendMessage("No no no the bold has to come §iafter§r the &c or else it §c§lcancells.§r");
                    break;
                case "&c&l\"cancel\"":
                    event.getPlayer().sendMessage(Component.text("You did it...").clickEvent(ClickEvent.openUrl("https://www.urbandictionary.com/define.php?term=Type%20any%20word...")));
            }

            if (!Utils.checkPermission(event.getPlayer(), "escape.rename.format")) {
                bannedColorCodes.add("&k");
                bannedColorCodes.add("&l");
                bannedColorCodes.add("&m");
                bannedColorCodes.add("&n");
                bannedColorCodes.add("&o");
                bannedColorCodes.add("&r");
            }

            if (Utils.checkPermission(event.getPlayer(), "escape.rename.color")) {
                rename = Utils.replaceExcept(rename, "&", "§", bannedColorCodes.toArray(new String[0]));
            }

            if (!Utils.range(event.getPlayer().getInventory().getHeldItemSlot(), 6, 8)) {
                Utils.renameHeldItem(rename, event.getPlayer());
            }
            else {
                event.getPlayer().sendMessage("You can't rename that!");
            }
        }
    }
}