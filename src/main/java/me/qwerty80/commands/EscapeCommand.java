package me.qwerty80.commands;

import org.bukkit.entity.Player;

import me.qwerty80.Qwerty80;

public class EscapeCommand {

    public Qwerty80 main;

    public EscapeCommand(Qwerty80 main) {
        this.main = main;
    }

    public final String usage = "/escape";

    public final String[] supportedCommands = new String[0];

    public boolean checkArguments(String command ,String[] args) {
        return true;
    }

    public void execute(String command, String[] args, Player player) {

    }
}