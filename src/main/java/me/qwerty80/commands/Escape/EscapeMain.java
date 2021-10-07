package me.qwerty80.commands.Escape;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.qwerty80.commands.EscapeCommandArgumentCheckResult;
import me.qwerty80.commands.EscapeCommandExecutor;
import me.qwerty80.commands.EscapeCommandWithConsoleSupport;
import me.qwerty80.commands.Escape.Admin.EscapeAdminMain;

public class EscapeMain extends EscapeCommandWithConsoleSupport {

    public EscapeMain() {
        super();
        super.supportedCommands = new String[]{"escape"};
        super.usage = "/escape [list|join|leave]";
    }

    @Override
    public EscapeCommandArgumentCheckResult checkArguments(String command, String[] args, CommandSender sender) {
        EscapeCommandArgumentCheckResult check = new EscapeCommandArgumentCheckResult();

        if (args.length == 0) { // Only /escape (shows credits)
            return check;
        }

        if (args[0].equals("list") || args[0].equals("li")) { // /escape list
            return check;
        }

        if (args[0].equals("join") || args[0].equals("j")) { // /escape join [number] [player]
            return new EscapeJoin().checkArguments(command, args);
        }

        if (args[0].equals("leave") || args[0].equals("le")) { // escape leave [player]
            return new EscapeLeave().checkArguments(command, args);
        }

        if (args[0].equals("admin")) { // escape admin [startgame|stopgame|getmap]
            return new EscapeAdminMain().checkArguments(command, args);
        }

        check.result = false;
        return check;
    }

    @Override
    public void execute(String command, String[] args, Player player) {
        if (args.length == 0) {
            EscapeCommandExecutor.executeCommand("credits", new String[0], player);
            return;
        }

        if (args[0].equals("list") || args[0].equals("li")) {
            EscapeCommandExecutor.executeCommand("escape", new String[0], (CommandSender) player, new EscapeList());
            return;
        }

        if (args[0].equals("join") || args[0].equals("j")) {
            EscapeCommandExecutor.executeCommand("escape", args, (CommandSender) player, new EscapeJoin());
            return;
        }

        if (args[0].equals("leave") || args[0].equals("le")) {
            EscapeCommandExecutor.executeCommand("escape", args, (CommandSender) player, new EscapeLeave());
            return;
        }

        if (args[0].equals("admin")) {
            EscapeCommandExecutor.executeCommand("escape", args, (CommandSender) player, new EscapeAdminMain());
            return;
        }
    }

    @Override
    public void execute(String command, String[] args, CommandSender sender) {
        if (args.length == 0) {
            EscapeCommandExecutor.executeCommand("credits", args, sender);
            return;
        }

        if (args[0].equals("list") || args[0].equals("li")) {
            EscapeCommandExecutor.executeCommand("list", args, sender, new EscapeList());
            return;
        }

        if (args[0].equals("join") || args[0].equals("j")) {
            EscapeCommandExecutor.executeCommand("escape", args, sender, new EscapeJoin());
            return;
        }

        if (args[0].equals("leave") || args[0].equals("le")) {
            EscapeCommandExecutor.executeCommand("escape", args, sender, new EscapeLeave());
            return;
        }
    }
}
