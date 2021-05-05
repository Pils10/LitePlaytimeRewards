package com.backtobedrock.LitePlaytimeRewards.configurations;

import com.backtobedrock.LitePlaytimeRewards.LitePlaytimeRewards;
import com.backtobedrock.LitePlaytimeRewards.domain.enumerations.InventoryLayout;
import com.backtobedrock.LitePlaytimeRewards.domain.enumerations.RewardsOrder;
import com.backtobedrock.LitePlaytimeRewards.domain.ConfigReward;
import com.backtobedrock.LitePlaytimeRewards.utilities.ConfigUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {

    private final File configFile;
    private FileConfiguration config;
    private final LitePlaytimeRewards plugin;

    public Config(File configFile) {
        this.configFile = configFile;
        this.plugin = JavaPlugin.getPlugin(LitePlaytimeRewards.class);
        this.setConfig(YamlConfiguration.loadConfiguration(configFile));
    }

    public void setConfig(FileConfiguration fc) {
        this.config = fc;
    }

    // <editor-fold desc="Miscellaneous" defaultstate="collapsed">
    public boolean isUpdateChecker() {
        return this.config.getBoolean("UpdateChecker", true);
    }
    // </editor-fold>

    // <editor-fold desc="Guis" defaultstate="collapsed">
    public Material getBorderMaterial() {
        return ConfigUtils.getMaterial("BorderMaterial", this.config.getString("BorderMaterial", "white_stained_glass_pane"), this.plugin.isLegacy() ? Material.matchMaterial("thin_glass") : Material.WHITE_STAINED_GLASS_PANE);
    }

    public InventoryLayout getInventoryLayout() {
        return ConfigUtils.getInventoryLayout("InventoryLayout", this.config.getString("InventoryLayout", "centered"), InventoryLayout.CENTERED);
    }

    public RewardsOrder getRewardsOrder() {
        return ConfigUtils.getRewardsOrder("RewardsOrder", this.config.getString("RewardsOrder", "id"), RewardsOrder.ID);
    }
    // </editor-fold>

    public TreeMap<String, ConfigReward> getRewards() {
        TreeMap<String, ConfigReward> rewards = new TreeMap<>();
        ConfigurationSection rewardsSection = this.config.getConfigurationSection("Rewards");
        if (rewardsSection != null) {
            rewardsSection.getKeys(false).forEach(e -> {
                ConfigurationSection rewardConfig = rewardsSection.getConfigurationSection(e);
                ConfigReward reward = ConfigReward.deserialize(e, rewardConfig);
                if (reward != null) {
                    rewards.put(e.toLowerCase(), reward);
                }
            });
        }
        return rewards;
    }

    private void saveConfig() {
        try {
            this.config.save(this.configFile);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot save to {0}", this.configFile.getName());
        }
    }
}
