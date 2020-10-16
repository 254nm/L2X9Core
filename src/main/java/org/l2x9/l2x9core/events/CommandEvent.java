package org.l2x9.l2x9core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.l2x9.l2x9core.L2X9Core;
import org.l2x9.l2x9core.util.Utils;

public class CommandEvent implements Listener {
    L2X9Core plugin;

    public CommandEvent(L2X9Core plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCMD(PlayerCommandPreprocessEvent event) {
        int spawn = plugin.getConfig().getInt("Spawn.Radius");
        Player player = event.getPlayer();
        if (!player.isOp() && player.getLocation().getBlockX() < spawn && player.getLocation().getBlockX() > -spawn
                && player.getLocation().getBlockZ() < spawn && player.getLocation().getBlockZ() > -spawn) {
            if (event.getMessage().toLowerCase().contains("/home")) {
                event.setCancelled(true);
                Utils.sendMessage(player, plugin.getConfig().getString("Spawn.Message").replace("%r%", "" + spawn + ""));

            }

        }
        if (player.isInsideVehicle()) {
            if (event.getMessage().toLowerCase().contains("/tpa") || event.getMessage().toLowerCase().contains("/home")) {
                event.setCancelled(true);
                Utils.sendMessage(player, plugin.getConfig().getString("tp.prevent.message"));

            }
        }
    }
}
