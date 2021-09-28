package me.qwerty80.commands.Spawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.qwerty80.Utils;
import me.qwerty80.commands.EscapeCommandArgumentCheckResult;
import me.qwerty80.commands.EscapeCommandWithConsoleSupport;

public class SpawnMain extends EscapeCommandWithConsoleSupport {

    public SpawnMain() {
        super.singleMethod = true;
        super.supportedCommands = new String[]{"spawn"};
    }

    private final boolean debugMode = false; // Disables checks for returning to spawn and does not take you out of the game anymore.

    public final String usage = "/spawn";

    public String[] getSupportedCommands() {
        return this.supportedCommands;
    }

    @Override
    public EscapeCommandArgumentCheckResult checkArguments(String command, String[] args, CommandSender sender) {
        EscapeCommandArgumentCheckResult check = new EscapeCommandArgumentCheckResult();
        Player player = sender instanceof Player ? (Player) sender : null;

        if (debugMode) {
            return check;
        }

        if (player == null && args.length < 1) { // /spawn [player]
            check.result = false;
            check.reason = "Usage: /spawn <player>";
            return check;
        }

        player = player == null ? Bukkit.getServer().getPlayer(args[0]) : player;

        if (player == null) {
            check.result = false;
            check.reason = "§cThat player is not online!";
            return check;
        }

        if (player.getWorld().getName().equals("empty")) {
            check.result = false;
            check.reason = sender instanceof Player ? "§cYou are already in the lobby!" : "§cThat player is already in the lobby!";
            return check;
        }

        if (!Utils.playerIsInAGame(player)) {
            check.result = false;
            check.reason = sender instanceof Player ? "§cYou are not in a game!" : "§cThat player is not in a game!";
            return check;
        }

        return check;
    }

    @Override
    public void execute(String command, String[] args, Player player) {
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

    @Override
    public void execute(String command, String[] args, CommandSender sender) {
        Player player = Bukkit.getServer().getPlayer(args[0]);

        if (!debugMode) {
            Utils.getPlayersGame(player).playerLeave(player);
        }
        player.sendMessage("§aYou have been returned to the lobby by the console.");
        sender.sendMessage("§aSending the player to spawn...");

        Location world;
        try {
            world = Utils.main.getServer().getWorld("empty").getSpawnLocation();
        }
        catch (NullPointerException err) {
            player.sendMessage("§cUh oh, an unexpected error occured while trying to teleport you to spawn. Please contact the server admins about this.\n" + err.getMessage());
            sender.sendMessage("§cUh oh, an unexpected error occured while trying to teleport this player.\n" + err.getMessage());
            return;
        }
        player.teleport(world);
    }
}
