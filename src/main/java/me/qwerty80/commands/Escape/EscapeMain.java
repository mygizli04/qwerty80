package me.qwerty80.commands.Escape;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.qwerty80.commands.EscapeCommandExecutor;
import me.qwerty80.commands.EscapeCommandWithConsoleSupport;

public class EscapeMain extends EscapeCommandWithConsoleSupport {

    public EscapeMain() {
        super();
        super.supportedCommands = new String[]{"escape"};
    }

    @Override
    public boolean checkArguments(String command, String[] args, boolean isPlayer) {
        if (args.length == 0) { // Only /escape (shows credits)
            return true;
        }

        return false;
    }

    @Override
    public void execute(String command, String[] args, Player player) {
        if (args.length == 0) {
            EscapeCommandExecutor.executeCommand("credits", new String[0], player);
            return;
        }
    }

    @Override
    public void execute(String command, String[] args, CommandSender sender) {
        if (args.length == 0) {
            EscapeCommandExecutor.executeCommand("credits", new String[0], sender);
        }
    }
}
