package org.l2x9.l2x9core.antiillegal;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.l2x9.l2x9core.L2X9Core;

public class PlayerScroll implements Listener {
    ItemUtils itemUtils;
    L2X9Core plugin;

    public PlayerScroll(L2X9Core plugin) {
        this.plugin = plugin;
        itemUtils = new ItemUtils(plugin);
    }

    @EventHandler
    public void onItemMove(PlayerItemHeldEvent event) {
        if (plugin.getConfig().getBoolean("Antiillegal.PlayerHotbarMove-Enabled")) {
            Player player = event.getPlayer();
            itemUtils.deleteIllegals(player.getInventory());
        }
    }
}
