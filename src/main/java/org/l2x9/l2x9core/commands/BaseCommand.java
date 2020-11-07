package org.l2x9.l2x9core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.l2x9.l2x9core.Main;
import org.l2x9.l2x9core.util.Utils;

import java.util.Arrays;
import java.util.List;

public class BaseCommand implements TabExecutor {
    Main plugin;

    public BaseCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("l2x9core.base")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    plugin.reloadConfig();
                    Utils.sendMessage(sender, Utils.getPrefix() + "&aReloaded configuration file");
                } else {
                    if (args[0].equalsIgnoreCase("version")) {
                        Utils.sendMessage(sender, Utils.getPrefix() + "&6Version &r&c" + plugin.getDescription().getVersion());

                    } else {
                        if (args[0].equalsIgnoreCase("help")) {
                            Utils.sendMessage(sender, Utils.getPrefix() + "&1---&r " + Utils.getPrefix() + "&6Help &r&1---");
                            Utils.sendMessage(sender, Utils.getPrefix() + "&6 /aef reload |&r&e Reloads the config");
                            Utils.sendMessage(sender, Utils.getPrefix() + "&6 /aef version |&r&e Shows the version of the plugin");
                            Utils.sendMessage(sender, Utils.getPrefix() + "&6 /aef help |&r&e Shows help for the plugin");
                        }
                    }
                }
            } else {
                Utils.sendMessage(sender, Utils.getPrefix() + "&6Please do&r&c /aef help&r&6 to get help");
            }

        } else {
            Utils.sendMessage(sender, Utils.getPrefix() + "&6This server is using&r " + Utils.getPrefix() + "&6Version&r&c " + plugin.getDescription().getVersion() + "&r&6 by&r&c 254n_m");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length > 0) {
            return Arrays.asList("reload", "version", "help");

        }
        return null;
    }

}
