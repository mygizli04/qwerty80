package me.qwerty80.commands.Credits;

import org.bukkit.command.CommandSender;

import me.qwerty80.commands.EscapeCommandWithConsoleSupport;

public class CreditsMain extends EscapeCommandWithConsoleSupport {
    public CreditsMain() {
        super();
        super.supportedCommands = new String[]{"credits"};
        super.singleMethod = true;
    }

    @Override
    public void execute (String command, String[] args, CommandSender sender) {
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
    }
}
