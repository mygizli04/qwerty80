package me.qwerty80;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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
        player.openInventory(Utils.createGui(27, "Anvil", Material.LIME_STAINED_GLASS_PANE, Material.BLUE_STAINED_GLASS_PANE, new Utils.InventoryOverride[] {
            new Utils.InventoryOverride(10, Utils.createNamedItem(Material.YELLOW_STAINED_GLASS_PANE, "")),
            new Utils.InventoryOverride(11, Utils.createNamedItem(Material.NAME_TAG, "Rename!")),
            new Utils.InventoryOverride(12, Utils.createNamedItem(Material.YELLOW_STAINED_GLASS_PANE, "")),
            new Utils.InventoryOverride(13, Utils.createNamedItem(Material.NETHER_STAR,"§cC§3h§aa§2n§1g§be §aC§6o§3l§4o§er")),
            new Utils.InventoryOverride(14, Utils.createNamedItem(Material.YELLOW_STAINED_GLASS_PANE, "")),
            new Utils.InventoryOverride(15, Utils.createNamedItem(Material.ENCHANTED_BOOK, "§kL§r§LFormating Text§r§kL")),
            new Utils.InventoryOverride(16, Utils.createNamedItem(Material.YELLOW_STAINED_GLASS_PANE, "")),
            new Utils.InventoryOverride(17, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, "")),
            new Utils.InventoryOverride(19, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, "")),
            new Utils.InventoryOverride(18, Utils.createNamedItem(Material.RED_STAINED_GLASS_PANE, "§l§cCancel!")),
        }));

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

                                player.openInventory(Utils.createGui(36, "Anvil", Material.AIR, Material.AIR, new Utils.InventoryOverride[]{
                                    new Utils.InventoryOverride(10, Utils.createNamedItem(Material.RED_STAINED_GLASS_PANE, "§4Dark Red - &4")),
                                    new Utils.InventoryOverride(11, Utils.createNamedItem(Material.ORANGE_STAINED_GLASS_PANE, "§6Orange - &6")),
                                    new Utils.InventoryOverride(12, Utils.createNamedItem(Material.GREEN_STAINED_GLASS_PANE, "§2Dark Green - &2")),
                                    new Utils.InventoryOverride(13, Utils.createNamedItem(Material.CYAN_STAINED_GLASS_PANE, "§3Cyan - &3")),
                                    new Utils.InventoryOverride(14, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, "§1Dark Blue - &1")),
                                    new Utils.InventoryOverride(15, Utils.createNamedItem(Material.PURPLE_STAINED_GLASS_PANE, "§5Purple - &5")),
                                    new Utils.InventoryOverride(16, Utils.createNamedItem(Material.BLACK_STAINED_GLASS_PANE, "§fBlack - &0")),
                                    new Utils.InventoryOverride(19, Utils.createNamedItem(Material.RED_STAINED_GLASS_PANE, "§cLight Red - &c")),
                                    new Utils.InventoryOverride(20, Utils.createNamedItem(Material.YELLOW_STAINED_GLASS_PANE, "§eYellow - &e")),
                                    new Utils.InventoryOverride(21, Utils.createNamedItem(Material.LIME_STAINED_GLASS_PANE, "§aLime - &a")),
                                    new Utils.InventoryOverride(22, Utils.createNamedItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "§bLight Blue - &b")),
                                    new Utils.InventoryOverride(23, Utils.createNamedItem(Material.BLUE_STAINED_GLASS_PANE, "§9Blue - &9")),
                                    new Utils.InventoryOverride(24, Utils.createNamedItem(Material.PINK_STAINED_GLASS_PANE, "§dMagenta - &d")),
                                    new Utils.InventoryOverride(25, Utils.createNamedItem(Material.GRAY_STAINED_GLASS_PANE, "§8Gray - &8")),
                                    new Utils.InventoryOverride(27, Utils.createNamedItem(Material.BARRIER, "§l§cGo back")),
                                    new Utils.InventoryOverride(31, Utils.createNamedItem(Material.NAME_TAG, "Rename!")),
                                }));

                                playersWithGuiOpen.add(new PlayerWithGUI((Player) event.getWhoClicked(), GUIType.COLOR));
                                break;
                            case 15:
                                if (!Utils.checkPermission(event.getWhoClicked(), "escape.rename.format")) {
                                    event.getWhoClicked().sendMessage("You do not have permission to use format codes in your items.");
                                    return;
                                }

                                player.openInventory(Utils.createGui(27, "Anvil", Material.BLUE_STAINED_GLASS_PANE, Material.BLACK_STAINED_GLASS_PANE, new Utils.InventoryOverride[]{
                                    new Utils.InventoryOverride(11, Utils.addFlag(Utils.createNamedItem(Material.MOJANG_BANNER_PATTERN, "§lBold - &l"), ItemFlag.HIDE_POTION_EFFECTS)),
                                    new Utils.InventoryOverride(12, Utils.addFlag(Utils.createNamedItem(Material.MOJANG_BANNER_PATTERN, "§oItalics - &o"), ItemFlag.HIDE_POTION_EFFECTS)),
                                    new Utils.InventoryOverride(13, Utils.addFlag(Utils.createNamedItem(Material.MOJANG_BANNER_PATTERN, "§nUnderline - &n"), ItemFlag.HIDE_POTION_EFFECTS)),
                                    new Utils.InventoryOverride(14, Utils.addFlag(Utils.createNamedItem(Material.MOJANG_BANNER_PATTERN, "§mStrikethrough - &m"), ItemFlag.HIDE_POTION_EFFECTS)),
                                    new Utils.InventoryOverride(15, Utils.addFlag(Utils.createNamedItem(Material.MOJANG_BANNER_PATTERN, "§kWow you either cheated or translated... §r- &k"), ItemFlag.HIDE_POTION_EFFECTS)),
                                    new Utils.InventoryOverride(18, Utils.createNamedItem(Material.RED_STAINED_GLASS_PANE, "§l§cGo back")),
                                    new Utils.InventoryOverride(22, Utils.createNamedItem(Material.NAME_TAG, "Rename!")),
                                }));

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
                                else if (event.isShiftClick()) {
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