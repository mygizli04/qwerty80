package me.qwerty80.commands.Escape;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.qwerty80.Utils;
import me.qwerty80.commands.EscapeCommandArgumentCheckResult;
import me.qwerty80.commands.EscapeCommandExecutor;
import me.qwerty80.commands.EscapeCommandWithConsoleSupport;

public class EscapeMain extends EscapeCommandWithConsoleSupport {

    public EscapeMain() {
        super();
        super.supportedCommands = new String[]{"escape"};
        super.usage = "/escape [list]";
    }

    @Override
    public EscapeCommandArgumentCheckResult checkArguments(String command, String[] args, CommandSender sender) {
        EscapeCommandArgumentCheckResult check = new EscapeCommandArgumentCheckResult();
        Player player = sender instanceof Player ? (Player) sender : null;

        if (args.length == 0) { // Only /escape (shows credits)
            return check;
        }

        if (args[0].equals("list") || args[0].equals("li")) { // /escape list
            return check;
        }

        if (args[0].equals("join") || args[0].equals("join")) { // /escape join [number] [player]

            if (main.games.size() == 0) {
                check.executable = false;
                check.reason = "§cThere are no games to join!";
                return check;
            }

            if (player != null && Utils.playerIsInAGame(player)) {
                check.executable = false;
                check.reason = "§cYou are already in a game!";
                return check;
            }

            check.executable = player != null || ((args.length == 3) && Utils.isNumber(args[1]));

            if (!check.executable) {
                check.reason = "Usage: /escape join <number> <player>";
            }

            return check;
        }

        check.executable = false;
        return check;
    }

    @Override
    public void execute(String command, String[] args, Player player) {
        if (args.length == 0) {
            EscapeCommandExecutor.executeCommand("credits", new String[0], player);
            return;
        }

        if (args[0].equals("list") || args[0].equals("li")) {
            EscapeCommandExecutor.executeCommand("list", new String[0], (CommandSender) player, new EscapeList());
            return;
        }

        if (args[0].equals("join") || args[0].equals("j")) {

        }
    }

    @Override
    public void execute(String command, String[] args, CommandSender sender) {
        if (args.length == 0) {
            EscapeCommandExecutor.executeCommand("credits", new String[0], sender);
            return;
        }

        if (args[0].equals("list") || args[0].equals("li")) {
            EscapeCommandExecutor.executeCommand("list", new String[0], sender, new EscapeList());
            return;
        }
    }
}
