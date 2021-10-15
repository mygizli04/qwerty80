package me.qwerty80.commands.Escape.Team;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.qwerty80.Utils;
import me.qwerty80.commands.EscapeCommand;
import me.qwerty80.commands.EscapeCommandArgumentCheckResult;

public class EscapeTeamJoin extends EscapeCommand {

    public EscapeTeamJoin() {
        super();
        super.usage = "/escape team join <player>";
    }

    @Override
    public EscapeCommandArgumentCheckResult checkArguments(String command, String[] args, Player player) {
        EscapeCommandArgumentCheckResult check = new EscapeCommandArgumentCheckResult();

        if (Utils.main.teams.playerIsInATeam(player)) {
            check.result = false;
            check.reason = "§cYou are already in a team!";
            return check;
        }

        if (args.length < 3) { // /escape team join
            check.result = false;
            return check;
        }

        if (args[2].equals(player.getName())) {
            check.result = false;
            check.reason = "§cYou cannot join your own team!";
            return check;
        }

        Player target = Bukkit.getServer().getPlayer(args[2]);

        if (target == null) {
            check.result = false;
            check.reason = "§cThat player is not online!";
            return check;
        }

        ArrayList<Player> team = main.teams.getPlayersTeam(target);

        // TODO: Add distance check here

        // TODO: Change this system so players can accept/deny join requests
        if (team != null || Utils.arrayContains(target, main.teams.getPlayersLookingForTeam())) { // The target player already has a team | the target is looking for a team too
            return check;
        }
        else {
            check.result = false;
            check.reason = "§cThat player is not looking for a team!";
            return check;
        }

        // no default case, anything after above is dead code
    }

    @Override
    public void execute(String command, String[] args, Player player) {
        Player target = Bukkit.getServer().getPlayer(args[2]);
        
        if (Utils.arrayContains(target, main.teams.getPlayersLookingForTeam())) { // If the player is looking for a team...
            main.teams.createTeam(player, target);
        }
        else { // or if they already have a team (only possible option after checkArguments)
            main.teams.getPlayersTeam(target).add(player);
        }
        
        player.sendMessage("§aSuccess!");
    }
}
