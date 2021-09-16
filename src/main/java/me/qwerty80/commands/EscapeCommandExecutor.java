package me.qwerty80.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.qwerty80.Qwerty80;
import me.qwerty80.Utils;

import org.bukkit.command.Command;

public class EscapeCommandExecutor implements CommandExecutor {
    Qwerty80 main;

    public EscapeCommandExecutor(Qwerty80 main) {
        this.main = main;
    }

    private final EscapeCommand[] commands = new EscapeCommand[]{};
    private final EscapeCommandWithConsoleSupport[] commandsWithConsole = new EscapeCommandWithConsoleSupport[]{};

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("[INSERT CREDITS HERE]");
            return true;
        }

        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }

        for (EscapeCommand command : commands) {
            if (Utils.arrayContains(cmd, command.supportedCommands)) {
                if (command.checkArguments(cmd.getName(), args)) {
                    command.execute(cmd.getName(), args, player);
                }
                else {
                    sender.sendMessage("Usage: " + command.usage);
                }
                return true;
            }
        }

        for (EscapeCommandWithConsoleSupport command : commandsWithConsole) {
            if (Utils.arrayContains(cmd, command.supportedCommands)) {
                boolean isPlayer = player != null;
                if (command.checkArguments(cmd.getName(), args, isPlayer)) {
                    if (isPlayer) {
                        command.execute(cmd.getName(), args, player);
                    }
                    else {
                        command.execute(cmd.getName(), args, sender);
                    }
                }
                else {
                    sender.sendMessage("Usage: " + command.usage);
                }
                return true;
            }
        }

        main.debug("Can't find command " + cmd.getName(), player, "EscapeCommandExecutor");

        return false;
    }
    
}
