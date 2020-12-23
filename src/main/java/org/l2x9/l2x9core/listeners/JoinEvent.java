package org.l2x9.l2x9core.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.potion.PotionEffect;
import org.l2x9.l2x9core.Main;
import org.l2x9.l2x9core.util.Utils;

public class JoinEvent implements Listener {
	Main plugin;

	public JoinEvent(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		try {
			Player player = e.getPlayer();
			if (player.getActivePotionEffects() != null) {
				for (PotionEffect effects : player.getActivePotionEffects()) {
					if (effects.getAmplifier() > 5) {
						player.removePotionEffect(effects.getType());
					}
				}

			}
			if (plugin.getConfigBoolean("FirstJoin.Enabled")) {
				if (!player.hasPlayedBefore()) {
					Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&',
							plugin.getConfig().getString("FirstJoin.Message").replace("{Player}", player.getName())));
				}
			}
			if (plugin.discordWebhook.alertsEnabled() && player.isOp()) {
				if (plugin.getConfigBoolean("AlertSystem.OppedPlayerJoin")) {
					plugin.discordAlertQueue.add(plugin.getPingRole() + " [OppedPlayerJoin] Player with op by the name of " + player.getName() + " Joined the server");
				}
			}
		} catch (Error | Exception throwable) {
			Utils.reportException(throwable);
		}
	}

	@EventHandler
	public void onKick(PlayerKickEvent event) {
		if (event.getReason().equalsIgnoreCase("Kicked for spamming")) {
			event.setCancelled(true);
		}
	}
}
