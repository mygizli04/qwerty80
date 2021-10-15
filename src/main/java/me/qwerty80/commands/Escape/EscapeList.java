package me.qwerty80.commands.Escape;

import org.bukkit.command.CommandSender;

import me.qwerty80.commands.EscapeCommandWithConsoleSupport;

public class EscapeList extends EscapeCommandWithConsoleSupport {
    public EscapeList() {
        super();
        super.usage = "/escape list";
        super.singleMethod = true;
    }

    @Override
    public void execute(String command, String[] args, CommandSender sender) {
        sender.sendMessage("§aThere are currently §b" + main.games.size() + "§a games active.");
    }
}
