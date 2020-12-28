package org.l2x9.l2x9core.listeners.antiillegal;

import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.l2x9.l2x9core.Main;
import org.l2x9.l2x9core.util.Utils;

public class ChunkLoad implements Listener {
    Main plugin;

    public ChunkLoad(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    @AntiIllegal(EventName = "ChunkLoadEvent")
    public void onLoad(ChunkLoadEvent event) {
        try {
            if (plugin.getConfig().getBoolean("Antiillegal.ChunkLoad-Enabled")) {
                for (BlockState state : event.getChunk().getTileEntities()) {
                    if (state instanceof Container) {
                        Container container = (Container) state;
                        plugin.getItemUtils().deleteIllegals(container.getInventory());

                    }
                }
            }
        } catch (Error | Exception throwable) {
            Utils.reportException(throwable);

        }
    }
}