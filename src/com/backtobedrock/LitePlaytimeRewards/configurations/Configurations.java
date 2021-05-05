package com.backtobedrock.LitePlaytimeRewards.configurations;

import com.backtobedrock.LitePlaytimeRewards.LitePlaytimeRewards;
import com.backtobedrock.LitePlaytimeRewards.configurations.ConfigurationSections.ConfigurationData;
import com.backtobedrock.LitePlaytimeRewards.configurations.ConfigurationSections.ConfigurationGuis;
import com.backtobedrock.LitePlaytimeRewards.configurations.ConfigurationSections.ConfigurationMiscellaneous;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Configurations {
    private final LitePlaytimeRewards plugin;
    private final FileConfiguration config;

    //configurations
    private ConfigurationData dataConfiguration;
    private ConfigurationMiscellaneous miscellaneousConfiguration;
    private ConfigurationGuis guisConfiguration;

    public Configurations(File configFile) {
        this.plugin = JavaPlugin.getPlugin(LitePlaytimeRewards.class);
        this.config = YamlConfiguration.loadConfiguration(configFile);
        this.getDataConfiguration();
        this.getMiscellaneousConfiguration();
        this.getGuisConfiguration();
    }

    public ConfigurationData getDataConfiguration() {
        if (this.dataConfiguration == null) {
            this.dataConfiguration = ConfigurationData.deserialize(this.config);
            if (this.dataConfiguration == null) {
                this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
            }
        }
        return this.dataConfiguration;
    }

    public ConfigurationMiscellaneous getMiscellaneousConfiguration() {
        if (this.miscellaneousConfiguration == null) {
            this.miscellaneousConfiguration = ConfigurationMiscellaneous.deserialize(this.config);
        }
        return this.miscellaneousConfiguration;
    }

    public ConfigurationGuis getGuisConfiguration() {
        if (this.guisConfiguration == null) {
            this.guisConfiguration = ConfigurationGuis.deserialize(this.config);
            if (this.guisConfiguration == null) {
                this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
            }
        }
        return this.guisConfiguration;
    }
}
