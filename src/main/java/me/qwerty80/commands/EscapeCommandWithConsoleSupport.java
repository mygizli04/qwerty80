package me.qwerty80.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EscapeCommandWithConsoleSupport {

    public final String usage = "/escape";

    public final String[] supportedCommands = new String[0];

    public boolean checkArguments(String command, String[] args, boolean isPlayer) {
        return true;
    }

    public void execute(String command, String[] args, Player player) {

    }

    public void execute(String command, String[] args, CommandSender sender) {

    }
}