package org.l2x9.l2x9core.listeners.antiillegal;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.l2x9.l2x9core.Main;
import org.l2x9.l2x9core.util.Utils;

public class InventoryOpen implements Listener {
    Main plugin;

    public InventoryOpen(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    @AntiIllegal(EventName = "InventoryCloseEvent")
    public void onInventoryClose(InventoryOpenEvent event) {
        try {
            if (plugin.getConfig().getBoolean("Antiillegal.InventoryOpen-Enabled")) {
                Inventory inv = event.getInventory();
                plugin.getItemUtils().deleteIllegals(inv);
            }
        } catch (Error | Exception throwable) {
            Utils.reportException(throwable);

        }
    }
}