package org.l2x9.l2x9core.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.l2x9.l2x9core.Main;
import org.l2x9.l2x9core.util.Cooldown;
import org.l2x9.l2x9core.util.Utils;

import java.util.List;

public class PlayerChat implements Listener {
	Cooldown cm = new Cooldown();
	Main plugin;

	public PlayerChat(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		try {
			if (plugin.getConfigBoolean("Chat.Enabled")) {
				Player player = event.getPlayer();
				if (!player.isOp()) {
					if (cm.checkCooldown(player)) {
						cm.setCooldown(player, plugin.getConfig().getInt("Chat.Cooldown"));
					} else {
						event.setCancelled(true);
					}
				}
				if (!player.isOp()) {
					if (plugin.getConfig().getStringList("Chat.Blocked-words") != null) {
						List<String> list = plugin.getConfig().getStringList("Chat.Blocked-words");
						boolean hasBlackListedWord = false;
						for (String word : list) {
							if (event.getMessage().toLowerCase().contains(word)) {
								hasBlackListedWord = true;
								break;
							}
						}
						if (hasBlackListedWord) {
							Utils.println(Utils.getPrefix() + "&6Prevented &r&c" + player.getName() + " &r&6from advertising");
							event.setCancelled(true);
							if (!event.getMessage().startsWith(">")) {
								if (!event.getMessage().startsWith("#")) {
									Utils.sendMessage(event.getPlayer(), "<" + player.getName() + "> " + event.getMessage());
								} else {
									Utils.sendMessage(event.getPlayer(), "<" + player.getName() + "> " + "&e" + event.getMessage());
								}
							} else {
								Utils.sendMessage(event.getPlayer(), "<" + player.getName() + "> " + "&a" + event.getMessage());
							}
						}
					}
				}
			}
		} catch (Error | Exception throwable) {
			Utils.reportException(throwable);

		}
	}
}