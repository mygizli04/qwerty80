package me.qwerty80;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;

import org.bukkit.Bukkit;

// This class will handle everything regarding a single game
public class Game {
    public Game(int id) {
        // Initilize multiverse
        MultiverseCore multiverse = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager worldManager = multiverse.getMVWorldManager();

        worldManager.cloneWorld("escape_new", id + "_GAME_escape_new");
    }
}
