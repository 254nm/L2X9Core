package org.l2x9.l2x9core.events;

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

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        try {
            System.out.println(Main.getPlugin().getConfig().getBoolean("Antiillegal.Block-Place-Enabled"));
            Player player = e.getPlayer();
            if (player.getActivePotionEffects() != null) {
                for (PotionEffect effects : player.getActivePotionEffects()) {
                    if (effects.getAmplifier() > 5) {
                        player.removePotionEffect(effects.getType());
                    }
                }

            }
            if (Main.getPlugin().getConfigBoolean("FirstJoin.Enabled")) {
                if (!player.hasPlayedBefore()) {
                    Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                            Main.getPlugin().getConfig().getString("FirstJoin.Message").replace("{Player}", player.getName())));
                }
            }
            if (Main.getPlugin().discordWebhook.alertsEnabled() && player.isOp()) {
                if (Main.getPlugin().getConfigBoolean("AlertSystem.OppedPlayerJoin")) {
                    Main.getPlugin().discordWebhook.setContent(Main.getPlugin().getPingRole() + " [OppedPlayerJoin] Player with op by the name of " + player.getName() + " Joined the server");
                    Main.getPlugin().discordWebhook.execute();
                }
            }
        } catch (Error | Exception throwable) {
            Utils.reportException(throwable);
            throwable.printStackTrace();
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        if (event.getReason().equalsIgnoreCase("Kicked for spamming")) {
            event.setCancelled(true);
        }
    }
}
