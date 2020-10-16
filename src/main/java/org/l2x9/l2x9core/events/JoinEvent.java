package org.l2x9.l2x9core.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.potion.PotionEffect;
import org.l2x9.l2x9core.L2X9Core;

public class JoinEvent implements Listener {
    L2X9Core plugin;

    public JoinEvent(L2X9Core plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        System.out.println(plugin.getConfig().getBoolean("Antiillegal.Block-Place-Enabled"));
        Player player = e.getPlayer();
        if (player.getActivePotionEffects() != null) {
            for (PotionEffect effects : player.getActivePotionEffects()) {
                if (effects.getAmplifier() > 5) {
                    player.removePotionEffect(effects.getType());
                }
            }

        }
        if (!player.hasPlayedBefore()) {
            Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("FirstJoin.Message").replace("{Player}", player.getName())));
        }
        if (plugin.discordWebhook.alertsEnabled() && player.isOp()) {
            if (plugin.getConfigBoolean("AlertSystem.OppedPlayerJoin")) {
                plugin.discordWebhook.setContent(plugin.getPingRole() + " [OppedPlayerJoin] Player with op by the name of " + player.getName() + " Joined the server");
                plugin.discordWebhook.execute();
            }
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        if (event.getReason().equalsIgnoreCase("Kicked for spamming")) {
            event.setCancelled(true);
        }
    }
}
