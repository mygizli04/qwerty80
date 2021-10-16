package me.qwerty80.commands.Escape.Team;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.qwerty80.Utils;
import me.qwerty80.commands.EscapeCommand;
import me.qwerty80.commands.EscapeCommandArgumentCheckResult;

public class EscapeTeamMain extends EscapeCommand {

    ArrayList<Player> playersOnTimeOut = new ArrayList<>();

    public EscapeTeamMain() {
        super();
        super.usage = "/escape team (join <player>|leave)";
    }

    @Override
    public EscapeCommandArgumentCheckResult checkArguments(String command, String[] args, Player player)  {
        EscapeCommandArgumentCheckResult check = new EscapeCommandArgumentCheckResult();

        if (playersOnTimeOut.contains(player)) {
            check.result = false;
            check.reason = "§cHey there cowboy! Slow down a bit...";
            return check;
        }

        if (args.length == 1) { // /escape team (Show usage, don't count against the spam timeout)
            check.result = false;
            return check;
        }

        // args.length > 1 due to check made above
        
        if (!Utils.playerIsInAGame(player)) { // If player is not in a game they're not supposed to join games, also doesn't count as spam timeout
            check.result = false;
            check.reason = "§cYou are not in a game!";
            return check;
        }

        playersOnTimeOut.add(player); // 20 second timeout
        Bukkit.getScheduler().scheduleSyncDelayedTask(Utils.main, () -> {
            playersOnTimeOut.remove(player);
        }, 400);

        if (args[1].equals("join")) {
            return new EscapeTeamJoin().checkArguments(command, args, player);
        }

        if (args[1].equals("leave")) {
            return new EscapeTeamLeave().checkArguments(command, args, player);
        }

        check.result = false;
        check.reason = "§cThe subcommand provided could not be found.";
        return check;
    }

    @Override
    public void execute(String command, String[] args, Player player) {
        if (args[1].equals("join")) {
            new EscapeTeamJoin().execute(command, args, player);
        }
        else {
            new EscapeTeamLeave().execute(command, args, player);
        }
    }
}
