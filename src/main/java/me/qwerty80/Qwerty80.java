package me.qwerty80;

import java.util.Collection;
import java.util.Stack;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

// Main class
public class Qwerty80 extends JavaPlugin {
    public Stack<Game> games = new Stack<Game>(); // This stack will have every game instance.

    Commands commandHandler = new Commands(this);
    TabComplete tabCompleter = new TabComplete(this);

    ItemGUI itemGui = new ItemGUI(this);

    MultiverseCore multiverse;
    MVWorldManager worldManager;

    @Override
    public void onEnable() {
        getCommand("escape").setExecutor(commandHandler);
        getCommand("escape").setTabCompleter(tabCompleter);

        getCommand("spawn").setExecutor(commandHandler);

        getServer().getPluginManager().registerEvents(itemGui, this);
        getServer().getPluginManager().registerEvents(new ChestGUI(), this);
        getServer().getPluginManager().registerEvents(new PlayerEvents(this), this);

        // Ready multiverse
        multiverse = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core"); // <relevant line for help on discord>
        worldManager = multiverse.getMVWorldManager();

        // Create new game
        games.add(new Game(0));

        worldManager.getMVWorlds().forEach(world -> {
            if (world.getName().endsWith("_GAME_island_water")) {
                int gameNumber = Integer.parseInt(world.getName().substring(0, world.getName().indexOf("_")));
                if (gameNumber == 0) {return;}
                getLogger().info("Ongoing game #" + gameNumber + " detected.");
                games.add(new Game(gameNumber));
            }
        });;
    }

    @Override
    public void onDisable() {
        // Delete worlds
        Collection<MultiverseWorld> worlds = worldManager.getMVWorlds();
        worlds.forEach(world -> {
            if (world.getName().endsWith("_GAME_island_water")) {
                worldManager.deleteWorld(world.getName());
            }
        });
    }
}
