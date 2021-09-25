package me.qwerty80.commands.Escape;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
    public EscapeCommandArgumentCheckResult checkArguments(String command, String[] args, boolean isPlayer) {
        EscapeCommandArgumentCheckResult check = new EscapeCommandArgumentCheckResult();

        if (args.length == 0) { // Only /escape (shows credits)
            return check;
        }

        if (args[0].equals("list") || args[0].equals("li")) { // /escape list
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
