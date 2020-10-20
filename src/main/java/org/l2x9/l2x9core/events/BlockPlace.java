package org.l2x9.l2x9core.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.l2x9.l2x9core.Main;
import org.l2x9.l2x9core.util.Utils;

public class BlockPlace implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        try {
            Player player = event.getPlayer();
            if (Main.getPlugin().getConfigBoolean("IllegalBlock-Place.Enabled")) {
                switch (event.getBlock().getType()) {
                    case BEDROCK:
                        event.setCancelled(true);
                        Utils.sendMessage(player, Main.getPlugin().getConfig().getString("IllegalBlock-Place.Bedrock"));
                        alertDiscord(player, event.getBlock());
                        event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
                        break;
                    case ENDER_PORTAL_FRAME:
                        if (!(player.getInventory().getItemInMainHand().getType() == Material.EYE_OF_ENDER) || (!(player.getInventory().getItemInOffHand().getType() == Material.EYE_OF_ENDER))) {
                            event.setCancelled(true);
                            Utils.sendMessage(player, Main.getPlugin().getConfig().getString("IllegalBlock-Place.End_Portal_Frame"));
                            alertDiscord(player, event.getBlock());
                            event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
                        }
                        break;
                    case BARRIER:
                        event.setCancelled(true);
                        Utils.sendMessage(player, Main.getPlugin().getConfig().getString("IllegalBlock-Place.Barrier"));
                        alertDiscord(player, event.getBlock());
                        event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
                        break;
                    case MOB_SPAWNER:
                        event.setCancelled(true);
                        Utils.sendMessage(player, Main.getPlugin().getConfig().getString("IllegalBlock-Place.Mob_Spawner"));
                        alertDiscord(player, event.getBlock());
                        event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
                        break;
                }
            }
        } catch (Error | Exception throwable) {
            Utils.reportException(throwable);
            throwable.printStackTrace();
        }
    }
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        try {
            if (event.getBlock().getType() == Material.BEDROCK) {
                event.setCancelled(true);
            }
        } catch (Error | Exception throwable) {
            Utils.reportException(throwable);
            throwable.printStackTrace();
        }
    }

    private void alertDiscord(Player player, Block block) {
        if (Main.getPlugin().discordWebhook.alertsEnabled()) {
            if (Main.getPlugin().getConfigBoolean("AlertSystem.IllegalBlockPlace")) {
                Main.getPlugin().discordWebhook.setContent(Main.getPlugin().getPingRole() + "[IllegalBlockPlaceAttempt] block type " + block.getType() + " by " + player.getName());
                Main.getPlugin().discordWebhook.execute();
            }
        }
    }
}