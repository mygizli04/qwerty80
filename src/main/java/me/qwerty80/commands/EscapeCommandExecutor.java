package me.qwerty80.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.qwerty80.Qwerty80;
import me.qwerty80.Utils;

import org.bukkit.command.Command;

public class EscapeCommandExecutor implements CommandExecutor {

    private final boolean debugMode = false; // Enables more aggresive debug features such as ignoring command requirements.

    Qwerty80 main;

    public EscapeCommandExecutor(Qwerty80 main) {
        this.main = main;
    }

    private final static EscapeCommand[] commands = new EscapeCommand[]{
        new me.qwerty80.commands.Spawn.SpawnMain(),
    };
    private final static EscapeCommandWithConsoleSupport[] commandsWithConsole = new EscapeCommandWithConsoleSupport[]{
        new me.qwerty80.commands.Credits.CreditsMain(),
        new me.qwerty80.commands.Escape.EscapeMain()
    };

    public static void executeCommand(String command, String[] args, CommandSender sender) {
        for (EscapeCommand currentCommand : commands) {
            if (Utils.objectArrayContains(command, currentCommand.supportedCommands)) {
                currentCommand.execute(command, args, (Player) sender);
            }
        }

        for (EscapeCommandWithConsoleSupport currentCommand : commandsWithConsole) {
            if (Utils.objectArrayContains(command, currentCommand.supportedCommands)) {
                currentCommand.execute(command, args, sender instanceof Player ? (Player) sender : sender);
            }
        }
    }

    public static EscapeCommandArgumentCheckResult executeCommand(String command, String[] args, Player player, EscapeCommand customCommand) {
        EscapeCommandArgumentCheckResult result = customCommand.checkArguments(command, args);

        if (result.executable) {
            customCommand.execute(command, args, player);
            return null;
        }
        else {
            return result;
        }
    }

    public static EscapeCommandArgumentCheckResult executeCommand(String command, String[] args, CommandSender sender, EscapeCommandWithConsoleSupport customCommand) {
        EscapeCommandArgumentCheckResult result = customCommand.checkArguments(command, args);

        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }

        if (result.executable) {
            if (!customCommand.singleMethod && player != null) {
                customCommand.execute(command, args, player);
            }
            else {
                customCommand.execute(command, args, sender);
            }
            return null;
        }
        else {
            return result;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }

        for (EscapeCommand command : commands) {
            if (Utils.objectArrayContains(cmd.getName(), command.supportedCommands)) {

                if (debugMode) {
                    main.debug("Ignoring command requirements for command " + cmd.getName(), player, "EscapeCommandExecutor");
                    command.execute(cmd.getName(), args, player);
                    return true;
                }

                if (player == null) {
                    sender.sendMessage("This command cannot be executed by the console.");
                    return true;
                }

                EscapeCommandArgumentCheckResult check = command.checkArguments(cmd.getName(), args);

                if (check.executable) {
                    command.execute(cmd.getName(), args, player);
                }
                else if (check.reason != null) {
                    sender.sendMessage(check.reason);
                }
                else {
                    sender.sendMessage("Usage: " + command.usage);
                }
                return true;
            }
        }

        for (EscapeCommandWithConsoleSupport command : commandsWithConsole) {
            if (Utils.objectArrayContains(cmd.getName(), command.supportedCommands)) {

                if (debugMode) {
                    main.debug("Ignoring command requirements for command " + cmd.getName(), player, "EscapeCommandExecutor");
                    command.execute(cmd.getName(), args, player);
                    return true;
                }

                boolean isPlayer = player != null;
                EscapeCommandArgumentCheckResult check = command.checkArguments(cmd.getName(), args, isPlayer);
                if (check.executable) {
                    if (isPlayer && !command.singleMethod) {
                        command.execute(cmd.getName(), args, player);
                    }
                    else {
                        command.execute(cmd.getName(), args, sender);
                    }
                }
                else if (check.reason != null) {
                    sender.sendMessage(check.reason);
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
