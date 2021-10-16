package me.qwerty80.commands.Escape.Team;

import org.bukkit.entity.Player;

import me.qwerty80.commands.EscapeCommand;
import me.qwerty80.commands.EscapeCommandArgumentCheckResult;

public class EscapeTeamLeave extends EscapeCommand {
    public EscapeTeamLeave() {
        super();
        super.usage = "/escape team leave";
    }

    @Override
    public EscapeCommandArgumentCheckResult checkArguments(String command, String[] args, Player player) {
        EscapeCommandArgumentCheckResult check = new EscapeCommandArgumentCheckResult();

        if (!main.teams.playerIsInATeam(player)) {
            check.result = false;
            check.reason = "§cYou are not in a team!";
            return check;
        }

        check.result = false;
        return check;
    }

    @Override
    public void execute(String command, String[] args, Player player) {
        main.teams.removePlayerFromTeams(player);
        player.sendMessage("§aSuccess!");
    }
}
