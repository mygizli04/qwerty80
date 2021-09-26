package me.qwerty80.commands.Spawn;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.qwerty80.Utils;
import me.qwerty80.commands.EscapeCommandWithConsoleSupport;

public class SpawnMain extends EscapeCommandWithConsoleSupport {

    public SpawnMain() {
        super.singleMethod = true;
    }

    private final boolean debugMode = false; // Disables checks for returning to spawn and does not take you out of the game anymore.

    public final String usage = "/spawn";

    private final String[] supportedCommands = new String[]{"spawn"};

    public String[] getSupportedCommands() {
        return this.supportedCommands;
    }

    public void execute(String command, String[] args, Player player) {
        if (!debugMode && player.getWorld().getName().equals("empty")) {
            player.sendMessage("§cYou are already in the lobby!");
            return;
        }

        if (!debugMode && !Utils.playerIsInAGame(player)) {
            player.sendMessage("§cYou are not in a game!");
            return;
        }

        if (!debugMode) {
            Utils.getPlayersGame(player).playerLeave(player);
        }
        player.sendMessage("§aReturning to lobby...");

        Location world;
        try {
            world = Utils.main.getServer().getWorld("empty").getSpawnLocation();
        }
        catch (NullPointerException err) {
            player.sendMessage("§cUh oh, an unexpected error occured while trying to teleport you to spawn. Please contact the server admins about this.\n" + err.getMessage());
            return;
        }
        player.teleport(world);
    }
}
