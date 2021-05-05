package com.backtobedrock.LitePlaytimeRewards.domain.enumerations;

import com.backtobedrock.LitePlaytimeRewards.LitePlaytimeRewards;
import org.bukkit.plugin.java.JavaPlugin;

public enum Permission {
    AFKTIME,
    AFKTIME_OTHER;

    public String getPermissionString() {
        return (JavaPlugin.getPlugin(LitePlaytimeRewards.class).getDescription().getName().toLowerCase() + "." + this.name().replaceAll("_", ".").toLowerCase());
    }
}
