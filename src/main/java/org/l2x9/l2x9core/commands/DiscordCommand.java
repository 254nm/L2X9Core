package org.l2x9.l2x9core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.l2x9.l2x9core.L2X9Core;
import org.l2x9.l2x9core.util.Utils;

public class DiscordCommand implements CommandExecutor {
    L2X9Core plugin;

    public DiscordCommand(L2X9Core plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Utils.sendMessage(sender, plugin.getConfig().getString("Discord"));
        return true;
    }

}
