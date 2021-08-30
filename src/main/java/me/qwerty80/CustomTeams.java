package me.qwerty80;

import java.util.ArrayList;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import me.qwerty80.Exceptions.NotFoundException;

public class CustomTeams implements Listener {
    ArrayList<ArrayList<Player>> teams = new ArrayList<>();
    private ArrayList<Player> lookingForTeam = new ArrayList<>();

    Qwerty80 main;

    public CustomTeams(Qwerty80 _main) {
        main = _main;
    }

    public boolean addPlayerLookingForTeam(Player player) {
        if (playerIsInATeam(player)) {
            return false;
        }

        if (lookingForTeam.contains(player)) {
            return false;
        }

        if (lookingForTeam.size() == 0) {
            lookingForTeam.add(player);
            Bukkit.broadcast(Component.text(player.getName() + " is looking for a team! Click here to join their team!").clickEvent(ClickEvent.runCommand("escape team join " + player.getName())).hoverEvent(Component.text("Click here!")));
            return true;
        }
        else {
            ArrayList<Player> toAdd = new ArrayList<>();
//for(org.bukkit.entity.Entity entity : player.getNearbyEntities(25, 15, 25)) {
            toAdd.add(lookingForTeam.get(0));
            toAdd.add(player);

            lookingForTeam.get(0).sendMessage("§aYou have been teamed up with " + player.getName());
            player.sendMessage("§aYou have been teamed up with " + lookingForTeam.get(0).getName());

            lookingForTeam.removeAll(lookingForTeam);
            teams.add(toAdd);
            return true;
        }
    }

    public void removePlayerLookingForTeam(Player player) throws NotFoundException {
        if (!lookingForTeam.contains(player)) {
            throw new NotFoundException();
        }

        lookingForTeam.get(0).sendMessage("§aYou are no longer looking for a team!");
        Bukkit.broadcast(Component.text("§a" + lookingForTeam.get(0).getName() + " is no longer looking for a team!").hoverEvent(Component.text(":(")));
        lookingForTeam.remove(player);
    }

    public boolean lookingForATeam(Player player) {
        return lookingForTeam.contains(player);
    }

    public boolean playerIsInATeam(Player player) {
        return teams.stream().anyMatch((team) -> team.contains(player));
    }

    public void removePlayerFromTeams(Player player) {
        teams.forEach(team -> {
            if (team.contains(player)) {
                team.remove(player);
            }
        });
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        
        if (event.getClickedInventory().getType() != InventoryType.PLAYER) {
            return;
        }


        if (!Utils.playerIsInAGame(player, main.games)) {
            return;
        }


        switch (event.getSlot()) {
            case 9: // Join team, i know having 2 buttons for this is dumb but whatever
                if (lookingForTeam.contains(player)) {
                    try {
                        removePlayerLookingForTeam(player);
                    }
                    catch (NotFoundException err) {
                        player.sendMessage("§cWe're sorry, but a bug happened on our part while trying to update your status. Please let the devs know about this. We're sorry for the inconvenience.");
                        lookingForTeam.removeAll(lookingForTeam);
                    }
                }
                else {
                    addPlayerLookingForTeam(player);
                }
                break;
            case 10:
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
            return;
        }

        teams.forEach(team -> {
            if (team.contains((Player) event.getEntity()) && team.contains((Player) event.getDamager())) {
                event.setCancelled(true);
            }
        });
    }
}