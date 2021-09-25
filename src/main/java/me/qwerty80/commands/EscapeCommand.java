package me.qwerty80.commands;

import org.bukkit.entity.Player;

import me.qwerty80.Qwerty80;
import me.qwerty80.Utils;
public class EscapeCommand {
    
    public final Qwerty80 main = Utils.main;

    public String usage = "/escape";

    public String[] supportedCommands = new String[0];

    public EscapeCommandArgumentCheckResult checkArguments(String command ,String[] args) {
        return new EscapeCommandArgumentCheckResult();
    }

    public void execute(String command, String[] args, Player player) {

    }
}