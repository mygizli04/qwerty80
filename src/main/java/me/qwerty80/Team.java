package me.qwerty80;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class Team implements Listener {

    Qwerty80 main;

    public Team(Qwerty80 _main) {
        main = _main;
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory().getType() != InventoryType.PLAYER) {
            return;
        }

        Player player = (Player) event.getWhoClicked();

        switch (event.getSlot()) {
            case 8: // Join team, i know having 2 buttons for this is dumb but whatever
                if (main.teams.playerIsInATeam(player)) {
                    player.sendMessage("Sorry, but you can only be in a single team at a time.");
                    return;
                }

                Bukkit.broadcast(Component.text(player.getName() + " is looking for a team! Click here to join their team!").clickEvent(ClickEvent.runCommand("escape team join " + player.getName())).hoverEvent(Component.text("Click here!")));
        }
    }
}