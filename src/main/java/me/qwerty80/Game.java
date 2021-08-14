package me.qwerty80;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

// This class will handle everything regarding a single game
public class Game {

    World world;

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
        worldManager.deleteWorld(world.getName());
    }
    
    public Game(int id) {
        // Initilize multiverse

        worldManager.cloneWorld("island_water", id + "_GAME_island_water");
        world = Bukkit.getServer().getWorld(id + "_GAME_island_water");
        worldManager.getMVWorld(world).setKeepSpawnInMemory(false);

        for (int i = 0; i != chestCount; i++) {
            int x = Utils.random(-174, 941);
            int y = Utils.random(31, 38);
            int z = Utils.random(12, 887);

            while (getMaterial(x, y - 1, z) == Material.AIR) {
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
