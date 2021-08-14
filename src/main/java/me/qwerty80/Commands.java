package me.qwerty80;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

import net.kyori.adventure.text.Component;

// Handle everything command related.
// on command:
public class Commands implements CommandExecutor {

    Qwerty80 main; // This is a different class, but we still want to access the main class.

    public Commands(Qwerty80 that) {
        main = that;
    }

    // Unused for now
    void invalidCommand(String[] subcommands, CommandSender sender) {
        if (subcommands.length == 0) {
            sender.sendMessage("Invalid subcommand. You cannot use any subcommands of this command.");
        }
        else {
            String ret = "Invalid subcommand. Available subcommands are: ";
            for (int i = 0; i < subcommands.length; i++) {
                ret += subcommands[0];
                if (i != subcommands.length - 1) ret += ", ";
            }
            sender.sendMessage(ret);
        }
    }

    private String errorMessage(CommandSender sender) {
        String ret = "Unknown subcommand. Available subcommands are: ";
        if (Utils.checkPermission(sender, "escape.admin.startgame")) {
            ret += "startgame, ";
        }

        if (Utils.checkPermission(sender, "escape.admin.stopgame")) {
            ret += "stopgame, ";
        }

        if (Utils.checkPermission(sender, "escape.admin.getmap")) {
            ret += "getmap, ";
        }

        return ret.substring(0, ret.length() - 2);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        switch (cmd.getName().toLowerCase()) {
            // on command escape:
            case "escape":
                if (args.length == 0) { // If someone just runs /escape
                    sender.sendMessage("Invalid arguments provided. Valid subcommands are: list");
                }
                else {
                    switch (args[0]) { // First (0th) argument of command
                        case "list": // /escape list
                            sender.sendMessage("There are currently " + main.games.size() + " games active.");
                            break;
                        case "join": // /escape join
                            if (main.games.size() == 0) {
                                sender.sendMessage("There are no games to join!");
                                return true;
                            }

                            int destination = 0;

                            if (args.length >= 2) {
                                int target;
                                try {
                                    target = Integer.parseInt(args[1]);
                                }
                                catch (NumberFormatException err) {
                                    sender.sendMessage("The argument provided is not a valid number.");
                                    return true;
                                }

                                if (!Utils.range(target, 1, main.games.size())) {
                                    sender.sendMessage("That is not a valid game.");
                                    return true;
                                }

                                destination = target - 1;
                            }

                            if (sender instanceof Player) {
                                Utils.teleportPlayerToWorld(sender, destination + "_GAME_island_water");
                            }
                            else {
                                sender.sendMessage("I can't teleport the console you know...");
                            }
                            break;
                        case "admin":
                            if (!(sender instanceof Player)) {
                                sender.sendMessage("whoops sorry no console admin sad face");
                                return true;
                            }

                            Player player = (Player) sender;
                            
                            if (Utils.checkPermission(player, "escape.admin")) {
                                if (args.length < 2) {
                                    sender.sendMessage("oopsie daisy you haven't provided any argsies. Tip: Use tab lmao");
                                    return true;
                                }

                                switch (args[1]) {
                                    case "startgame":
                                        if (Utils.checkPermission(sender, "escape.admin.startgame")) {
                                            Bukkit.broadcast(Component.text("Warning: A new game is being generated. Please ignore the lag. We're sorry for the inconvenience"));
                                            main.games.add(new Game(main.games.size()));
                                        }
                                        else {
                                            sender.sendMessage(errorMessage(sender));
                                        }
                                        break;
                                    case "stopgame":
                                        if (Utils.checkPermission(sender, "escape.admin.stopgame")) {
                                            if (main.games.size() == 0) {
                                                sender.sendMessage("There are no games to stop!");
                                                return true;
                                            }
                                            if (args.length < 3) {
                                                Bukkit.broadcast(Component.text("Warning: A game instance is being removed. Please ignore the lag. We're sorry for the inconvenience"));
                                                main.games.get(0).delete();
                                                main.games.remove(0);
                                            }
                                            else {
                                                if (!(Utils.range(Integer.parseInt(args[2]), 1, main.games.size()))) {
                                                    sender.sendMessage("Cannot stop a game that doesn't exist!");
                                                    return true;
                                                }

                                                main.games.get(Integer.parseInt(args[2]) - 1).delete();
                                                main.games.remove(Integer.parseInt(args[2]) - 1);
                                            }
                                        }
                                        else {
                                            sender.sendMessage(errorMessage(sender));
                                        }
                                        break;
                                    case "getmap":
                                        if (Utils.checkPermission(sender, "escape.admin.getmap")) {
                                            if (sender instanceof Player) {
                                                sender.sendMessage("§a§lHere's the map of the island for island_water");
                                                ItemStack item = new ItemStack(Material.FILLED_MAP);
                                                MapMeta meta = (MapMeta) item.getItemMeta();
                                                meta.setMapId(103); // I know it's deprecated, but it's more convenient this way. Sometimes I wonder if convenience > functionality (in limited cases). Anyway, it's staying like this until I can be bothered to fix it.
                                                item.setItemMeta(meta);
                                                player.getInventory().addItem(item);
                                            }
                                        }
                                        else {
                                            sender.sendMessage("Console cant get maps...");
                                        }
                                        break;
                                    default:
                                        sender.sendMessage(errorMessage(sender));
                                }
                                return true;
                            }
                            return false;
                        default: // everything else
                            sender.sendMessage("Invalid arguments provided. Valid subcommands are: list, join");
                    }
                }
                return true;
            case "lobby":
                if (sender instanceof Player) {
                    sender.sendMessage("§a§lReturning to lobby...");
                    Location world = main.getServer().getWorld("empty").getSpawnLocation();
                    Player player = (Player) sender;
                    player.teleport(world);
                }
                else {
                    sender.sendMessage("Bruh I can't teleport the console anywhere...");
                }
                return true;
           // If the command isn't defined
            default:
                return false;     
        }
    }
}

// I'm muted for a min lol


/*
            case "getmap":
                if (sender instanceof Player) {
                    sender.sendMessage("§a§lHere's the map of the island for island_water");
                    Player player = (Player) sender;
                    player.getInventory.add(new ItemStack(Material.FILLED_MAP{map:103}, 1));
                }
                else {
                    sender.sendMessage("Console cant get maps...");
                }
*/