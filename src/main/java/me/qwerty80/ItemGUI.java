package me.qwerty80;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;

public class ItemGUI implements Listener {

@EventHandler
public void useAnvil(PlayerInteractEvent event){
    Player player = event.getPlayer();
    Action action = event.getAction();
    if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
        if (player.getInventory().getItemInMainHand().getType() == Material.ANVIL) {
                // Create anvil
                Inventory inventory = Bukkit.getServer().createInventory(null, 27, Component.text("Anvil"));
                player.getInventory().setItem(11, Utils.changeItemName(new ItemStack(Material.NAME_TAG, 1), Component.text("§rRename!")));

                // Show anvil to user   
                event.getPlayer().openInventory(inventory);
        }
    }
}}