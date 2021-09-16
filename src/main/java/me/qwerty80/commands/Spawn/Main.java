package me.qwerty80.commands.Spawn;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.qwerty80.Qwerty80;
import me.qwerty80.Utils;
import me.qwerty80.commands.EscapeCommand;
import me.qwerty80.Exceptions.NotFoundException;

public class Main extends EscapeCommand {

    public Main(Qwerty80 main) {
        super(main);
    }

    public final String usage = "/spawn";

    public final String[] supportedCommands = new String[]{"spawn"};

    public boolean checkArguments(String command, String[] args) {
        return true;
    }

    public void execute(String command, String[] args, Player player) {
        if (player.getWorld().getName() == "empty") {
            player.sendMessage("§cYou are already in the lobby!");
            return;
        }

        try {
            Utils.getPlayersGame(player, main.games).playerLeave(player);
            player.sendMessage("§aReturning to lobby...");
        }
        catch (NotFoundException err) {
            player.sendMessage("§cYou are not in a game!");
            return;
        }

        Location world = main.getServer().getWorld("empty").getSpawnLocation();
        player.teleport(world);
    }
}
