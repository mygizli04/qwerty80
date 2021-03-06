package me.qwerty80.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EscapeCommandWithConsoleSupport extends EscapeCommand {
    /**
     * If {@link #execute(String, String[], Player)} will be called regardless of if the caller is a player
     */
    public boolean singleMethod = false;

    /**
     * Checks if the arguments passed on to the command are valid.
     * 
     * If this method returns true, the command is executed. Else, usage will be displayed.
     * 
     * @param command The command that's executed
     * @param args Arguments to the command
     * @param sender The sender who initiated the command
     * @return Whether or not the command should be executed
     */
    public EscapeCommandArgumentCheckResult checkArguments(String command, String[] args, CommandSender sender) {
        return new EscapeCommandArgumentCheckResult();
    }

    @Override
    public EscapeCommandArgumentCheckResult checkArguments(String command, String[] args, Player player) {
        return checkArguments(command, args, (CommandSender) player);
    }

    /**
     * This method will be called in order to execute the command in the case that the executor is a player and {@link #singleMethod} is false
     * 
     * @param command The command that's executed
     * @param args Arguments to the command
     * @param player The player that executed the command
     */
    public void execute(String command, String[] args, Player player) {

    }

    /**
     * This method will be called in order to execute the command (in the case that the executor is NOT a player) or when {@link #singleMethod} is true
     * 
     * @param command The command that's executed
     * @param args Arguments to the command
     * @param sender
     */
    public void execute(String command, String[] args, CommandSender sender) {

    }
}
