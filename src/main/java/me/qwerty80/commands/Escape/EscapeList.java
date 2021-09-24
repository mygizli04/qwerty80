package me.qwerty80.commands.Escape;

import org.bukkit.entity.Player;

import me.qwerty80.commands.EscapeCommandWithConsoleSupport;

public class EscapeList extends EscapeCommandWithConsoleSupport {
    public EscapeList() {
        super();
        super.usage = "/escape list";
        super.supportedCommands = new String[]{
            "list",
            "li"
        };
    }

    @Override
    public void execute(String command, String[] args, Player player) {
        player.sendMessage("§aThere are currently §b" + main.games.size() + "§a games active.");
    }
}
