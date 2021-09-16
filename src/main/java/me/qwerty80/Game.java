package me.qwerty80;

import java.util.ArrayList;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

// This class will handle everything regarding a single game
public class Game {

    Qwerty80 main;

    World world;
    public ArrayList<Player> players = new ArrayList<Player>();

    void setPlayerInventory(Player player) {
        Inventory inventory = player.getInventory();

        inventory.setItem(0, Utils.createUnbreakableItem(Material.DIAMOND_PICKAXE));
        inventory.setItem(15, Utils.createNamedItem(Material.PAPER, players.size() + " players alive."));
        inventory.setItem(16, new ItemStack(Material.PLAYER_HEAD, 1));
        inventory.setItem(17, new ItemStack(Material.PLAYER_HEAD, 1));
        inventory.setItem(24, new ItemStack(Material.PLAYER_HEAD, 1));
        inventory.setItem(25, new ItemStack(Material.PLAYER_HEAD, 1));
        inventory.setItem(26, new ItemStack(Material.PLAYER_HEAD, 1));
        inventory.setItem(33, new ItemStack(Material.PLAYER_HEAD, 1));
        inventory.setItem(34, new ItemStack(Material.PLAYER_HEAD, 1));
        inventory.setItem(35, new ItemStack(Material.PLAYER_HEAD, 1));
        inventory.setItem(12, new ItemStack(Material.BOOK, 1));
        inventory.setItem(13, new ItemStack(Material.BOOK, 1));
        inventory.setItem(14, new ItemStack(Material.BOOK, 1));
        inventory.setItem(7, new ItemStack(Material.SADDLE, 1));
        inventory.setItem(8, new ItemStack(Material.MAP, 1));
        inventory.setItem(6, new ItemStack(Material.ANVIL, 1));
        inventory.setItem(11, new ItemStack(Material.EXPERIENCE_BOTTLE, 1));
        inventory.setItem(9, new ItemStack(Material.STRUCTURE_VOID, 1));
        inventory.setItem(10, new ItemStack(Material.BARRIER, 1));
    }

    void updatePlayerInventories() {
        players.forEach(player -> {
            player.getInventory().setItem(15, Utils.createNamedItem(Material.PAPER, players.size() + " players alive."));
        });
    }

    public void playerJoin(Player player) {
        players.add(player);
        setPlayerInventory(player);
        updatePlayerInventories();
    }

    public void kickAllPlayers() {
        players.forEach(player -> {
            player.getInventory().clear();
            Utils.teleportPlayerToWorld(player, "empty");
        });
        players.removeAll(players);
    }

    public void playerLeave(Player player) {
        players.remove(player);
        Utils.teleportPlayerToWorld(player, "empty");
        updatePlayerInventories();
        player.getInventory().clear();
        main.teams.removePlayerFromTeams(player);
    }

    Material getMaterial(int x, int y, int z) {
        return getBlock(x, y, z).getType();
    }

    Block getBlock(int x, int y, int z) {
        return new Location(world, x, y, z).getBlock();
    }

    MultiverseCore multiverse = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
    MVWorldManager worldManager = multiverse.getMVWorldManager();
    int chestCount = 10000; // how many chests will be generated
    
    public void delete() {
        if (main.noWorldGen) {
            main.debug("Can't delete game because no world gen mode is activated!", null, "Game");
            return;
        }
        kickAllPlayers();
        worldManager.deleteWorld(world.getName());
    }
    
    public Game(int id, Qwerty80 _main) {

        main = _main;

        // Initilize multiverse
        world = Bukkit.getServer().getWorld(id + "_GAME_island_water");

        if (main.noWorldGen) {
            main.debug("No world gen mode activated! Expect the plugin to be broken in this state!", null, "Game");
            return;
        }
        
        if (world == null) {
            if (!worldManager.cloneWorld("island_water", id + "_GAME_island_water")) {
                Utils.deleteRecursively(Bukkit.getServer().getWorldContainer().getAbsolutePath() + "/" + id + "_GAME_island_water");
                if (!worldManager.cloneWorld("island_water", id + "_GAME_island_water")) {
                    Bukkit.getLogger().severe("Cannot clone world! Please check logs!");
                    Bukkit.getPluginManager().disablePlugin(_main);
                    return;
                }
            }
            world = Bukkit.getServer().getWorld(id + "_GAME_island_water");
        }

        for (int i = 0; i != chestCount; i++) {
            int x = Utils.random(-174, 941);
            int y = Utils.random(31, 112);
            int z = Utils.random(12, 887);

            while (getMaterial(x, y - 1, z) == Material.AIR) {
                if (y == 0) {
                    // Give up.
                    i--;
                    continue;
                }
                y -= 1;
            }

            if (getMaterial(x, y - 1, z) == Material.WATER) {
                // Don't gen on water, silly!
                i--;
                continue;
            }

            while (getMaterial(x, y, z) != Material.AIR) {
                if (y == 255) {
                    // Eh at this point don't even bother.
                    i--;
                    continue;
                }
                y += 1;
            }

            LootTable table = new LootTable();
            if (table.rare) {
                getBlock(x, y, z).setType(Material.ENDER_CHEST);
            }
            else {
                getBlock(x, y, z).setType(Material.CHEST);
            }
            LootChest chest = new LootChest(table, table.rare);
            chest.generate();
        }
    }
}
