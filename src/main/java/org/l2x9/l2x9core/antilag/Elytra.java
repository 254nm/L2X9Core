package org.l2x9.l2x9core.antilag;

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
    @EventHandler
    public void onToggleGlide(EntityToggleGlideEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (Utils.getTps() <= Main.getPlugin().getConfig().getInt("Elytra.Disable-TPS")) {
                event.setCancelled(true);
                Utils.sendMessage(player, Main.getPlugin().getConfig().getString("ElytraLowTPS.Message").replace("{tps}", "" + Main.getPlugin().getConfig().getInt("Elytra.Disable-TPS")));
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
