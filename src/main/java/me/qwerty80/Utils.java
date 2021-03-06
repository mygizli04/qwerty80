package me.qwerty80;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
public class Utils {
    static public int random(double min, double max) {
        return (int) Math.round(Math.random() * (max - min) + min);
    }

    static public boolean percentage(double percent) {
        return Math.random() < (percent / 100);
    }

    static public boolean range(double num, double min, double max) {
        return (min <= num) && (num <= max);
    }

    static public <T> T choose(T[] array) {
        return array[random(0, array.length - 1)];
    }

    static public <From, To> To[] convertArray(From[] from, Class<To> to) {
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

    static public <T> boolean arrayIncludes(T[] array, T includes) {
        for (T item : array) {
            if (item == includes) {
                return true;
            }
        }
        return false;
    }

    static public <T> T choosePercentage(T[] array, int[] percentages) { // choosepercentage([1,2,7,3]) then it'll return int. || choosepercentage["6","9","6","9"] then it'll return string.
        int chance = random(0, 100);
        int currentChance = 0;

        for (int uwu = 0; uwu < array.length; uwu++) { // if uwu >= 1: && ++ == 
            if (range(chance, currentChance, percentages[uwu])) { // (uwu - 1)st/nd/rd/th argument of percentages hi :D
                return array[uwu];
            }
        }

        return null; // Should never happen (pls). :L
    }
    
    static public RegisteredServiceProvider<LuckPerms> lpProvider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
    static public LuckPerms luckperms = lpProvider.getProvider();
    
    static public boolean checkPermission(User user, String permission) {
        return user.getCachedData().getPermissionData().checkPermission(permission).asBoolean();
    }

    static public boolean checkPermission(Player player, String permission) {
        return checkPermission(luckperms.getPlayerAdapter(Player.class).getUser(player), permission);
    }

    static public boolean checkPermission(CommandSender sender, String permission) {
        if (sender instanceof Player) {
            return checkPermission((Player) sender, permission);
        }
        else {
            return true;
        }
    }

    static public boolean stringIsTruthy(String string) {
        if (string == null) {
            return false;
        }
        
        if (string.length() == 0) {
            return false;
        }

        return true;
    }

    static public void teleportPlayerToWorld(Player player, String world) {
        Location destination = Bukkit.getServer().getWorld(world).getSpawnLocation();
        player.teleport(destination);
    }

    static public void teleportPlayerToWorld(CommandSender sender, String world) {
        if (sender instanceof Player) {
            teleportPlayerToWorld((Player) sender, world);
        }
        else {
            Bukkit.getLogger().warning("Tried to teleport non-entity sender! Failing silently instead...");
        }
    }

    static public double chooseHighest(double[] array) {
        // I sure do hope no one passes a negative number here :)
        double ret = 0;
        for (double num : array) {
            if (num > ret) {
                ret = num;
            }
        }
        return ret;
    }

    static public void deleteRecursively(File toDelete) {
        File[] contents = toDelete.listFiles();
        if (contents != null) {
            for (File file : contents) {
                deleteRecursively(file);
            }
        }
        toDelete.delete();
    }

    static public void deleteRecursively(String toDelete) {
        deleteRecursively(new File(toDelete));
    }

    @SuppressWarnings("unchecked")
    static public ViaAPI<Player> viaApi = Via.getAPI();

    static public Game getPlayersGame(Player player) {
        Game[] ret = new Game[1]; // I hate java SO SO much....
        main.games.forEach(game -> {
            game.players.forEach(searchPlayer -> {
                if (searchPlayer == player) {
                    ret[0] = game;
                }
            });
        });
        return ret[0];
    }

    static public ItemStack changeItemName(ItemStack item, TextComponent name) {
        ItemMeta meta = item.getItemMeta();
        meta.displayName(name);
        item.setItemMeta(meta);
        return item;
    }

    static public ItemStack changeItemName(ItemStack item, String name) {
        if (name == null) {
            ItemMeta meta = item.getItemMeta();
            meta.displayName(null);
            item.setItemMeta(meta);
            return item;
        }
        else {
            return changeItemName(item, Component.text(name).decoration(TextDecoration.ITALIC, false));
        }
    }

    static public ItemStack createNamedItem(Material item, TextComponent name) {
        return changeItemName(new ItemStack(item), name);
    }

    static public ItemStack createNamedItem(Material item, String name) {
        if (item == Material.AIR) {
            return null;
        }
        else {
            return changeItemName(new ItemStack(item), name);
        }
    }

    static public boolean playerIsInAGame(Player player) {
        return getPlayersGame(player) != null;
    }

    static public void renameHeldItem(String name, Player player) {
        PlayerInventory inventory = player.getInventory();
        inventory.setItemInMainHand(changeItemName(inventory.getItem(inventory.getHeldItemSlot()), name));
    }

    static public int countOccurance(String string, String compare) {
        int ret = 0;
        while (string.contains(compare)) {
            string = string.replaceFirst(compare, "");
            ret++;
        }
        return ret;
    }

    static public String replaceExcept(String replace, String thing, String with, String[] except) {
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

    static public ItemStack addFlags(ItemStack item, ItemFlag[] flags) {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(flags);
        item.setItemMeta(meta);
        return item;
    }

    static public ItemStack addFlag(ItemStack item, ItemFlag flag) {
        return addFlags(item, new ItemFlag[]{
            flag
        });
    }

    static public Inventory createGui(int size, TextComponent name, ItemStack item1, ItemStack item2, InventoryOverride[] overrides) {
        Inventory inventory = Bukkit.getServer().createInventory(null, size, name);
        
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0) {
                inventory.setItem(i, item1);
            }
            else {
                inventory.setItem(i, item2);
            }
        }

        for (InventoryOverride override : overrides) {
            inventory.setItem(override.slot, override.item);
        }

        return inventory;
    }

