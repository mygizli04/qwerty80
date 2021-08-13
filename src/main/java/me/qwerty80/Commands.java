package me.qwerty80;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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