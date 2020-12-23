package org.l2x9.l2x9core.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.l2x9.l2x9core.Main;

public class DeopOnLeave implements Listener {
    Main plugin;
    public DeopOnLeave(Main plugin) {
       this.plugin = plugin;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
       if (plugin.getConfigBoolean("DeopOnLeave") && player.isOp()) {
           player.setOp(false);
       }
    }
    @EventHandler
    public void onKick(PlayerKickEvent event) {
        Player player = event.getPlayer();
        if (plugin.getConfigBoolean("DeopOnLeave") && player.isOp()) {
            player.setOp(false);
        }
    }
}
