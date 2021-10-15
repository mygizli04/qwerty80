package me.qwerty80.commands.Escape;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.qwerty80.commands.EscapeCommandArgumentCheckResult;
import me.qwerty80.commands.EscapeCommandExecutor;
import me.qwerty80.commands.EscapeCommandWithConsoleSupport;

public class EscapeLeave extends EscapeCommandWithConsoleSupport {

    @Override
    public EscapeCommandArgumentCheckResult checkArguments(String command, String[] args, CommandSender sender) {
        EscapeCommandArgumentCheckResult check = new EscapeCommandArgumentCheckResult();

        if (!(sender instanceof Player)) {
            if (args.length < 2) {
                check.result = false;
                check.reason = "Usage: /escape leave <player>";
                return check;
            }

            if (Bukkit.getServer().getPlayer(args[1]) == null) {
                check.result = false;
                check.reason = "§cThat player is not online!";
                return check;
            }
        }

        return check;
    }

    @Override
    public void execute(String command, String[] args, Player player) {
        EscapeCommandExecutor.executeCommand("spawn", new String[0], player);
    }

    @Override
    public void execute(String command, String[] args, CommandSender sender) {
        EscapeCommandExecutor.executeCommand("spawn", new String[]{args[1]}, sender);
    }
}
