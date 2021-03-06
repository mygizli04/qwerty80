package me.qwerty80;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

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

        event.getPlayer().teleport(Bukkit.getWorld("empty").getSpawnLocation());

        if (!Utils.playerIsInAGame(event.getPlayer())) {
            return;
        }

        Utils.getPlayersGame(event.getPlayer()).playerLeave(event.getPlayer());

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (!Utils.playerIsInAGame(event.getPlayer())) {
            return;
        }

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (event.getClickedBlock().getType().name().contains("_BED")
                && !Utils.checkPermission(event.getPlayer(), "escape.admin.bedinteract")) {
            event.setCancelled(true);
        }

        switch (event.getPlayer().getInventory().getHeldItemSlot()) {
            // Case 6 handled in ItemGUI
            case 7:
                event.setCancelled(true);
                break;
            case 8:
                event.setCancelled(true);
                break;
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        if (!Utils.playerIsInAGame(event.getEntity())) {
            return;
        }

        Game game = Utils.getPlayersGame(event.getEntity());
        game.playerLeave(event.getEntity());
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.setRespawnLocation(Bukkit.getServer().getWorld("empty").getSpawnLocation());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory() == null) {
            return;
        }

        if (event.getClickedInventory().getType() != InventoryType.PLAYER) {
            return;
        }

        if (!Utils.playerIsInAGame(player)) {
            return;
        }

        if (Utils.range(event.getHotbarButton(), 6, 8)) {
            event.setCancelled(true);
        }
        //for somereason slot 34 is not getting cancelled.
        if (event.getClickedInventory().getType() == InventoryType.PLAYER) {
            // If it's in range of 6-17 (excluding 7) or range of 24-35 excluding 27, 28, 29, 30, 31, 32
            if (Utils.inRangeExcept(event.getSlot(), 6, 17, new int[] { 7 })
                    || Utils.inRangeExcept(event.getSlot(), 24, 35, new int[] { 27, 28, 29, 30, 31, 32 })) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (!Utils.playerIsInAGame(event.getPlayer())) {
            return;
        }
        switch (event.getPlayer().getInventory().getHeldItemSlot()) {
            case 6:
                event.setCancelled(true);
                break;
            case 7:
                // Handled in ArrowQuiver.java
                break;
            case 8:
                event.setCancelled(true);
                break;
            default:
                // Nothing.
        }
    }
}