package com.backtobedrock.LitePlaytimeRewards.configurations.ConfigurationSections;

import com.backtobedrock.LitePlaytimeRewards.utilities.ConfigUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ConfigurationData {
    private final boolean CountAllPlaytime;
    private List
    public int getAutoSave() {
        return ConfigUtils.checkMin(this.config.getInt("AutoSave", 1), 1, 1) * 1200;
    }

    public int getTimeKeepDataInCache() {
        return ConfigUtils.checkMin(this.config.getInt("TimeKeepDataInCache", 5), 0, 5) * 1200;
    }

    public boolean isCountAllPlaytime() {
        return !this.plugin.isLegacy() && this.config.getBoolean("CountAllPlaytime", true);
    }

    public List<String> getDisableGettingRewardsInWorlds() {
        return this.config.getStringList("DisableGettingRewardsInWorlds").stream().map(String::toLowerCase).collect(Collectors.toList());
    }
}
