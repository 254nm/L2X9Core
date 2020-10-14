package org.l2x9.l2x9core.patches;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.l2x9.l2x9core.Main;

public class ChinkBan implements Listener {
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        int x = block.getLocation().getBlockX();
        int y = block.getLocation().getBlockY();
        int z = block.getLocation().getBlockZ();
        String worldName = block.getWorld().getName();
        if (!(player.hasPermission("chunkban.bypass"))) {
            if (isChecked(block)) {
                if (event.getBlock().getChunk().getTileEntities().length > Main.getPlugin().getConfig().getInt("ChunkBan.TileEntity-Max")) {
                    event.setCancelled(true);
                    if (Main.getPlugin().discordWebhook.alertsEnabled()) {
                        if (Main.getPlugin().getConfigBoolean("AlertSystem.OffhandServerCrash")) {
                            Main.getPlugin().discordWebhook.setContent(Main.getPlugin().getPingRole() + " [POSSIBLE CHUNKBAN ATTEMPT] by " + player.getName() + " at " + x + " " + y + z + " in world " + worldName);
                            Main.getPlugin().discordWebhook.execute();
                        }
                    }
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("ChunkBan.Prevent-Message")));

                }
            }
        }
        if (block.getType() == Material.SKULL || block.getType() == Material.SKULL_ITEM) {
            if (block.getChunk().getTileEntities().length > Main.getPlugin().getConfig().getInt("ChunkBan.Skull-Max")) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("ChunkBan.Prevent-Message")));
                if (Main.getPlugin().discordWebhook.alertsEnabled()) {
                    if (Main.getPlugin().getConfigBoolean("AlertSystem.OffhandServerCrash")) {
                        Main.getPlugin().discordWebhook.setContent(Main.getPlugin().getPingRole() + " [POSSIBLE CHUNKBAN ATTEMPT] by " + player.getName() + " at " + x + " " + y + z + " in world " + worldName);
                        Main.getPlugin().discordWebhook.execute();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSpawn(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getItem() != null) {
            if (event.getItem().getType() == Material.ITEM_FRAME) {
                int x = event.getPlayer().getLocation().getBlockX();
                int y = event.getPlayer().getLocation().getBlockY();
                int z = event.getPlayer().getLocation().getBlockZ();
                String worldName = event.getPlayer().getWorld().getName();
                int amount = 0;
                for (Entity entity : event.getPlayer().getChunk().getEntities()) {
                    if (entity instanceof ItemFrame) {
                        amount++;
                    }
                }
                if (amount > Main.getPlugin().getConfig().getInt("ChunkBan.TileEntity-Max")) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("ChunkBan.Prevent-Message")));
                    if (Main.getPlugin().discordWebhook.alertsEnabled()) {
                        if (Main.getPlugin().getConfigBoolean("AlertSystem.ChunkBanAttempt")) {
                            Main.getPlugin().discordWebhook.setContent(Main.getPlugin().getPingRole() + " [Possible ChunkBanAttempt] by " + event.getPlayer().getName() + " at " + x + " " + y + z + " in world " + worldName);
                            Main.getPlugin().discordWebhook.execute();
                        }
                    }
                }
            }
        }
    }

    private boolean isChecked(Block block) {
        switch (block.getType()) {
            case FURNACE:
            case TRAPPED_CHEST:
            case ENCHANTMENT_TABLE:
            case WALL_BANNER:
            case WALL_SIGN:
            case HOPPER:
            case DROPPER:
            case DISPENSER:
            case BREWING_STAND:
            case BEACON:
            case SIGN_POST:
            case ENDER_CHEST:
            case FLOWER_POT:
            case BANNER:
                return true;
        }
        return false;
    }
}