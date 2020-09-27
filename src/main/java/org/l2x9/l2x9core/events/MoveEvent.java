package org.l2x9.l2x9core.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.l2x9.l2x9core.Main;

public class MoveEvent implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        int x = player.getLocation().getBlockX();
        int z = player.getLocation().getBlockZ();
        Location bottom = new Location(player.getWorld(), z, 5, x);
        Location top = new Location(player.getWorld(), z, 125, x);
        if (!(player.hasPermission("antifag.netherroof.bypass"))) {

            if (player.getWorld().getEnvironment() == Environment.NETHER
                    && player.getLocation().getBlockY() > Main.getPlugin().getConfig().getInt("Nether.Top-Layer")
                    && !player.isOp()) {

                player.teleport(top);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Main.getPlugin().getConfig().getString("Nether.Top-message")));
                if (Main.getPlugin().getConfig().getString("Nether.top-bottom-do-damage").equalsIgnoreCase("true")) {
                    player.setHealth(0);
                }
            }
            if (player.getWorld().getEnvironment() == Environment.NETHER
                    && player.getLocation().getBlockY() < Main.getPlugin().getConfig().getInt("Nether.Bottom-Layer")
                    && !player.isOp()) {

                player.teleport(bottom);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Main.getPlugin().getConfig().getString("Nether.Bottom-message")));
                if (Main.getPlugin().getConfig().getString("Nether.top-bottom-do-damage").equalsIgnoreCase("true")) {
                    player.damage(40);

                }
            }
        }
    }
}
