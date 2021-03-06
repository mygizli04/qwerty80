package me.qwerty80.commands.Escape.Admin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

import me.qwerty80.Game;
import me.qwerty80.Utils;
import me.qwerty80.commands.EscapeCommandArgumentCheckResult;
import me.qwerty80.commands.EscapeCommandWithConsoleSupport;
import me.qwerty80.commands.Escape.EscapeMain;
import net.kyori.adventure.text.Component;

public class EscapeAdminMain extends EscapeCommandWithConsoleSupport {
    public EscapeAdminMain() {
        super();
        super.usage = "/escape admin";
    }

    @Override
    public EscapeCommandArgumentCheckResult checkArguments(String command, String[] args, CommandSender sender) {
        EscapeCommandArgumentCheckResult check = new EscapeCommandArgumentCheckResult();

        if (!Utils.checkPermission(sender, "escape.admin")) { // If they don't have permission to use admin commands act like they don't exist
            check.result = false;
            super.usage = new EscapeMain().usage;
            return check;
        }

        boolean startGamePermission = Utils.checkPermission(sender, "escape.admin.startgame");
        boolean stopGamePermission = Utils.checkPermission(sender, "escape.admin.stopgame");
        boolean getMapPermission = Utils.checkPermission(sender, "escape.admin.getmap");

        int permissionCount = 0;
        permissionCount += startGamePermission ? 1 : 0;
        permissionCount += stopGamePermission ? 1 : 0;
        permissionCount += getMapPermission ? 1 : 0;

        if (permissionCount == 0) {
            check.result = false;
            check.reason = "§cYou have permission to use /escape admin, but don't have permission to use any subcommands? Ask an admin about this.";
            return check;
        } else if (permissionCount == 1) {
            super.usage = "/escape admin [";
            super.usage += startGamePermission ? "startgame" : "";
            super.usage += stopGamePermission ? "stopgame" : "";
            super.usage += getMapPermission ? "getmap" : "";
            super.usage += "]";
        } else {
            super.usage = "/escape admin [";
            super.usage += startGamePermission ? "startgame|" : "";
            super.usage += stopGamePermission ? "stopgame|" : "";
            super.usage += getMapPermission ? "getmap|" : "";
            super.usage = super.usage.substring(0, super.usage.length() - 1) + "]";
        }

        if (args.length == 1) { // /escape admin (No arguments, show usage)
            check.result = false;
            return check;
        }

        // Subcommands are hidden if the player does not have permission to use them

        if (args[1].equals("startgame") && startGamePermission) { // /escape admin startgame
            return check;
        }

        if (args[1].equals("stopgame") && stopGamePermission) { // /escape admin stopgame [number]

            if (Utils.main.games.size() == 0) {
                check.result = false;
                check.reason = "§cThere are no games to stop!";
                return check;
            }

            int destination = 0;
            if (args.length > 3) {
                if (!Utils.isNumber(args[2])) {
                    check.result = false;
                    check.reason = "§cThat is not a valid number.";
                    return check;
                }

                destination = Integer.parseInt(args[2]);

                if (!Utils.canTeleportToGame(destination)) {
                    check.result = false;
                    check.reason = "§cThat game does not exist!";
                    return check;
                }
            } else {
                return check;
            }
        }

        if (args[1].equals("getmap") && getMapPermission) { // /escape admin getmap [player]
            if (!(sender instanceof Player)) {
                if (!(args.length > 2)) {
                    if (Bukkit.getServer().getPlayer(args[2]) != null) {
                        return check;
                    }
                    else {
                        check.result = false;
                        check.reason = "§cThat player is not online!";
                        return check;
                    }
                }
                else {
                    check.result = false;
                    check.reason = "Usage: /escape admin getmap <player>";
                    return check;
                }
            }
            return check;
        }

        check.result = false;
        return check;
    }

    void startGame() {
        Bukkit.broadcast(Component.text("§eWarning: A new game is being generated. Please ignore the lag. We're sorry for the inconvenience"));
        main.games.add(new Game(main.games.size(), main));
        return;
    }

    void stopGame(int game) {
        Bukkit.broadcast(Component.text("§eWarning: A game instance is being removed. Please ignore the lag. We're sorry for the inconvenience"));
        main.games.get(game != -1 ? game - 1 : 0).delete();
        main.games.remove(game != -1 ? game - 1 : 0);
    }

    @Override
    public void execute(String command, String[] args, Player player) {
        if (args[1].equals("startgame")) {
            startGame();
        }

        if (args[1].equals("stopgame")) {
            stopGame(args.length < 3 ? -1 : Integer.parseInt(args[2]));
        }

        if (args[1].equals("getmap")) {
            player.sendMessage("§aHere's the map of the island for island_water");
            ItemStack item = new ItemStack(Material.FILLED_MAP);
            MapMeta meta = (MapMeta) item.getItemMeta();
            meta.setMapView(Bukkit.getServer().getMap(103)); // Deprecated, but it appears I cannot find an alternative that is not deprecated.
            item.setItemMeta(meta);
            player.getInventory().addItem(item);
        }
    }

    @Override
    public void execute(String command, String[] args, CommandSender sender) {
        if (args[1].equals("startgame")) { // /escape admin startgame
            startGame();
        }

        if (args[1].equals("stopgame")) { // /escape admin stopgame
            stopGame(args.length < 3 ? -1 : Integer.parseInt(args[2]));
        }

        if (args[1].equals("getmap")) { // /escape admin getmap [player]
            Player player = Bukkit.getServer().getPlayer(args[2]);
            sender.sendMessage("§aSending the map of island_water to " + args[2]);
            sender.sendMessage("§aYou have been given a map of island_water by CONSOLE");
            ItemStack item = new ItemStack(Material.FILLED_MAP);
            MapMeta meta = (MapMeta) item.getItemMeta();
            meta.setMapView(Bukkit.getServer().getMap(103)); // Deprecated, but it appears I cannot find an alternative that is not deprecated.
            item.setItemMeta(meta);
            player.getInventory().addItem(item);
        }
    }
}
