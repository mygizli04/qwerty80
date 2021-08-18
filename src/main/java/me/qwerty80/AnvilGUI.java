package me.qwerty80;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class AnvilGUI implements Listener {

@EventHandler
public void useAnvil(PlayerInteractEvent event){
    Player player = event.getPlayer();
    if(event.getAction().equals(Action.RIGHT_CLICK_AIR)){
        if(player.getInventory().getItemInMainHand().getType() == Material.ANVIL){
        }
    }
}}