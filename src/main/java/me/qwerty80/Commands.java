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
                            if (sender instanceof Player) { // Making sure a player sent the command.
                                Location world = main.getServer().getWorld("escape").getSpawnLocation(); // Getting the world
                                Player player = (Player) sender; // If the sender isn't a player this will error! This allows us to use teleport and stuff..
                                player.teleport(world); // I wonder what this does..
                            }
                            else {
                                sender.sendMessage("Only players can join games.");
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
                return true;
           // If the command isn't defined
            default:
                return false;     
        }
    }
}