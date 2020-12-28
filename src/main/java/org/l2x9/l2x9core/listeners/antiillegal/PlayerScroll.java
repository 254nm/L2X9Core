package org.l2x9.l2x9core.listeners.antiillegal;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.l2x9.l2x9core.Main;
import org.l2x9.l2x9core.util.Utils;

public class PlayerScroll implements Listener {
    Main plugin;

    public PlayerScroll(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemMove(PlayerItemHeldEvent event) {
        try {
            if (plugin.getConfig().getBoolean("Antiillegal.PlayerHotbarMove-Enabled")) {
                Player player = event.getPlayer();
                plugin.getItemUtils().deleteIllegals(player.getInventory());
            }
        } catch (Error | Exception throwable) {
            Utils.reportException(throwable);

        }
    }
}