package org.l2x9.l2x9core.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.l2x9.l2x9core.util.Utils;

public class UUidCommand implements CommandExecutor {

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("l2x9core.uuid")) {
            if (args.length > 0) {
                Player player = Bukkit.getPlayer(args[0]);
                if (Bukkit.getOnlinePlayers().contains(player)) {
                    Player target = Bukkit.getPlayer(args[0]);
                    Utils.sendMessage(sender, "&6The uuid of the player&r&c " + target.getName() + "&r&6 is &r&c " + target.getUniqueId().toString());
                    if (sender instanceof Player) {
                        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6The uuid of the player&r&c " + target.getName() + "&r&6 is &r&c " + target.getUniqueId().toString()));
                    }
                } else {
                    OfflinePlayer olTarget = Bukkit.getOfflinePlayer(args[0]);
                    Utils.sendMessage(sender, "&6The uuid of the player&r&c " + olTarget.getName() + "&r&6 is&r&c " + olTarget.getUniqueId().toString());
                    if (sender instanceof Player) {
                        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6The uuid of the player&r&c " + olTarget.getName() + "&r&6 is &r&c " + olTarget.getUniqueId().toString()));
                    }
                }

            } else {
                Utils.sendMessage(sender, "&4Error:&r&c Please include an argument /uuid <player>");
            }
        } else {
            Utils.sendMessage(sender, "&cNo permission");
        }

        return true;
    }

}
