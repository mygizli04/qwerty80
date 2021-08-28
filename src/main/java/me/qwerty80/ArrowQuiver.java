package me.qwerty80;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;

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

        if (event.getClickedInventory() == null){
            return;
        }

        if (event.getClickedInventory().getType() == InventoryType.CHEST) {
            if (event.getCurrentItem().getType() == Material.SPECTRAL_ARROW) {
                int arrows = event.getCurrentItem().getAmount();
                ItemStack arrow = new ItemStack(Material.ARROW, arrows);
                if (event.isShiftClick()) {
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
        else if (event.getClickedInventory().getType() == InventoryType.PLAYER && event.getSlot() == 7) {
            event.setCancelled(true);
            if (event.getClick() == ClickType.DROP) {
                dropQuiver((Player) event.getWhoClicked());
            }
        }
    }

    boolean dropQuiver(Player player) throws NullPointerException {
        if (player.getInventory().getItem(7).getType() == Material.ARROW) {
            ItemStack specArrow = new ItemStack(Material.SPECTRAL_ARROW, 1);
            if (player.getInventory().getItem(7).getAmount() == 1) {
                player.getInventory().setItem(7, new ItemStack(Material.SADDLE));
                Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                    player.getInventory().remove(Material.ARROW);
                });
            }
            else {
                player.getInventory().setItem(7, new ItemStack(Material.ARROW, player.getInventory().getItem(7).getAmount() - 1));
            }
            Item droppedItem = player.getWorld().dropItem(player.getLocation().add(0,1,0), specArrow);
            droppedItem.setPickupDelay(20);
            droppedItem.setVelocity(player.getLocation().getDirection().multiply(0.5));
            return true;
        }
        else if (player.getInventory().getItem(7).getType() == Material.SADDLE) {
            return false;
        }
        else {
            // If it's NEITHER saddle or arrow we have to recover from it
            player.getInventory().remove(Material.ARROW);
            player.getInventory().remove(Material.SPECTRAL_ARROW);
            player.getInventory().remove(Material.SADDLE);
            player.getInventory().setItem(7, new ItemStack(Material.SADDLE));
            player.sendMessage(Component.text("Sorry, but we've run into an unfixable problem and had to reset your arrows. Please report this bug to the admins. We apologize for the inconveniece."));
            return false;
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (!Utils.playerIsInAGame(event.getPlayer(), main.games)) {
            return;
        }

        if (event.getItemDrop().getItemStack().getType() == Material.ARROW) {
            ItemStack specArrow = new ItemStack(Material.SPECTRAL_ARROW, 1);
            event.setCancelled(true);
            if (event.getPlayer().getInventory().getItem(7) == null) {
                event.getPlayer().getInventory().setItem(7, new ItemStack(Material.SADDLE));
                Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                    event.getPlayer().getInventory().remove(Material.ARROW);
                });
            }
            else {
                event.getPlayer().getInventory().setItem(7, new ItemStack(Material.ARROW, event.getPlayer().getInventory().getItem(7).getAmount() - 1));
            }
            Item droppedItem = event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation().add(0,1,0), specArrow);
            droppedItem.setPickupDelay(20);
            droppedItem.setVelocity(event.getPlayer().getLocation().getDirection().multiply(0.5));
        }
        else if (event.getItemDrop().getItemStack().getType() == Material.SADDLE) {
            event.setCancelled(true);
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

    @EventHandler
    public void itemPickup(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player && event.getItem().getItemStack().getType() == Material.SPECTRAL_ARROW ) {
            Player player = (Player) event.getEntity();
            Inventory inventory = player.getInventory();
            int amount;
            try {
                amount = inventory.getItem(7).getAmount();
            }
            catch (NullPointerException err) {
                event.setCancelled(true);
                player.sendMessage("§cUh oh, an error occured and we cannot recover from it. We're sorry about this bug and please inform the devs about this issue.");
                inventory.remove(Material.ARROW);
                inventory.remove(Material.SPECTRAL_ARROW);
                inventory.remove(Material.SADDLE);
                inventory.setItem(7, new ItemStack(Material.SADDLE));
                player.sendMessage("§c" + err.getMessage());
                return;
            }

            switch (inventory.getItem(7).getType()) {
                case SADDLE:
                    Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                        inventory.remove(Material.SPECTRAL_ARROW);
                        inventory.setItem(7, new ItemStack(Material.ARROW, event.getItem().getItemStack().getAmount()));
                    });
                    break;
                case ARROW:
                    if (amount == 100) {
                        event.setCancelled(true);
                    }
                    else {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                            inventory.remove(Material.SPECTRAL_ARROW);
                            inventory.setItem(7, new ItemStack(Material.ARROW, amount + event.getItem().getItemStack().getAmount()));
                        });
                    }
                    break;
                default:
                    // If it's NEITHER saddle or arrow we have to recover from it
                    inventory.remove(Material.ARROW);
                    inventory.remove(Material.SPECTRAL_ARROW);
                    inventory.remove(Material.SADDLE);
                    inventory.setItem(7, new ItemStack(Material.SADDLE));
                    player.sendMessage(Component.text("Sorry, but we've run into an unfixable problem and had to reset your arrows. Please report this bug to the admins. We apologize for the inconveniece."));
            }
        }
    }
}
