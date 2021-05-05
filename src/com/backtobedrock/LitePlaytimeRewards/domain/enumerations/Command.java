package com.backtobedrock.LitePlaytimeRewards.domain.enumerations;

import java.util.Collections;
import java.util.List;

public enum Command {
    AFKTIME(Permission.AFKTIME, null, Collections.emptyList(), Collections.singletonList("player"), "Check your current AFK time or that of another player.");
//    GIVEREWARD("/givereward §6<reward> <player> §o[amount] [broadcast]", "Force give one of the reward to a player."),
//    LPR("/lpr §6§o[help §8|§6§o reload §8|§6§o reset]", "LPR commands."),
//    LPR_HELP("/lpr help", "List of LitePlaytimeRewards commands."),
//    LPR_RELOAD("/lpr reload", "Reload the config and messages files."),
//    LPR_RESET("/lpr reset §6<reward> <player>", "Resets the reward time for the given reward for the given player."),
//    PLAYTIME("/playtime §6§o[player]", "Check your current playtime or that of another player."),
//    REWARDS("/rewards §6§o[reward]", "Check info on all your available rewards or a single one.");

    private final Permission permission;
    private final Command parent;
    private final List<String> requiredParameters;
    private final List<String> optionalParameters;
    private final String description;

    Command(Permission permission, Command parent, List<String> requiredParameters, List<String> optionalParameters, String description) {
        this.permission = permission;
        this.parent = parent;
        this.requiredParameters = requiredParameters;
        this.optionalParameters = optionalParameters;
        this.description = description;
    }

    public Permission getPermission() {
        return permission;
    }

    public int getMinimumArguments() {
        return this.parent == null ? this.requiredParameters.size() : this.requiredParameters.size() + 1;
    }

    public int getMaximumArguments() {
        return this.parent == null ? (this.requiredParameters.size() + this.optionalParameters.size()) : (this.requiredParameters.size() + this.optionalParameters.size()) + 1;
    }

    public String getFancyVersion() {
        StringBuilder builder = new StringBuilder("§b/").append(this.parent == null ? this.name().toLowerCase() : this.parent.name().toLowerCase() + " " + this.name().toLowerCase());
        if (!this.requiredParameters.isEmpty()) {
            builder.append("§3");
            this.requiredParameters.forEach(e -> builder.append(" ").append("[").append(e).append("]"));
        }
        if (!this.optionalParameters.isEmpty()) {
            builder.append("§9");
            this.optionalParameters.forEach(e -> builder.append(" ").append("(").append(e).append(")"));
        }
        builder.append(" §8§l- §7").append(this.description).append(".");
        return builder.toString();
    }
}
