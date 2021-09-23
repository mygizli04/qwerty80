package me.qwerty80.commands;

import org.bukkit.entity.Player;
public class EscapeCommand {
    public final String usage = "/escape";

    private final String[] supportedCommands = new String[0];

    public String[] getSupportedCommands() {
        return supportedCommands;
    }

    public boolean checkArguments(String command ,String[] args) {
        return true;
    }

    public void execute(String command, String[] args, Player player) {

    }
}