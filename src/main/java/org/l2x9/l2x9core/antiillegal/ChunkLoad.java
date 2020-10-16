package org.l2x9.l2x9core.antiillegal;

import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.l2x9.l2x9core.L2X9Core;

public class ChunkLoad implements Listener {
    ItemUtils itemUtils;
    L2X9Core plugin;

    public ChunkLoad(L2X9Core plugin) {
        this.plugin = plugin;
        itemUtils = new ItemUtils(plugin);
    }

    @EventHandler
    @AntiIllegal(EventName = "ChunkLoadEvent")
    public void onLoad(ChunkLoadEvent event) {
        if (plugin.getConfig().getBoolean("Antiillegal.ChunkLoad-Enabled")) {
            for (BlockState state : event.getChunk().getTileEntities()) {
                if (state instanceof Container) {
                    Container container = (Container) state;
                    itemUtils.deleteIllegals(container.getInventory());

                }
            }
        }
    }
}