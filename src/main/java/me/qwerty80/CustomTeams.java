package me.qwerty80;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CustomTeams implements Listener {
    ArrayList<ArrayList<Player>> teams = new ArrayList<>();

    public boolean playerIsInATeam(Player player) {
        return teams.stream().anyMatch((team) -> team.contains(player));
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