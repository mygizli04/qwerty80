package me.qwerty80;

import java.util.Stack;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.qwerty80.commands.EscapeCommandExecutor;
import net.kyori.adventure.text.Component;

enum DebugMode { // Where to send debug messages to
    DISABLED, // Debug messages are ignored
    CONSOLE, // Debug messages will be sent to the console
    PLAYER, // Debug messages will be sent to the player if appropriate (or else it will be broadcasted)
    BROADCAST // Broadcast all debug messages
}

// Main class
public class Qwerty80 extends JavaPlugin {
    private final DebugMode debugMode = DebugMode.DISABLED; // Change this to enable debug mode!
    private final String[] enableDebugFor = new String[]{}; // Add things to here to enable only certain debug mode from senders! For example to only get debug messages from DiamondPickaxe: new String[]{"DiamondPickaxe", "something else", "etc"}
    public final boolean debugModeEnabled = debugMode != DebugMode.DISABLED;
    public final boolean noWorldGen = true; // Set this to true for no world gen.

    //@SuppressWarnings("unused") // no idea why it think that some code is unreachable, will find out in the future i guess!
    public void debug(String message, Player player, String sender) {

        String prefix = "[DEBUG] [" + sender + "] ";
        prefix += (player != null) && (debugMode == DebugMode.PLAYER) ? "[" + player.getName() + "] " : ""; // removing this line gets rid of the dead code warning
        
        if (enableDebugFor.length > 0 && !Utils.arrayContains(sender, enableDebugFor)) {
            return;
        }

        switch (debugMode) {
            case DISABLED:
                // Ignore
                break;
            case CONSOLE:
                getLogger().info(prefix + message);
                break;
            case PLAYER:
                if (player != null) {
                    player.sendMessage(Component.text(prefix + message));
                }
                else {
                    getServer().broadcast(Component.text(prefix + message));
                }
                break;
            case BROADCAST:
                getServer().broadcast(Component.text(message));
                
        }
    }

    public Stack<Game> games = new Stack<Game>(); // This stack will have every game instance.
    public CustomTeams teams = new CustomTeams(this);

    EscapeCommandExecutor commandHandler = new EscapeCommandExecutor(this);
    TabComplete tabCompleter = new TabComplete(this);

    MultiverseCore multiverse;
    MVWorldManager worldManager;

    @Override
    public void onEnable() {
        Utils.main = this;
        
        getCommand("escape").setExecutor(commandHandler);
        getCommand("escape").setTabCompleter(tabCompleter);

        getCommand("spawn").setExecutor(commandHandler);
        
        getCommand("credits").setExecutor(commandHandler);

        getServer().getPluginManager().registerEvents(new ItemGUI(this), this);
        getServer().getPluginManager().registerEvents(new ChestGUI(this), this);
        getServer().getPluginManager().registerEvents(new PlayerEvents(this), this);
        getServer().getPluginManager().registerEvents(new ArrowQuiver(this), this);
        getServer().getPluginManager().registerEvents(teams, this);
        getServer().getPluginManager().registerEvents(new DiamondPickaxe(this), this);

        // Ready multiverse
        multiverse = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core"); // <relevant line for help on discord>
        worldManager = multiverse.getMVWorldManager();

        // Create new game
        games.add(new Game(0, this));

        worldManager.getMVWorlds().forEach(world -> {
            if (world.getName().endsWith("_GAME_island_water")) {
                int gameNumber = Integer.parseInt(world.getName().substring(0, world.getName().indexOf("_")));
                if (gameNumber == 0) {return;}
                getLogger().info("Ongoing game #" + gameNumber + " detected.");
                games.add(new Game(gameNumber, this));
            }
        });
    }

    @Override
    public void onDisable() {
        games.forEach(game -> {
            game.delete();
        });
    }
}
