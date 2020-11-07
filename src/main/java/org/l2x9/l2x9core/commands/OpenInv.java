package org.l2x9.l2x9core.commands;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.l2x9.l2x9core.util.Utils;

public class OpenInv implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender p, Command cmd, String label, String[] args) {
		if (p instanceof Player) {
			Player sender = (Player) p;
			if (sender.hasPermission("l2x9core.inv")) {
				if (args.length < 2) {
					Utils.sendMessage(sender, "&4Error:&r&c <ender|inventory> <player>");
				} else {
					if (args.length > 0) {
						if (args[0].equalsIgnoreCase("ender")) {
							if (Bukkit.getOnlinePlayers().contains(sender)) {
								Player target = Bukkit.getPlayer(args[1]);
								sender.openInventory(target.getEnderChest());
							}
						} else {
							if (args[0].equalsIgnoreCase("inv")) {
								if (Bukkit.getOnlinePlayers().contains(sender)) {
									Player target = Bukkit.getPlayer(args[1]);
									sender.openInventory(target.getInventory());
								}
							}
						}
					}
				}

			} else {
				p.sendMessage(ChatColor.RED + "No permission");
			}
		} else {
			p.sendMessage(ChatColor.RED + "You must be a player to use this command");
		}
		return true;
	}

}
