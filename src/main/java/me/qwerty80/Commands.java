package me.qwerty80;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

import me.qwerty80.Exceptions.NotFoundException;
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
            sender.sendMessage("§cInvalid subcommand. You cannot use any subcommands of this command.");
        }
        else {
            String ret = "§cInvalid subcommand. Available subcommands are: ";
            for (int i = 0; i < subcommands.length; i++) {
                ret += subcommands[0];
                if (i != subcommands.length - 1) ret += ", ";
            }
            sender.sendMessage(ret);
        }
    }

    private String errorMessage(CommandSender sender) {
        String ret = "§cUnknown subcommand. Available subcommands are: ";
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

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        switch (cmd.getName().toLowerCase()) {
            // on command escape:
            case "escape":
                if (args.length == 0) { // If someone just runs /escape
                    sender.sendMessage("§cInvalid arguments provided. Valid subcommands are: list");
                }
                else {
                    switch (args[0]) { // First (0th) argument of command
                        case "list": // /escape list
                        case "li":
                            sender.sendMessage("§aThere are currently §b" + main.games.size() + "§a games active.");
                            break;
                        case "join": // /escape join
                        case "j":
                            if (main.games.size() == 0) {
                                sender.sendMessage("§cThere are no games to join!");
                                return true;
                            }

                            if (sender instanceof Player) {
                                try {
                                    Utils.getPlayersGame((Player) sender, main.games);
                                    sender.sendMessage("§cYou're already in-game!");
                                    return true;
                                }
                                catch (NotFoundException err) {
                                    // Nothing.
                                }
                            }
                            else {
                                sender.sendMessage("Uhhh I don't have magical powers to teleport the console y'know..");
                                return true;
                            }

                            int destination = 0;

                            if (args.length >= 2) {
                                int target;
                                try {
                                    target = Integer.parseInt(args[1]);
                                }
                                catch (NumberFormatException err) {
                                    sender.sendMessage("§cThe argument provided is not a valid number.");
                                    return true;
                                }

                                if (!Utils.range(target, 1, main.games.size())) {
                                    sender.sendMessage("§cThat is not a valid game.");
                                    return true;
                                }

                                destination = target - 1;
                            }

                            if (sender instanceof Player) {
                                main.games.get(destination).playerJoin((Player) sender);
                                Utils.teleportPlayerToWorld(sender, destination + "_GAME_island_water");
                            }
                            else {
                                sender.sendMessage("I can't teleport the console you know...");
                            }
                            break;
                        case "le":
                        case "leave":
                            //identical to spawn, Changed a few things
                            if (sender instanceof Player) {
                                Player player = (Player) sender;
                            /* if (player.getWorld().getName() == "empty") {
                                    sender.sendMessage("§cYou are already in the lobby!");
                                    return true;
                                } */
                                sender.sendMessage("§aReturning to lobby...");
                                try {
                                    Utils.getPlayersGame(player, main.games).playerLeave(player);
                                    sender.sendMessage("§cLeaving Game");
                                }
                                catch (NotFoundException err) {
                                    /*sender.sendMessage("§cYou are not in a game!");
                                    return true;*/
                                }
                                Location world = main.getServer().getWorld("empty").getSpawnLocation();
                                player.teleport(world);
                            }
                            else {
                                sender.sendMessage("Bruh I can't teleport the console anywhere...");
                            }
                            return true;
                        case "admin":
                            if (Utils.checkPermission(sender, "escape.admin")) {
                                if (args.length < 2) {
                                    sender.sendMessage("§coopsie daisy you haven't provided any argsies. Tip: Use tab lmao");
                                    return true;
                                }

                                switch (args[1]) {
                                    case "startgame":
                                        if (Utils.checkPermission(sender, "escape.admin.startgame")) {
                                            Bukkit.broadcast(Component.text("§eWarning: A new game is being generated. Please ignore the lag. We're sorry for the inconvenience"));
                                            main.games.add(new Game(main.games.size(), main));
                                        }
                                        else {
                                            sender.sendMessage(errorMessage(sender));
                                        }
                                        break;
                                    case "stopgame":
                                        if (Utils.checkPermission(sender, "escape.admin.stopgame")) {
                                            if (main.games.size() == 0) {
                                                sender.sendMessage("§cThere are no games to stop!");
                                                return true;
                                            }
                                            if (args.length < 3) {
                                                Bukkit.broadcast(Component.text("§eWarning: A game instance is being removed. Please ignore the lag. We're sorry for the inconvenience"));
                                                main.games.get(0).delete();
                                                main.games.remove(0);
                                            }
                                            else {
                                                if (!(Utils.range(Integer.parseInt(args[2]), 1, main.games.size()))) {
                                                    sender.sendMessage("§cCannot stop a game that doesn't exist!");
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
                                                Player player = (Player) sender;
                                                sender.sendMessage("§aHere's the map of the island for island_water");
                                                ItemStack item = new ItemStack(Material.FILLED_MAP);
                                                MapMeta meta = (MapMeta) item.getItemMeta();
                                                meta.setMapView(Bukkit.getServer().getMap(103));
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
                        case "team":
                            if (!(sender instanceof Player)) {
                                sender.sendMessage("You cannot be in a team, you're the console!");
                                return true;
                            }

                            Player player = (Player) sender;

                            if (!Utils.playerIsInAGame(player, main.games)) {
                               return true;
                            }

                            if (args.length < 2) {
                                sender.sendMessage("§cJoin or leave lmao idk");
                                return true;
                            }

                            switch (args[1]) {
                                case "join":
                                    if (main.teams.playerIsInATeam(player)) {
                                        sender.sendMessage("§cYou are already in a team!");
                                        return true;
                                    }

                                    if (args.length < 3) {
                                        sender.sendMessage("§cWhose team?");
                                        return true;
                                    }

                                    Player targetPlayer = Bukkit.getServer().getPlayer(args[2]);

                                    if (targetPlayer == player) {
                                        sender.sendMessage("§cYou can't join your own team!");
                                        return true;
                                    }

                                    ArrayList<Player> team = main.teams.getPlayersTeam(targetPlayer);

                                    if (team != null) {
                                        team.add(player);
                                        sender.sendMessage("§asuccess");
                                    }
                                    else if (Utils.arrayContains(player, main.teams.getPlayersLookingForTeam())) {
                                        main.teams.createTeam(player, targetPlayer);
                                        sender.sendMessage("§asuccess");
                                    }
                                    else {
                                        sender.sendMessage("§cThat player does not have a team!");
                                    }
                                    break;
                                case "leave":
                                    if (!main.teams.playerIsInATeam(player)) {
                                        sender.sendMessage("§cYou are not in a team!");
                                        return true;
                                    } //sometimes

                                    main.teams.removePlayerFromTeams(player);
                                    break;
                                default:
                                sender.sendMessage("§cSorry, an error has occured. Please report this to a developer (Commands.java Error: Case not matched)");
                            }
                            return true;
                        default: // everything else
                            sender.sendMessage("§cInvalid arguments provided. Valid subcommands are: list, join");
                    }
                }
                return true;
            case "spawn":
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                   /* if (player.getWorld().getName() == "empty") {
                        sender.sendMessage("§cYou are already in the lobby!");
                        return true;
                    } */
                    sender.sendMessage("§aReturning to lobby...");
                    try {
                        Utils.getPlayersGame(player, main.games).playerLeave(player);
                        sender.sendMessage("§cLeaving Game");
                    }
                    catch (NotFoundException err) {
                        /*sender.sendMessage("§cYou are not in a game!");
                        return true;*/
                    }
                    Location world = main.getServer().getWorld("empty").getSpawnLocation();
                    player.teleport(world);
                }
                else {
                    sender.sendMessage("Bruh I can't teleport the console anywhere...");
                }
                return true;
/*            case "confirm":
                if (sender instanceof Player) 
           // If the command isn't defined */
            case "credits":
                sender.sendMessage("§a§lCredits:");
                sender.sendMessage("     §bIsland Shape: Lentebrije");
                sender.sendMessage("§a§lBuilders:");
                sender.sendMessage("     §bCloudy_TkT §2| §eTkT_Ohyeadude21#0179");
                sender.sendMessage("     §bNorwegianNeko §2| §eEcho🇳🇴#7716");
                sender.sendMessage("     §bSAVAGEBOY989235");
                sender.sendMessage("     §bSwagmannene §2| §eMichal#4492");
                sender.sendMessage("     §bLutappiBoon");
                sender.sendMessage("     §bwestie404 §2| §ewestie404#0404");
                sender.sendMessage("     §bSyxaer §2| §eSyxaer#1072");
                sender.sendMessage("§a§lCouncil:");
                sender.sendMessage("     §6Dev §2| §bJusteyCrustey §2| §eJusteyCrustey#1293");
                sender.sendMessage("     §6Dev §2| §bSbeve §2| §bhow_are_you §2| §esbeve#4701");
                sender.sendMessage("     §bAlex §2| §bLeverClient §2| §eLeverClient#1634");
                sender.sendMessage("     §bJay §2| §bColdStorm905 §2| §eColdStorm905#0101");
                sender.sendMessage("§bThanks so much to our §e§lAMAZING§b staff team.");
                return true;
            default:
                return false;     
        }
    }
}