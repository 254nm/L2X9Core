package org.l2x9.l2x9core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.l2x9.l2x9core.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class SpawnCommand implements TabExecutor {
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
		if (commandSender instanceof Player) {
			if (commandSender.hasPermission("l2x9core.spawnshit")) {
				if (getEntityTypes().contains(args[1])) {
					int amount = Integer.parseInt(args[0]);
					Player player = (Player) commandSender;
					for (int i = 0; i < amount; i++) {
						player.getWorld().spawnEntity(player.getLocation(), EntityType.valueOf(args[1]));
					}
				}
			} else {
				Utils.sendMessage(commandSender, "&cNo permission");
			}
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
		return getEntityTypes();
	}

	public List<String> getEntityTypes() {
		List<String> entityTypes = new ArrayList<>();
		for (EntityType entityType : EntityType.values()) {
			entityTypes.add(entityType.toString().toLowerCase());
		}
		return entityTypes;
	}
}
