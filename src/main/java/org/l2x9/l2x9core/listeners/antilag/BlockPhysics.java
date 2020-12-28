package org.l2x9.l2x9core.listeners.antilag;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.l2x9.l2x9core.Main;
import org.l2x9.l2x9core.util.Utils;

public class BlockPhysics implements Listener {
    Main plugin;

    public BlockPhysics(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLiquidSpread(BlockFromToEvent event) {
        try {
            int disableTPS = plugin.getConfig().getInt("BlockPhysics-disable-tps");
            if (Utils.getTps() < disableTPS) {
                Material type = event.getBlock().getType();
                if (isChecked(type)) {
                    event.setCancelled(true);
                }
            }
        } catch (Error | Exception throwable) {
            Utils.reportException(throwable);

        }
    }

    private boolean isChecked(Material material) {
        switch (material) {
            case LAVA:
            case STATIONARY_LAVA:
            case WATER:
            case STATIONARY_WATER:
                return true;
        }
        return false;
    }
}