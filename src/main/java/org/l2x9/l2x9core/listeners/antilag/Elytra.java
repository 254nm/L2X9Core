package org.l2x9.l2x9core.listeners.antilag;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.l2x9.l2x9core.Main;
import org.l2x9.l2x9core.util.Utils;

public class Elytra implements Listener {
    Main plugin;

    public Elytra(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onToggleGlide(EntityToggleGlideEvent event) {
        try {
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                if (Utils.getTps() <= plugin.getConfig().getInt("Elytra.Disable-TPS")) {
                    event.setCancelled(true);
                    Utils.sendMessage(player, plugin.getConfig().getString("ElytraLowTPS.Message").replace("{tps}", "" + plugin.getConfig().getInt("Elytra.Disable-TPS")));
                }
            }
        } catch (Error | Exception throwable) {
            Utils.reportException(throwable);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        try {
            Player player = event.getPlayer();
            if (Utils.getTps() <= plugin.getConfig().getInt("Elytra.Disable-TPS")) {
                if (event.getPlayer().isGliding()) {
                    event.getPlayer().setGliding(false);
                    Utils.sendMessage(player, plugin.getConfig().getString("ElytraLowTPS.Message").replace("{tps}", "" + plugin.getConfig().getInt("Elytra.Disable-TPS")));
                }
            }
            Location from = event.getFrom();
            Location to = event.getTo();
            double distX = to.getX() - from.getX();
            double distZ = to.getZ() - from.getZ();
            double finalValue = Math.round(Math.hypot(distX, distZ));
            if (finalValue > plugin.getConfig().getInt("Speed.Limit")) {
                event.setCancelled(true);
                Utils.sendMessage(player, plugin.getConfig().getString("Speed.Message"));
                if (plugin.getConfig().getBoolean("Speed.Damage")) {
                    player.damage(plugin.getConfig().getInt("Speed.DamageAmount"));
                }
                if (player.isGliding()) {
                    player.setGliding(false);
                    if (player.getInventory().getChestplate().getType() == Material.ELYTRA) {
                        player.getWorld().dropItem(player.getLocation(), player.getInventory().getChestplate());
                        player.getInventory().setChestplate(null);
                    }
                }
            }
        } catch (Error | Exception throwable) {
            Utils.reportException(throwable);

        }
    }
}