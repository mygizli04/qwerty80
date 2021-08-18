package me.qwerty80;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemGUI implements Listener {

@EventHandler
public void useAnvil(PlayerInteractEvent event){
    Player player = event.getPlayer();
    if(event.getAction().equals(Action.RIGHT_CLICK_AIR)){
        switch (player.getInventory().getItemInMainHand().getType()){
            case Material.ANVIL:
                // Create anvil
                Inventory inventory = Bukkit.getServer().createInventory(null, 27, "Anvil");

                // Add stuff to anvil
                

                // Show anvil to user
                event.getPlayer().openInventory(inventory);
            break;
        }
    }
}}