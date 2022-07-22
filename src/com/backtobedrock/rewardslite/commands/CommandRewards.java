package com.backtobedrock.rewardslite.commands;

import com.backtobedrock.rewardslite.interfaces.InterfaceRewards;
import com.backtobedrock.rewardslite.utilities.PlayerUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandRewards extends AbstractCommand {
    public CommandRewards(CommandSender cs, Command command, String[] args) {
        super(cs, command, args);
    }

    @Override
    public void run() {
        if (this.args.length == 0) {
            if (!this.hasPermission()) {
                return;
            }

            if (!this.isPlayer()) {
                return;
            }

            this.openRewardsGUI(this.sender);
        } else if (this.args.length == 1) {
            if (!this.cs.hasPermission(String.format("%s.rewards.other", this.plugin.getName().toLowerCase()))) {
                return;
            }

            if (!this.isPlayer()) {
                return;
            }

            if (!this.hasPlayedBefore(this.args[0])) {
                return;
            }

            this.openRewardsGUI(this.target);
        }
    }

    private void openRewardsGUI(OfflinePlayer commandTarget) {
        this.plugin.getPlayerRepository().getByPlayer(commandTarget)
                .thenAcceptAsync(playerData -> PlayerUtils.openInventory(this.sender, new InterfaceRewards(this.sender, playerData)))
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    return null;
                });
    }
}
