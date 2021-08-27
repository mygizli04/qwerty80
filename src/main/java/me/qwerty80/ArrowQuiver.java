package me.qwerty80;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ArrowQuiver implements Listener {

    Qwerty80 main;

    public ArrowQuiver(Qwerty80 _main) {
        main = _main;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!Utils.playerIsInAGame(player, main.games)) {
            return;
        }

        if (event.getClickedInventory().getType() == InventoryType.CHEST) {
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
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (!Utils.playerIsInAGame(event.getPlayer(), main.games)) {
            return;
        }

        if (event.getPlayer().getInventory().getHeldItemSlot() == 7) {
            if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.ARROW) {
                ItemStack specArrow = new ItemStack(Material.SPECTRAL_ARROW, 1);
                event.setCancelled(true);
                Item droppedItem = event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), specArrow);
                droppedItem.setPickupDelay(20);
                droppedItem.setVelocity(event.getPlayer().getLocation().getDirection());
            }

            if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.SADDLE) {
                event.setCancelled(true);
            }
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
