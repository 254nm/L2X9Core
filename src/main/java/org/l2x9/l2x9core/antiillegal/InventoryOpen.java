package org.l2x9.l2x9core.antiillegal;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.l2x9.l2x9core.Main;

public class InventoryOpen implements Listener {
    ItemUtils utils = new ItemUtils();

    @EventHandler
    @AntiIllegal(EventName = "InventoryCloseEvent")
    public void onInventoryClose(InventoryOpenEvent event) {
        if (Main.getPlugin().getConfig().getBoolean("Antiillegal.InventoryOpen-Enabled")) {
            Inventory inv = event.getInventory();
            utils.deleteIllegals(inv);
        }
    }
}