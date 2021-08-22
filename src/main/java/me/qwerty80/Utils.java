package me.qwerty80;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;

import me.qwerty80.Exceptions.NotFoundException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;

public interface Utils {
    static int random(double min, double max) {
        return (int) Math.round(Math.random() * (max - min) + min);
    }

    static boolean percentage(double percent) {
        return Math.random() < (percent / 100);
    }

    static boolean range(double num, double min, double max) {
        return (min <= num) && (num <= max);
    }

    static <T> T choose(T[] array) {
        return array[random(0, array.length - 1)];
    }

    static <From, To> To[] convertArray(From[] from, Class<To> to) {
        ArrayList<To> ret = new ArrayList<To>();
        for (int i = 0; i < from.length; i++) {
            try {
                ret.add(to.getDeclaredConstructor().newInstance(from[i]));
            }
            catch (Exception e) {
                Bukkit.getLogger().warning("ERROR: " + e);
            }
        }
        @SuppressWarnings("unchecked")
        To[] finalArray = (To[]) ret.toArray();
        return finalArray;
    }

    static <T> boolean arrayIncludes(T[] array, T includes) {
        for (T item : array) {
            if (item == includes) {
                return true;
            }
        }
        return false;
    }

    static <T> T choosePercentage(T[] array, int[] percentages) { // choosepercentage([1,2,7,3]) then it'll return int. || choosepercentage["6","9","6","9"] then it'll return string.
        int chance = random(0, 100);
        int currentChance = 0;

        for (int uwu = 0; uwu < array.length; uwu++) { // if uwu >= 1: && ++ == 
            if (range(chance, currentChance, percentages[uwu])) { // (uwu - 1)st/nd/rd/th argument of percentages hi :D
                return array[uwu];
            }
        }

        return null; // Should never happen (pls). :L
    }

    static RegisteredServiceProvider<LuckPerms> lpProvider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
    static LuckPerms luckperms = lpProvider.getProvider();

    static boolean checkPermission(User user, String permission) {
        return user.getCachedData().getPermissionData().checkPermission(permission).asBoolean();
    }

    static boolean checkPermission(Player player, String permission) {
        return checkPermission(luckperms.getPlayerAdapter(Player.class).getUser(player), permission);
    }

    static boolean checkPermission(CommandSender sender, String permission) {
        if (sender instanceof Player) {
            return checkPermission((Player) sender, permission);
        }
        else {
            return true;
        }
    }

    static boolean stringIsTruthy(String string) {
        if (string == null) {
            return false;
        }
        
        if (string.length() == 0) {
            return false;
        }

        return true;
    }

    static void teleportPlayerToWorld(Player player, String world) {
        Location destination = Bukkit.getServer().getWorld(world).getSpawnLocation();
        player.teleport(destination);
    }

    static void teleportPlayerToWorld(CommandSender sender, String world) {
        if (sender instanceof Player) {
            teleportPlayerToWorld((Player) sender, world);
        }
        else {
            Bukkit.getLogger().warning("Tried to teleport non-entity sender! Failing silently instead...");
        }
    }

    static double chooseHighest(double[] array) {
        // I sure do hope no one passes a negative number here :)
        double ret = 0;
        for (double num : array) {
            if (num > ret) {
                ret = num;
            }
        }
        return ret;
    }

    static void deleteRecursively(File toDelete) {
        File[] contents = toDelete.listFiles();
        if (contents != null) {
            for (File file : contents) {
                deleteRecursively(file);
            }
        }
        toDelete.delete();
    }

    static void deleteRecursively(String toDelete) {
        deleteRecursively(new File(toDelete));
    }

    @SuppressWarnings("unchecked")
    ViaAPI<Player> viaApi = Via.getAPI();

    static Game getPlayersGame(Player player, Stack<Game> games) throws Exceptions.NotFoundException {
        Game[] ret = new Game[1]; // I hate java SO SO much....
        games.forEach(game -> {
            game.players.forEach(searchPlayer -> {
                if (searchPlayer == player) {
                    ret[0] = game;
                }
            });
        });
        if (ret[0] == null) {
            throw new Exceptions.NotFoundException();
        }
        return ret[0];
    }

    static ItemStack changeItemName(ItemStack item, TextComponent name) {
        ItemMeta meta = item.getItemMeta();
        meta.displayName(name);
        item.setItemMeta(meta);
        return item;
    }

    static ItemStack changeItemName(ItemStack item, String name) {
        return changeItemName(item, Component.text(name).decoration(TextDecoration.ITALIC, false));
    }

    static ItemStack createNamedItem(Material item, TextComponent name) {
        return changeItemName(new ItemStack(item), name);
    }

    static ItemStack createNamedItem(Material item, String name) {
        return changeItemName(new ItemStack(item), name);
    }

    static boolean playerIsInAGame(Player player, Stack<Game> games) {
        try {
            getPlayersGame(player, games);
            return true;
        }
        catch (NotFoundException err) {
            return false;
        }
    }

    static void renameHeldItem(String name, Player player) {
        PlayerInventory inventory = player.getInventory();
        inventory.setItemInMainHand(changeItemName(inventory.getItem(inventory.getHeldItemSlot()), name));
    }

    static int countOccurance(String string, String compare) {
        int ret = 0;
        while (string.contains(compare)) {
            string = string.replaceFirst(compare, "");
            ret++;
        }
        return ret;
    }

    static String replaceExcept(String replace, String thing, String with, String[] except) {
        int lastSeen = 0;
        for (int i = 0; i < countOccurance(replace, thing); i++) {
            if (except.length == 0) {
                replace = replace.replaceAll(thing, with);
            }
            else {
                String banned = null;
                for (String exception : except) {
                    if (replace.substring(lastSeen).indexOf(thing) == replace.substring(lastSeen).indexOf(exception)) {
                        banned = exception;
                    }
                }

                if (banned != null) {
                    lastSeen = replace.substring(lastSeen).indexOf(thing) + banned.length();
                    continue;
                }
                else {
                    replace = replace.replaceFirst(thing, with);
                }
            }
        }
        return replace;
    }
}