    static public Inventory createGui(int size, String name, Material item1, Material item2, InventoryOverride[] overrides) {
        return createGui(size, Component.text(name), createNamedItem(item1, ""), createNamedItem(item2, ""), overrides);
    }

    static public Inventory createGui(int size, TextComponent name, Material item1, Material item2, InventoryOverride[] overrides) {
        return createGui(size, name, new ItemStack(item1), new ItemStack(item2), overrides);
    }

    static public Inventory createGui(int size, String name, ItemStack item1, ItemStack item2, InventoryOverride[] overrides) {
        return createGui(size, Component.text(name), item1, item2, overrides);
    }

    static public class InventoryOverride {
        int slot;
        ItemStack item;

        public InventoryOverride(int _slot, ItemStack _item) {
            slot = _slot;
            item = _item;
        }

        public InventoryOverride(int _slot, Material material) {
            slot = _slot;
            item = new ItemStack(material);
        }
    }

    static public <T> boolean arrayContains(T compare, T[] to) {
        boolean result = false;
        for (T comparing : to) {
            if (compare == comparing) {
                result = true;
            }
        }
        return result;
    }

    static public boolean arrayContains(int compare, int[] to) {
        boolean result = false;
        for (int comparing : to) {
            if (compare == comparing) {
                result = true;
            }
        }
        return result;
    }

    static public boolean objectArrayContains(Object compare, Object[] to) {
        boolean result = false;
        for (Object comparing : to) {
            if (compare.equals(comparing)) {
                result = true;
            }
        }
        return result;
    }

    static public boolean inRangeExcept(int num, int min, int max, int[] exclude) {
        return (min <= num) && (num <= max) && !arrayContains(num, exclude);
    }

    static public ItemStack createUnbreakableItem(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setUnbreakable(true);
        item.setItemMeta(itemMeta);
        item.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        return item;
    }

    static public int[] getEmptySlotsInInventory(Inventory inv) {
        ArrayList<Integer> slots = new ArrayList<>();
        ListIterator<ItemStack> iterator = inv.iterator();

        while (iterator.hasNext()) {
            ItemStack item = iterator.next();
            int index = iterator.hasNext() ? iterator.nextIndex() - 1 : inv.getSize();

            if (item == null) {
                slots.add(index);
            }
        }

        Integer[] integerArray = slots.toArray(new Integer[0]);
        int[] intArray = new int[integerArray.length];

        for (int i = 0; i < integerArray.length; i++) {
            intArray[i] = integerArray[i].intValue();
        }

        return intArray;
    }

    static public <T> T[] removeItemFromArray(T[] array, T element) {
        ArrayList<T> list = new ArrayList<>(Arrays.asList(array));

        list.remove(element);

        @SuppressWarnings("unchecked")
        T[] toReturn = (T[]) list.toArray();

        return toReturn;
    }

    static public Integer[] intArrayToIntegerArray(int[] nums) {
        Integer[] toRet = new Integer[nums.length];
        for (int i = 0; i < nums.length; i++) {
            toRet[i] = Integer.valueOf(nums[i]);
        }
        return toRet;
    }

    static public Qwerty80 main;

    static public boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException err) {
            return false;
        }
    }

    static public boolean teleportPlayerToGame(Player player, int game) {
        Game destination = main.games.get(game);
        
        if (destination == null && Bukkit.getServer().getWorld(game + "_GAME_island_water") == null) {
            return false;
        }

        teleportPlayerToWorld(player, destination == null ? game + "_GAME_island_water" : destination.world.getName());
        return true;

    }

    static public boolean canTeleportToGame(int game) {
        return !(main.games.get(game) == null && Bukkit.getServer().getWorld(game + "_GAME_island_water") == null);
    }
}