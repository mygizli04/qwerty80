package me.qwerty80.commands;

import org.bukkit.entity.Player;

public interface EscapeCommand {

    public final String usage = "/escape";

    public final String[] supportedCommands = new String[0];

    public boolean checkArguments(String command ,String[] args);

    public void execute(String command, String[] args, Player player);
}