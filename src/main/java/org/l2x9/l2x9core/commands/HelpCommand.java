package org.l2x9.l2x9core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.l2x9.l2x9core.L2X9Core;
import org.l2x9.l2x9core.util.Utils;

import java.util.List;

public class HelpCommand implements CommandExecutor {
    L2X9Core plugin;

    public HelpCommand(L2X9Core plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = plugin.getConfig().getStringList("help");
        String join = String.join("\n", list);
        Utils.sendMessage(sender, join);
        return true;
    }
}
