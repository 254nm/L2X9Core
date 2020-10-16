package org.l2x9.l2x9core.antilag;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.l2x9.l2x9core.L2X9Core;
import org.l2x9.l2x9core.util.Utils;

public class Elytra implements Listener {
    L2X9Core plugin;

    public Elytra(L2X9Core plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onToggleGlide(EntityToggleGlideEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (Utils.getTps() <= plugin.getConfig().getInt("Elytra.Disable-TPS")) {
                event.setCancelled(true);
                Utils.sendMessage(player, plugin.getConfig().getString("ElytraLowTPS.Message").replace("{tps}", "" + plugin.getConfig().getInt("Elytra.Disable-TPS")));
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();
        double distX = to.getX() - from.getX();
        double distZ = to.getZ() - from.getZ();
        double finalValue = Math.round(Math.hypot(distX, distZ));
        if (finalValue > 3) {
            event.setCancelled(true);
            player.damage(15);
            if (player.isGliding()) {
                player.setGliding(false);
                if (player.getInventory().getChestplate().getType() == Material.ELYTRA) {
                    player.getWorld().dropItem(player.getLocation(), player.getInventory().getChestplate());
                    player.getInventory().setChestplate(null);
                }
            }
        }
    }
}
