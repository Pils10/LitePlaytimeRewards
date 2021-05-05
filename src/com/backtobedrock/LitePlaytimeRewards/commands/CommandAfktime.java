package com.backtobedrock.LitePlaytimeRewards.commands;

import com.backtobedrock.LitePlaytimeRewards.domain.data.PlayerData;
import com.backtobedrock.LitePlaytimeRewards.domain.enumerations.Command;
import com.backtobedrock.LitePlaytimeRewards.domain.enumerations.Permission;
import org.bukkit.command.CommandSender;

public class CommandAfktime extends AbstractCommand {

    public CommandAfktime(CommandSender cs, String[] args) {
        super(cs, args);
    }

    @Override
    public void run() {
        Command command = Command.AFKTIME;

        if (this.args.length == 0 && !this.hasPermission(command)) {
            return;
        } else if (!this.hasPermission(Permission.AFKTIME_OTHER)) {
            return;
        }

        if (!this.isPlayer()) {
            return;
        }

        if (!this.hasCorrectAmountOfArguments(command)) {
            return;
        }

        if (!this.checkEssentials()) {
            return;
        }

        if (this.args.length == 0) {
            PlayerData crudafk = this.plugin.getPlayerCache().get(this.sender.getUniqueId());

            this.cs.sendMessage(this.plugin.getMessages().getAFKTime(crudafk.getAfktime()));
        } else {
            this.hasPlayedBefore(this.args[0]).thenAcceptAsync(bool -> {
                if (!bool) {
                    return;
                }

                PlayerData crudafkother = plyrafkother.isOnline()
                        ? this.plugin.getPlayerCache().get(plyrafkother.getUniqueId())
                        : new PlayerData(plyrafkother);

                cs.sendMessage(this.plugin.getMessages().getAFKTimeOther(crudafkother.getAfktime(), plyrafkother.getName()));
            }).exceptionally(ex -> {
                ex.printStackTrace();
                return null;
            });
        }
    }

    private boolean checkEssentials() {
        if (this.plugin.ess == null) {
            this.cs.sendMessage(this.plugin.getMessages().getServerDoesntKeepTrackOfAFK());
            return false;
        }
        return true;
    }

}
