package me.qwerty80;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class Team implements Listener {

    Qwerty80 main;

    public Team(Qwerty80 _main) {
        main = _main;
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String playerEntry = player.getName();
        //Team team = (Team) player.getScoreboard().getEntryTeam(playerEntry); for later
        int size = player.getScoreboard().getEntryTeam(playerEntry).getSize();
        if (!Utils.playerIsInAGame(player, main.games)) {
            switch (event.getRawSlot()) {
                case 9:
                    //This wont work unless a player is in a team, I think we should automaticly put them in a blank team when they join
                    //when they join they get assigned a team (prob easiest as a growing number) and when they request to team, the person who accepts will join that team and the other one will be abandoned
                    //then when they leave they can go back to a newly generated team which I think is easier then back to the original, will help with bugs when the person who *owns* the team, the person eho started it leaves.
                    //anyway ima push now
                    if (size >= 4) {
                        player.sendMessage("§cYour team has reached the maximum of §l4§c players!");
                        return;
                    }
                    else if (size < 4) {    
                        for(org.bukkit.entity.Entity entity : player.getNearbyEntities(25, 15, 25)) {
                            if( entity instanceof Player) {
                                player.sendMessage("§aCurrently in teaming mode. Press the team button again to cancel. (timeout in 20 sec)");
                            }
                            else  {
                                player.sendMessage("§cThere are no nearby players to team with!");
                                
                            }
                        }
                    }
                break;

                case 10:

                break;

                default:
                    //nothing
                break;
            }
        }
    }
}