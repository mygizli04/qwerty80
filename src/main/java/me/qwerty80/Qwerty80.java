package me.qwerty80;

import java.util.Stack;

import org.bukkit.plugin.java.JavaPlugin;

// Main class
public class Qwerty80 extends JavaPlugin {
    public Stack<Game> games = new Stack<Game>(); // This stack will have every game instance.

    Commands commandHandler = new Commands(this);

    @Override
    public void onEnable() {
        getCommand("escape").setExecutor(commandHandler);
        getCommand("escape").setTabCompleter(new TabComplete());

        getCommand("Lobby").setExecutor(commandHandler);
    }
}
