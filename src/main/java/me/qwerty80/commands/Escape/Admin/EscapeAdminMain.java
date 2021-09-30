package me.qwerty80.commands.Escape.Admin;

import org.bukkit.command.CommandSender;

import me.qwerty80.Utils;
import me.qwerty80.commands.EscapeCommandArgumentCheckResult;
import me.qwerty80.commands.EscapeCommandWithConsoleSupport;

public class EscapeAdminMain extends EscapeCommandWithConsoleSupport {
    public EscapeAdminMain() {
        super();
        super.usage = "/escape admin";
    }

    @Override
    public EscapeCommandArgumentCheckResult checkArguments(String command, String[] args, CommandSender sender) {
        EscapeCommandArgumentCheckResult check = new EscapeCommandArgumentCheckResult();

        if (args.length == 1) { // /escape admin (No arguments, show usage)
            check.result = false;
            return check;
        }

        if (args[1].equals("startgame")) { // /escape admin startgame
            return check;
        }

        if (args[1].equals("stopgame")) { // /escape admin stopgame [number]
            int destination = 0;
            if (args.length > 3) {
                if (!Utils.isNumber(args[2])) {
                    check.result = false;
                    check.reason = "§cThat is not a valid number.";
                    return check;
                }

                destination = Integer.parseInt(args[2]);

                if (Utils.main.games.get(destination) == null) {
                    check.result = false;
                    check.reason = "§cThat game does not exist!";
                    return check;
                }

                if (Utils.canTeleportToGame(destination)) {
                    
                }

            }

        }

        check.result = false;
        return check;
    }
}
