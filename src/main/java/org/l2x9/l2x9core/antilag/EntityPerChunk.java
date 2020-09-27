package org.l2x9.l2x9core.antilag;

import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.HashSet;

public class EntityPerChunk implements Listener {
    @EventHandler
    public void onSpawn(EntitySpawnEvent entitySpawnEvent) {
        if (entitySpawnEvent.getEntity() instanceof FallingBlock) {
            HashSet<FallingBlock> hashSet = new HashSet<>();
            for (Entity entity : entitySpawnEvent.getLocation().getChunk().getEntities()) {
                if (entity instanceof FallingBlock) {
                    hashSet.add((FallingBlock) entity);
                }
            }
            if (hashSet.size() > 5) {
                entitySpawnEvent.setCancelled(true);
                entitySpawnEvent.getEntity().remove();
            }
        }
    }
}
