package me.qwerty80.commands.Escape;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.qwerty80.Utils;
import me.qwerty80.commands.EscapeCommandArgumentCheckResult;
import me.qwerty80.commands.EscapeCommandWithConsoleSupport;

public class EscapeJoin extends EscapeCommandWithConsoleSupport {

    @Override
    public EscapeCommandArgumentCheckResult checkArguments(String command, String[] args, CommandSender sender) {
        Player player = sender instanceof Player ? (Player) sender : null;
        EscapeCommandArgumentCheckResult check = new EscapeCommandArgumentCheckResult();

        if (main.games.size() == 0) { // If there are no games, no one can join them!
            check.executable = false;
            check.reason = "§cThere are no games to join!";
            return check;
        }

        if (player != null && Utils.playerIsInAGame(player)) { // Make sure the player is not already in a game
            check.executable = false;
            check.reason = "§cYou are already in a game!";
            return check;
        }

        if (args.length > 1 && !Utils.isNumber(args[1])) { // The second argument must be a valid number
            check.executable = false;
            check.reason = "§cThat is not a valid game!";
            return check;
        }

        if (args.length > 1 && Bukkit.getServer().getWorld(args[1] + "_GAME_island_water") == null) { // The world they specified must exist!
            check.executable = false;
            check.reason = "§cThat is not a valid game!";
            return check;
        }

        if (player == null && args.length != 3) {
            check.executable = false;
            check.reason = "Usage: /escape join <number> <player>";
            return check;
        }

        if (player == null && Bukkit.getServer().getPlayer(args[2]) == null) {
            check.executable = false;
            check.reason = "§cThat player is not online!";
            return check;
        }

        return check;
    }

    @Override
    public void execute(String command, String[] args, Player player) { // /escape join [game]
        Utils.main.games.get(args.length > 1 ? Integer.parseInt(args[1]) : 0).playerJoin(player);
        Utils.teleportPlayerToWorld(player, (args.length > 1 ? args[1] : "0") + "_GAME_island_water" );
    }

    @Override
    public void execute(String command, String[] args, CommandSender sender) { // /escape join <game> <player>
        Player player = Bukkit.getServer().getPlayer(args[2]);
        int targetGame = Integer.parseInt(args[1]);

        main.games.get(targetGame).playerJoin(player);
        Utils.teleportPlayerToWorld(player, args[1] + "_GAME_island_water");
    }
}
