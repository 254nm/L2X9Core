package org.l2x9.l2x9core.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.l2x9.l2x9core.util.Utils;
import org.l2x9.saldupe.api.PlayerDupeEvent;

import java.util.ArrayList;

public class DupeEvt implements Listener {
    @EventHandler
    public void onDupe(PlayerDupeEvent event) {
        ArrayList<Entity> items = new ArrayList<>();
        for (Entity entity : event.getPlayer().getLocation().getNearbyEntities(30, 30, 30)) {
            if (entity instanceof Item) {
                items.add(entity);
            }
        }
        if (items.size() > 20) {
            for (Entity entity : items) {
                if (entity != null) {
                    entity.remove();
                }
            }
        }
    }

    @EventHandler
    public void onHopper(InventoryMoveItemEvent event) {
        if (Utils.getTps() < 17) {
            if (event.getSource().getType() == InventoryType.HOPPER) {
                event.setCancelled(true);
            }
        }
    }
}