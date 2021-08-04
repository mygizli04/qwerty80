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

    int chestCount = 50; // how many chests will be generated
    
    public Game(int id) {
        // Initilize multiverse
        MultiverseCore multiverse = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager worldManager = multiverse.getMVWorldManager();

        worldManager.cloneWorld("escape_new", id + "_GAME_escape_new");
        world = Bukkit.getServer().getWorld(id + "_GAME_escape_new");

        for (int i = 0; i != chestCount; i++) {
            int x = Utils.random(-69, 447);
            int y = Utils.random(77, 78);
            int z = Utils.random(-69, 447);

            if (y == 78 && getMaterial(x, 77, z) == Material.AIR) {
                y = 77;
            }

            if (getMaterial(x, y, z) == Material.AIR) {
                getBlock(x, y, z).setType(Material.CHEST);
                LootTable table = new LootTable();
                Loot loot = table.generate();
                LootChest chest = new LootChest(loot, new Location(world, x, y, z), loot.loot.length);
                chest.generate();
            }
        }
    }
}
