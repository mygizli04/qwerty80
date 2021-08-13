package me.qwerty80;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabComplete implements TabCompleter {

    Qwerty80 plugin;

    public TabComplete(Qwerty80 _plugin) {
        plugin = _plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        switch (command.getName().toLowerCase()) {
            case "escape": // If the command is /escape
                List<String> ret = new ArrayList<String>(); // Create an empty list
                switch (args[0]) { 
                    case "list": // If first argument is list we don't have any more suggestions.
                        // So if the player types /escape list, we don't have anything else to suggest.
                        return ret;
                    case "join":
                        for (int i = 0; plugin.games.size() > i; i++) {
                            ret.add(Integer.toString(i + 1));
                        }
                        return ret;
                    default: 
                        ret.add("list");
                        ret.add("join");
                        return ret;
                }
            default:
                return null;
        }
    }
}
