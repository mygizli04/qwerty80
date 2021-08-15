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

    @Override
    public void onEnable() {
        getCommand("escape").setExecutor(commandHandler);
        getCommand("escape").setTabCompleter(tabCompleter);

        getCommand("Lobby").setExecutor(commandHandler);

        getServer().getPluginManager().registerEvents(new ChestGUI(), this);
        getServer().getPluginManager().registerEvents(new PlayerEvents(this), this);

        // Ready multiverse
        MultiverseCore multiverse = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core"); // <relevant line for help on discord>
        MVWorldManager worldManager = multiverse.getMVWorldManager();

        //Delete old worlds (if any)
        Collection<MultiverseWorld> worlds = worldManager.getMVWorlds();
        worlds.forEach(world -> {
            if (world.getName().endsWith("_GAME_island_water")) {
                worldManager.deleteWorld(world.getName());
            }
        });

        // Create new game
        games.add(new Game(0));
    }
}
