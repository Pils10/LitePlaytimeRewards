package com.backtobedrock.LitePlaytimeRewards.commands;

import com.backtobedrock.LitePlaytimeRewards.LitePlaytimeRewards;
import com.backtobedrock.LitePlaytimeRewards.domain.enumerations.Command;
import com.backtobedrock.LitePlaytimeRewards.domain.enumerations.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.CompletableFuture;

public abstract class AbstractCommand {

    protected final LitePlaytimeRewards plugin;
    protected final CommandSender cs;
    protected final String[] args;
    protected Player sender = null;
    protected OfflinePlayer target = null;

    public AbstractCommand(CommandSender cs, String[] args) {
        this.plugin = JavaPlugin.getPlugin(LitePlaytimeRewards.class);
        this.cs = cs;
        this.args = args;
    }

    public abstract void run();

    protected boolean hasPermission(Command command) {
        return this.hasPermission(command.getPermission());
    }

    protected boolean hasPermission(Permission permission) {
        if (!this.cs.hasPermission(permission.getPermissionString())) {
            this.cs.sendMessage("§cYou have no permission to use this command.");
            return false;
        }
        return true;
    }

    protected boolean isPlayer() {
        if (!(this.cs instanceof Player)) {
            this.cs.sendMessage("§cYou will need to log in to use this command.");
            return false;
        }
        this.sender = (Player) cs;
        return true;
    }

    protected boolean hasCorrectAmountOfArguments(Command command) {
        if (this.args.length < command.getMinimumArguments() || this.args.length > command.getMaximumArguments()) {
            this.sendUsageMessage(command);
            return false;
        }
        return true;
    }

    protected CompletableFuture<Boolean> hasPlayedBefore(String playerName) {
        return CompletableFuture.supplyAsync(() -> {
            @SuppressWarnings("deprecation") OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
            if (!player.hasPlayedBefore()) {
                this.cs.sendMessage(String.format("§c%s has not played on the server before.", player.getName()));
                return false;
            }
            this.target = player;
            return true;
        });
    }

    public void sendUsageMessage(Command command) {
        this.cs.sendMessage(new String[]{"§8§m--------------§6 Command §fUsage §8§m--------------", command.getFancyVersion(), "§8§m------------------------------------------"});
    }
}
