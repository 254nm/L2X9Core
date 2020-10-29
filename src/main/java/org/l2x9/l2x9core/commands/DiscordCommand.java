package org.l2x9.l2x9core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.l2x9.l2x9core.Main;
import org.l2x9.l2x9core.util.Utils;

public class DiscordCommand implements CommandExecutor {
    Main plugin;

    public DiscordCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Utils.sendMessage(sender, plugin.getConfig().getString("Discord"));
        return true;
    }
}
