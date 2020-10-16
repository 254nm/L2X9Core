package org.l2x9.l2x9core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.l2x9.l2x9core.L2X9Core;
import org.l2x9.l2x9core.util.Utils;

public class UptimeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Utils.sendMessage(sender, "&6The server has had &r&c" + Utils.getFormattedInterval(System.currentTimeMillis() - L2X9Core.startTime) + "&r&6 uptime");
        return true;
    }

}
