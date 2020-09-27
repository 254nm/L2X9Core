package org.l2x9.l2x9core.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.l2x9.l2x9core.Main;
import org.l2x9.l2x9core.util.Utils;

public class SayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("SayCommand.say") && cmd.getName().equalsIgnoreCase("say")) {
            if (args.length > 0) {
                String configMessage = Main.getPlugin().getConfig().getString("SayCommandFormat");
                StringBuilder builder = new StringBuilder();
                for (String arg : args) {
                    builder.append(arg.concat(" "));
                }
                Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', configMessage.replace("{message}", builder.toString())));
            } else {
                Utils.sendMessage(sender, "&4Error:&r&c Message cannot be blank");
            }
        } else {
            Utils.sendMessage(sender, "&cNo permission");
        }
        return true;
    }
}