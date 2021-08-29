package me.qwerty80;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class Team {

    Qwerty80 main;

    public Team(Qwerty80 _main) {
        main = _main;
    }

    public void inventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!Utils.playerIsInAGame(player, main.games)) {
            switch (event.getRawSlot()) {
                case 9:
                    //if player has already pressed the team button or not to prevent spamming it, will add later
                        for(org.bukkit.entity.Entity entity : player.getNearbyEntities(25, 15, 25)) {
                            if( entity instanceof Player) {
                                player.sendMessage("&aCurrently in teaming mode. Press the team button again to cancel. (timeout in 20 sec)");
                            }
                            else  {
                                player.sendMessage("&cThere are no nearby players to team with!");
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