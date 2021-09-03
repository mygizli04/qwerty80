package me.qwerty80;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class TabComplete implements TabCompleter {

    Qwerty80 main;

    public TabComplete(Qwerty80 _main) {
        main = _main;
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
                        for (int i = 0; main.games.size() > i; i++) {
                            ret.add(Integer.toString(i + 1));
                        }
                        return ret;
                    case "admin":
                        if (Utils.checkPermission(sender, "escape.admin")) {
                            if (Utils.checkPermission(sender, "escape.admin.stopgame")) {
                                ret.add("stopgame");
                            }
                            if (Utils.checkPermission(sender, "escape.admin.startgame")) {
                                ret.add("startgame");
                            }
                            if (Utils.checkPermission(sender, "escape.admin.getmap")) {
                                ret.add("getmap");
                            }
                            return ret;
                        }
                        break;
                    case "team":
                        ret.add("join");
                        ret.add("leave");

                        if (args.length > 2) {
                            if (args[2] == "join") {
                                for (Player player : main.teams.getPlayersLookingForTeam()) {
                                    ret.add(player.getName());
                                }                                
                            }
                        }
                        return ret;
                    default: 
                        ret.add("list");
                        ret.add("join");
                        if (Utils.checkPermission(sender, "escape.admin")) {
                            ret.add("admin");
                        }
                        ret.add("team");
                        return ret;
                }
            default:
                return null;
        }
    }
}
