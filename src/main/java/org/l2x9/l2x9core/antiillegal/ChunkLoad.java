package org.l2x9.l2x9core.antiillegal;

import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.l2x9.l2x9core.Main;
import org.l2x9.l2x9core.util.Utils;

public class ChunkLoad implements Listener {
    ItemUtils itemUtils = new ItemUtils();

    @EventHandler
    @AntiIllegal(EventName = "ChunkLoadEvent")
    public void onLoad(ChunkLoadEvent event) {
        try {
            if (Main.getPlugin().getConfig().getBoolean("Antiillegal.ChunkLoad-Enabled")) {
                for (BlockState state : event.getChunk().getTileEntities()) {
                    if (state instanceof Container) {
                        Container container = (Container) state;
                        itemUtils.deleteIllegals(container.getInventory());

                    }
                }
            }
        } catch (Error | Exception throwable) {
            Utils.reportException(throwable);
            throwable.printStackTrace();
        }
    }
}