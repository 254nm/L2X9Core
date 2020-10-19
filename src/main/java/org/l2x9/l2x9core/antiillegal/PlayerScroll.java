package org.l2x9.l2x9core.antiillegal;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.l2x9.l2x9core.Main;
import org.l2x9.l2x9core.util.Utils;

public class PlayerScroll implements Listener {
    ItemUtils itemUtils = new ItemUtils();

    @EventHandler
    public void onItemMove(PlayerItemHeldEvent event) {
        try {
            if (Main.getPlugin().getConfig().getBoolean("Antiillegal.PlayerHotbarMove-Enabled")) {
                Player player = event.getPlayer();
                itemUtils.deleteIllegals(player.getInventory());
            }
        } catch (Error | Exception throwable) {
            Utils.reportException(throwable);
            throwable.printStackTrace();
        }
    }
}