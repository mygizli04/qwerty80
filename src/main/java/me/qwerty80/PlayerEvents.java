package me.qwerty80;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
//import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.qwerty80.Exceptions.NotFoundException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class PlayerEvents implements Listener {

    Qwerty80 main;

    public PlayerEvents(Qwerty80 _main) {
        main = _main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
            // https://wiki.vg/Protocol_version_numbers
            int version = Utils.viaApi.getPlayerVersion(event.getPlayer());

            if (Utils.range(version, 735, 755)) {
                event.getPlayer().sendMessage(Component.text(
                        "Hey there! Thank you for joining our server. Even though we allow older clients to join, please don't forget that our server was designed with 1.17.1 in mind. If you come across any glitches or bugs, please test them on 1.17.1 before reporting to us. Thank you and we hope you have a great time!")
                        .color(NamedTextColor.RED));
                return;
            }

            if (version < 735) {
                event.getPlayer().sendMessage(Component.text(
                        "Hey there! Thank you for joining our server. Even though we allow legacy clients to join, please don't forget that since you're on a version below 1.16 some items or blocks might look different than expected. We do not support versions below 1.16, expect a lot of bugs.")
                        .color(NamedTextColor.RED));
                return;
            }
        }, 5);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        try {
            Utils.getPlayersGame(event.getPlayer(), main.games).playerLeave(event.getPlayer());
        } catch (NotFoundException err) {
            // Do nothing.
        }

        event.getPlayer().teleport(Bukkit.getWorld("empty").getSpawnLocation());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType().name().contains("_BED")
                    && !Utils.checkPermission(event.getPlayer(), "escape.admin.bedinteract")) {
                event.setCancelled(true);
            }
        }

        if (Utils.playerIsInAGame(event.getPlayer(), main.games)) {
            switch (event.getPlayer().getInventory().getHeldItemSlot()) {
                case 6:
                    // Handled in ItemGUI
                    break;
                case 7:
                    event.setCancelled(true);
                    break;
                case 8:
                    event.setCancelled(true);
                    break;
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        try {
            Game game = Utils.getPlayersGame(event.getEntity(), main.games);
            game.playerLeave(event.getEntity());
        } catch (NotFoundException err) {
            // Do nothing, they're not in a game.
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.setRespawnLocation(Bukkit.getServer().getWorld("empty").getSpawnLocation());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!Utils.playerIsInAGame(player, main.games)) {
            return;
        }

        switch (event.getHotbarButton()) {
            case 6:
                event.setCancelled(true);
                break;
            case 7:
                event.setCancelled(true);
                break;
            case 8:
                event.setCancelled(true);
                break;
        }

        switch (event.getClickedInventory().getType()) {
            case PLAYER:
                switch (event.getSlot()) {
                    case 6:
                        event.setCancelled(true);
                        break;
                    case 7:
                        event.setCancelled(true);
                        break;
                    case 8:
                        event.setCancelled(true);
                        break;
                    case 9:
                        event.setCancelled(true);
                        break;
                    case 10:
                        event.setCancelled(true);
                        break;
                    case 11:
                        event.setCancelled(true);
                        break;
                    case 12:
                        event.setCancelled(true);
                        break;
                    case 13:
                        event.setCancelled(true);
                        break;
                    case 14:
                        event.setCancelled(true);
                        break;
                    case 15:
                        event.setCancelled(true);
                        break;
                    case 16:
                        event.setCancelled(true);
                        break;
                    case 17:
                        event.setCancelled(true);
                        break;
                    case 24:
                        event.setCancelled(true);
                        break;
                    case 25:
                        event.setCancelled(true);
                        break;
                    case 26:
                        event.setCancelled(true);
                        break;
                    case 33:
                        event.setCancelled(true);
                        break;
                    case 34:
                        event.setCancelled(true);
                        break;
                    case 35:
                        event.setCancelled(true);
                        break;
                }
                break;
            case CHEST:
                if (event.getCurrentItem().getType() == Material.SPECTRAL_ARROW) {
                    int arrows = event.getCurrentItem().getAmount();
                    ItemStack arrow = new ItemStack(Material.ARROW, arrows);
                    if (event.isShiftClick()) { // if it's a shift click
                        switch (player.getInventory().getItem(7).getType()) {
                            case ARROW:
                                int amount = player.getInventory().getItem(7).getAmount() + arrows;
                                int amountOver = 0;
                                if (amount > 100) {
                                    amountOver = amount - 100;
                                    amount = 100;
                                }
                                ItemStack addArrow = new ItemStack(Material.ARROW, amount);
                                player.getInventory().setItem(7, addArrow);
                                ItemStack remainingArrow = new ItemStack(Material.SPECTRAL_ARROW, amountOver);
                                event.setCancelled(true);
                                event.getClickedInventory().setItem(event.getSlot(), remainingArrow);
                                break;
                            case SADDLE:
                                player.getInventory().setItem(7, arrow);
                                event.setCancelled(true);
                                event.getClickedInventory().setItem(event.getSlot(), null);
                                break;
                            default:
                                // nothing
                        }
                    }
                    /*
                     * will fix later for just regular clicking if (event.getInventory().getType()
                     * == InventoryType.CHEST) { if (event.getCursor().getType() ==
                     * Material.SPECTRAL_ARROW) { if (event.getInventory().getType() ==
                     * InventoryType.PLAYER) { if (player.getInventory().getItem(7).getType() ==
                     * Material.ARROW) { int amount = player.getInventory().getItem(7).getAmount() +
                     * 8; ItemStack addArrow = new ItemStack(Material.ARROW, amount);
                     * player.getInventory().setItem(7, addArrow); } if
                     * (player.getInventory().getItem(7).getType() == Material.SADDLE) {
                     * player.getInventory().setItem(7, arrow); } } } }
                     */
                }
                break;
            default:
                // Nothing.
        }

        if (event.getClickedInventory().getType() == InventoryType.PLAYER) {

        }
        if (event.getClickedInventory().getType() == InventoryType.CHEST) {

        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (!Utils.playerIsInAGame(event.getPlayer(), main.games)) {
            return;
        }
        switch (event.getPlayer().getInventory().getHeldItemSlot()) {
            case 6:
                event.setCancelled(true);
            case 7:
                if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.ARROW) {
                    ItemStack specArrow = new ItemStack(Material.SPECTRAL_ARROW, 1);
                    event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), specArrow);
                    event.setCancelled(false);
                }
                if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.SADDLE) {
                    event.setCancelled(true);
                }
            case 8:
                event.setCancelled(true);
            default:
                // nothing ig (I think this may be contributing to my problems with dropping
                // from inv...)
        }
    }

    @EventHandler
    public void onShootBow(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getInventory().getItem(7).getType() == Material.ARROW && player.getInventory().getItem(7).getAmount() == 1) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                    player.getInventory().setItem(7, new ItemStack(Material.SADDLE));
                }, 1);
            }
        }
    }

    @EventHandler
    public void arrowPickup(PlayerPickupArrowEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
            Inventory inventory = event.getPlayer().getInventory();
            switch (inventory.getItem(7).getType()) {
                case ARROW:
                    int arrowAmount = inventory.getItem(7).getAmount();
                    if (arrowAmount == 100) {
                        inventory.remove(Material.ARROW);
                        inventory.setItem(7, new ItemStack(Material.ARROW, 100));
                    } else if (arrowAmount >= 64) {
                        inventory.remove(Material.ARROW);
                        inventory.setItem(7, new ItemStack(Material.ARROW, arrowAmount + 1));
                    }
                    break;
                case SADDLE:
                    inventory.remove(Material.ARROW);
                    inventory.setItem(7, new ItemStack(Material.ARROW));
                    break;
                default:
                    // Nothing :)
            }
        });
    }
}