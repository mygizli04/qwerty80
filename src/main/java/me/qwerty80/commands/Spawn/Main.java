package me.qwerty80.commands.Spawn;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.qwerty80.Qwerty80;
import me.qwerty80.Utils;
import me.qwerty80.commands.EscapeCommand;

public class Main extends EscapeCommand {
    public final String usage = "/spawn";

    private final String[] supportedCommands = new String[]{"spawn"};

    public String[] getSupportedCommands() {
        return this.supportedCommands;
    }

    public boolean checkArguments(String command, String[] args) {
        return true;
    }

    public void execute(String command, String[] args, Player player) {
        if (player.getWorld().getName().equals("empty")) {
            player.sendMessage("§cYou are already in the lobby!");
            return;
        }

        if (!Utils.playerIsInAGame(player, Utils.main.games)) {
            player.sendMessage("§cYou are not in a game!");
            return;
        }

        Utils.getPlayersGame(player, Utils.main.games).playerLeave(player);
        player.sendMessage("§aReturning to lobby...");

        Location world = Utils.main.getServer().getWorld("empty").getSpawnLocation();
        player.teleport(world);
    }
}
