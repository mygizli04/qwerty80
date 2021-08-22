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
import org.bukkit.inventory.ItemFlag;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;

public class ItemGUI implements Listener {

    Qwerty80 main;

    public ItemGUI(Qwerty80 _main) {
        main = _main;
    }

    ArrayList<PlayerWithGUI> playersWithGuiOpen = new ArrayList<PlayerWithGUI>();
    ArrayList<Player> playersRenamingItems = new ArrayList<Player>();

    void openAnvilInventory(Player player) {
        // Create anvil
        Inventory inventory = Bukkit.getServer().createInventory(null, 27, Component.text("Anvil"));

        // Add things to anvil
        inventory.setItem(0, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, ""));
        inventory.setItem(1, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, ""));
        inventory.setItem(2, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, ""));
        inventory.setItem(3, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, ""));
        inventory.setItem(4, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, ""));
        inventory.setItem(5, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, ""));
        inventory.setItem(6, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, ""));
        inventory.setItem(7, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, ""));
        inventory.setItem(8, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, ""));
        inventory.setItem(9, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, ""));
        inventory.setItem(10, Utils.createNamedItem(Material.YELLOW_STAINED_GLASS_PANE, ""));
        inventory.setItem(11, Utils.createNamedItem(Material.NAME_TAG, "Rename!"));
        inventory.setItem(12, Utils.createNamedItem(Material.YELLOW_STAINED_GLASS_PANE, ""));
        inventory.setItem(13, Utils.createNamedItem(Material.NETHER_STAR,"§cC§3h§aa§2n§1g§be §aC§6o§3l§4o§er"));
        inventory.setItem(14, Utils.createNamedItem(Material.YELLOW_STAINED_GLASS_PANE, ""));
        inventory.setItem(15, Utils.createNamedItem(Material.ENCHANTED_BOOK, "§kL§r§LFormating Text§r§kL"));
        inventory.setItem(16, Utils.createNamedItem(Material.YELLOW_STAINED_GLASS_PANE, ""));
        inventory.setItem(17, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, ""));
        inventory.setItem(19, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, ""));
        inventory.setItem(18, Utils.createNamedItem(Material.RED_STAINED_GLASS_PANE, "§l§cCancel!"));
        inventory.setItem(20, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, ""));
        inventory.setItem(21, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, ""));
        inventory.setItem(22, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, ""));
        inventory.setItem(23, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, ""));
        inventory.setItem(24, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, ""));
        inventory.setItem(25, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, ""));
        inventory.setItem(26, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, ""));

        // Show anvil to user
        player.openInventory(inventory);
        playersWithGuiOpen.add(new PlayerWithGUI(player, GUIType.ANVIL));
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
        playersWithGuiOpen.removeIf(element -> element.player == event.getPlayer());
    }

    void initRenameItem(Player player) {
        player.closeInventory();
        if (!Utils.checkPermission(player, "escape.rename.normal")) {
            player.sendMessage("You do not have permission to rename items!");
            return;
        }
        player.sendMessage("§aHold the item you want to §e§lRename§a, and type it in chat! §aType §c§l\"Cancel\"§a to cancel or §b§l\"Reset\"§a to reset.");
        playersRenamingItems.add(player);
    }

    @EventHandler
    public void clickAnvil(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        playersWithGuiOpen.forEach(playerWithGui -> {
            if (playerWithGui.player == event.getWhoClicked()) {
                switch (playerWithGui.type) {
                    case ANVIL:
                        switch (event.getRawSlot()) {
                            case 11:
                                initRenameItem(player);
                                break;
                            case 13:
                                // Check permission
                                if (!Utils.checkPermission(player, "escape.rename.color")) {
                                    player.sendMessage("You do not have permission to use color codes in your items!");
                                    event.setCancelled(true);
                                    return;
                                }

                                // create
                                Inventory color = Bukkit.getServer().createInventory(null, 36, Component.text("Anvil"));
                                // add stuff
                                color.setItem(0, Utils.createNamedItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ""));
                                color.setItem(1, Utils.createNamedItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ""));
                                color.setItem(2, Utils.createNamedItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ""));
                                color.setItem(3, Utils.createNamedItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ""));
                                color.setItem(4, Utils.createNamedItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ""));
                                color.setItem(5, Utils.createNamedItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ""));
                                color.setItem(6, Utils.createNamedItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ""));
                                color.setItem(7, Utils.createNamedItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ""));
                                color.setItem(8, Utils.createNamedItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ""));
                                color.setItem(9, Utils.createNamedItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ""));
                                color.setItem(10, Utils.createNamedItem(Material.RED_STAINED_GLASS_PANE, "§4Dark Red - &4"));
                                color.setItem(11, Utils.createNamedItem(Material.ORANGE_STAINED_GLASS_PANE, "§6Orange - &6"));
                                color.setItem(12, Utils.createNamedItem(Material.GREEN_STAINED_GLASS_PANE, "§2Dark Green - &2"));
                                color.setItem(13, Utils.createNamedItem(Material.CYAN_STAINED_GLASS_PANE, "§3Cyan - &3"));
                                color.setItem(14, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, "§1Dark Blue - &1"));
                                color.setItem(15, Utils.createNamedItem(Material.PURPLE_STAINED_GLASS_PANE, "§5Purple - &5"));
                                color.setItem(16, Utils.createNamedItem(Material.BLACK_STAINED_GLASS_PANE, "§fBlack - &0"));
                                color.setItem(17, Utils.createNamedItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ""));
                                color.setItem(18, Utils.createNamedItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ""));
                                color.setItem(19, Utils.createNamedItem(Material.RED_STAINED_GLASS_PANE, "§cLight Red - &c"));
                                color.setItem(20, Utils.createNamedItem(Material.YELLOW_STAINED_GLASS_PANE, "§eYellow - &e"));
                                color.setItem(21, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, "§aLime - &a"));
                                color.setItem(22, Utils.createNamedItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "§bLight Blue - &b"));
                                color.setItem(23, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, "§9Blue - &9"));
                                color.setItem(24, Utils.createNamedItem(Material.PINK_STAINED_GLASS_PANE, "§dMagenta - &d"));
                                color.setItem(25, Utils.createNamedItem(Material.GRAY_STAINED_GLASS_PANE, "§8Gray - &8"));
                                color.setItem(27, Utils.createNamedItem(Material.BARRIER, "§l§cGo back"));
                                color.setItem(26, Utils.createNamedItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ""));
                                color.setItem(28, Utils.createNamedItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ""));
                                color.setItem(29, Utils.createNamedItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ""));
                                color.setItem(30, Utils.createNamedItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ""));
                                color.setItem(31, Utils.createNamedItem(Material.NAME_TAG, "Rename!"));
                                color.setItem(32, Utils.createNamedItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ""));
                                color.setItem(33, Utils.createNamedItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ""));
                                color.setItem(34, Utils.createNamedItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ""));
                                color.setItem(35, Utils.createNamedItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ""));

                                player.openInventory(color);
                                playersWithGuiOpen.add(new PlayerWithGUI((Player) event.getWhoClicked(), GUIType.COLOR));
                                break;
                            case 15:
                                if (!Utils.checkPermission(event.getWhoClicked(), "escape.rename.format")) {
                                    event.getWhoClicked().sendMessage("You do not have permission to use format codes in your items.");
                                    return;
                                }
                                Inventory format = Bukkit.getServer().createInventory(null, 27, Component.text("Anvil"));
                                //items
                                format.setItem(0, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, ""));
                                format.setItem(1, Utils.createNamedItem(Material.BLACK_STAINED_GLASS_PANE, ""));
                                format.setItem(2, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, ""));
                                format.setItem(3, Utils.createNamedItem(Material.BLACK_STAINED_GLASS_PANE, ""));
                                format.setItem(4, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, ""));
                                format.setItem(5, Utils.createNamedItem(Material.BLACK_STAINED_GLASS_PANE, ""));
                                format.setItem(6, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, ""));
                                format.setItem(7, Utils.createNamedItem(Material.BLACK_STAINED_GLASS_PANE, ""));
                                format.setItem(8, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, ""));
                                format.setItem(9, Utils.createNamedItem(Material.BLACK_STAINED_GLASS_PANE, ""));
                                format.setItem(10, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, ""));
                                format.setItem(11, Utils.addFlag(Utils.createNamedItem(Material.MOJANG_BANNER_PATTERN, "§lBold - &l"), ItemFlag.HIDE_POTION_EFFECTS));
                                format.setItem(12, Utils.addFlag(Utils.createNamedItem(Material.MOJANG_BANNER_PATTERN, "§oItalics - &o"), ItemFlag.HIDE_POTION_EFFECTS));
                                format.setItem(13, Utils.addFlag(Utils.createNamedItem(Material.MOJANG_BANNER_PATTERN, "§nUnderline - &n"), ItemFlag.HIDE_POTION_EFFECTS));
                                format.setItem(14, Utils.addFlag(Utils.createNamedItem(Material.MOJANG_BANNER_PATTERN, "§mStrikethrough - &m"), ItemFlag.HIDE_POTION_EFFECTS));
                                format.setItem(15, Utils.addFlag(Utils.createNamedItem(Material.MOJANG_BANNER_PATTERN, "§kWow you either cheated or translated... §r- &k"), ItemFlag.HIDE_POTION_EFFECTS));
                                format.setItem(16, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, ""));
                                format.setItem(17, Utils.createNamedItem(Material.BLACK_STAINED_GLASS_PANE, "")); // tell me which ones i should add it to the banners all of em these 5
                                format.setItem(18, Utils.createNamedItem(Material.RED_STAINED_GLASS_PANE, "§l§cGo back"));
                                format.setItem(19, Utils.createNamedItem(Material.BLACK_STAINED_GLASS_PANE, ""));
                                format.setItem(20, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, ""));
                                format.setItem(21, Utils.createNamedItem(Material.BLACK_STAINED_GLASS_PANE, ""));
                                format.setItem(22, Utils.createNamedItem(Material.NAME_TAG, "Rename!"));
                                format.setItem(23, Utils.createNamedItem(Material.BLACK_STAINED_GLASS_PANE, ""));
                                format.setItem(24, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, ""));
                                format.setItem(25, Utils.createNamedItem(Material.BLACK_STAINED_GLASS_PANE, ""));
                                format.setItem(26, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, ""));

                                player.openInventory(format);
                                playersWithGuiOpen.add(new PlayerWithGUI((Player) event.getWhoClicked(), GUIType.FORMAT));
                                break;
                            case 18:
                                event.getWhoClicked().closeInventory();
                                break;
                        }

                        if (event.getRawSlot() <= 26) {
                            event.setCancelled(true);
                        }
                        break;
                    case COLOR:
                        switch (event.getRawSlot()) {
                            case 27:
                                event.getWhoClicked().closeInventory();
                                openAnvilInventory(playerWithGui.player);
                                break;
                            case 31:
                                initRenameItem(player);
                                break;
                            default:
                                if (event.getRawSlot() <= 35) {
                                    event.setCancelled(true);
                                }
                        }
                        break;
                    case FORMAT:
                        switch (event.getRawSlot()) {
                            case 18:
                                event.getWhoClicked().closeInventory();
                                openAnvilInventory(playerWithGui.player);
                                break;
                            case 22:
                                initRenameItem(player);
                                break;
                            default:
                                if (event.getRawSlot() <= 26) {
                                    event.setCancelled(true);
                                }
                        }
                        break;
                }
            }
        });
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

            // lol easter eggs, cancel and reset
            switch (rename.toLowerCase()) {
                case "cancel":
                    event.getPlayer().sendMessage("§c§lCancelled.");
                    return;
                case "\"cancel\"":
                    event.getPlayer().sendMessage("... not with the quotes... but §c§lcancelled§r rename.");
                    return;
                case "&ccancel":
                    event.getPlayer().sendMessage("Haha you did the color code too... §c§lcancelled.§r");
                    return;
                case "&lcancel":
                    event.getPlayer().sendMessage("What a §lbold§r way to §c§lcancel§r a rename.");
                    return;
                case "&c\"cancel\"":
                    event.getPlayer().sendMessage("You forgot the bold. §c§lCancelled.§r");
                    return;
                case "&l&c\"cancel\"":
                    event.getPlayer().sendMessage("No no no the bold has to come §iafter§r the &c or else it §c§lcancels.§r");
                    return;
                case "&c&l\"cancel\"":
                    event.getPlayer().sendMessage(Component.text("You did it...").clickEvent(ClickEvent.openUrl("https://www.urbandictionary.com/define.php?term=Type%20any%20word...")));
                    return;
                case "reset":
                    Utils.renameHeldItem(null, event.getPlayer());
                    event.getPlayer().sendMessage(Component.text("§eYour item name has been §b§lReset!"));
                    return;
            }

            if (rename.contains("&") && !Utils.checkPermission(event.getPlayer(), "escape.rename.color")) {
                event.getPlayer().sendMessage("You do not have permission to use color codes in your items!");
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

            if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) {
                event.getPlayer().sendMessage("§cYou can't rename air?!?!");
            }

            if (!Utils.range(event.getPlayer().getInventory().getHeldItemSlot(), 6, 8)) {
                Utils.renameHeldItem(rename, event.getPlayer());
            } else {
                event.getPlayer().sendMessage("§cYou can't rename that!");
            }
        }
    }
}

enum GUIType {
    ANVIL,
    COLOR,
    FORMAT
}

class PlayerWithGUI {
    Player player;
    GUIType type;

    public PlayerWithGUI(Player _player, GUIType _type) {
        player = _player;
        type = _type;
    }
}