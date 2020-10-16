package org.l2x9.l2x9core.antiillegal;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.l2x9.l2x9core.L2X9Core;

public class InventoryOpen implements Listener {
    ItemUtils itemUtils;
    L2X9Core plugin;

    public InventoryOpen(L2X9Core plugin) {
        this.plugin = plugin;
        itemUtils = new ItemUtils(plugin);
    }

    @EventHandler
    @AntiIllegal(EventName = "InventoryCloseEvent")
    public void onInventoryClose(InventoryOpenEvent event) {
        if (plugin.getConfig().getBoolean("Antiillegal.InventoryOpen-Enabled")) {
            Inventory inv = event.getInventory();
            itemUtils.deleteIllegals(inv);
        }
    }
}