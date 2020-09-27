package org.l2x9.l2x9core.antilag;

import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.l2x9.l2x9core.Main;
import org.l2x9.l2x9core.util.Utils;
import org.l2x9.l2x9core.util.ViolationUtils;

public class BlockRedstone implements Listener {
    public static ViolationUtils vm = new ViolationUtils();

    @EventHandler
    public void onRedstoneTick(BlockRedstoneEvent event) {
        try {
            if (Utils.getTps() <= Main.getPlugin().getConfig().getInt("Redstone.Disable-TPS")
                    && !(event.getBlock().getType() == Material.TRAPPED_CHEST)) {
                Block block = event.getBlock();
                String fagMachine = "Deleted a taco machine at " + block.getLocation().getBlockX() + " "
                        + block.getLocation().getBlockY() + " " + block.getLocation().getBlockZ() + " in world "
                        + block.getLocation().getWorld().getName() + "";
                event.setNewCurrent(0);
                event.getBlock().setType(Material.AIR);
                //Utils.getNearbyPlayers(20, block.getLocation()).chat(">>Tp to me i have a `fag machine");
                sendOpMessage("[&b&lL2X9&r&3&lCore&r] &6Removed a lag machine at &r&1" + block.getLocation().getBlockX() + " " + block.getLocation().getBlockY() + " " + block.getLocation().getBlockZ() + "&r&6 owned by &r&1 " + Utils.getNearbyPlayers(50, block.getLocation()).getName(), "&aClick to telepot to the player", "/tp " + Utils.getNearbyPlayers(50, block.getLocation()).getName(), ClickEvent.Action.RUN_COMMAND);
                //event.getBlock().getLocation().getWorld().strikeLightning(block.getLocation());
                System.out.println(ChatColor.translateAlternateColorCodes('&', "&a" + fagMachine));
                boolean alreadySent = false;
                for (Entity entity : block.getChunk().getEntities()) {
                    if (!(entity instanceof Player)) {
                        entity.remove();
                        if (!alreadySent) {
                            System.out.println(ChatColor.GREEN + "Removed " + block.getChunk().getEntities().length + " " + entity.getType().toString().toLowerCase().concat("s") + " from a laggy chunk");
                            Utils.sendOpMessage("[&b&lL2X9&r&3&lCore&r] &6Removed &r&1" + block.getChunk().getEntities().length + " " + entity.getType().toString().toLowerCase().concat("s") + "&r&6 from a laggy chunk");
                            alreadySent = true;
                        }
                    }
                }
            }
        } catch (StackOverflowError | NullPointerException ignored) {

        }
    }

    @EventHandler
    public void onPull(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType() == Material.LEVER) {
                Player player = event.getPlayer();
                vm.addVls(player, 3);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (vm.vlMapContainsPlayer(player)) {
                            vm.removeVl(player, vm.getVls(player) - 1);
                        }
                    }
                }.runTaskLater(Main.getPlugin(), 5);
                System.out.println(vm.getVls(player));
                if (vm.getVls(player) >= 5) {
                    event.setCancelled(true);
                    Utils.kickPlayer(player, Utils.getPrefix() + "&6AntiFaggotExploit by 254n_m");
                    vm.resetVls(player);

                }
            }
        }
    }

    private void sendOpMessage(String message, String hoverText, String cmd, ClickEvent.Action action) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.isOp()) {
                Utils.sendClickableMessage(online, message, hoverText, cmd, action);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Chunk chunk = event.getBlock().getChunk();
        if (Utils.countBlockPerChunk(chunk, Material.REDSTONE_WIRE) > Main.getPlugin().getConfig().getInt("Redstone.Amount-per-chunk")) {
            event.setCancelled(true);
            Utils.sendMessage(event.getPlayer(), Utils.getPrefix() + "&6Please limit redstone to &r&c16&r&6 per chunk");

        }
    }
}
