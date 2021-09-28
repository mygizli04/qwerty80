package me.qwerty80.commands;

public class EscapeCommandArgumentCheckResult {
    /**
     * Whether or not the command can be executed correctly
     */
    public boolean result = true;

    /**
     * The message to be displayed to the player if {@link #result} is false. {@code null} for default response (Usage: {@link EscapeCommand#usage})
     */
    public String reason = null;
}
