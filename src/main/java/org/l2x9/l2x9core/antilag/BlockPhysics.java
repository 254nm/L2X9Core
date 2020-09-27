package org.l2x9.l2x9core.antilag;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.l2x9.l2x9core.Main;
import org.l2x9.l2x9core.util.Utils;

public class BlockPhysics implements Listener {

    @EventHandler
    public void onLiquidSpread(BlockFromToEvent event) {
        int disableTPS = Main.getPlugin().getConfig().getInt("BlockPhysics-disable-tps");
        if (Utils.getTps() < disableTPS) {
            Material type = event.getBlock().getType();
            if (isChecked(type)) {
                event.setCancelled(true);
            }
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