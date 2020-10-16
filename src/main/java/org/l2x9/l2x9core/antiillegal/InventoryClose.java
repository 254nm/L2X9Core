package org.l2x9.l2x9core.antiillegal;

import org.bukkit.block.ShulkerBox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.l2x9.l2x9core.L2X9Core;

public class InventoryClose implements Listener {
    ItemUtils itemUtils;
    L2X9Core plugin;

    public InventoryClose(L2X9Core plugin) {
        this.plugin = plugin;
        itemUtils = new ItemUtils(plugin);
    }

    @EventHandler
    @AntiIllegal(EventName = "InventoryCloseEvent")
    public void onInventoryClose(InventoryCloseEvent event) {
        if (plugin.getConfig().getBoolean("Antiillegal.InventoryClose-Enabled")) {
            Inventory inv = event.getInventory();
            itemUtils.deleteIllegals(inv);
            Inventory playerInv = event.getPlayer().getInventory();
            itemUtils.deleteIllegals(playerInv);
            if (event.getInventory().getType() == InventoryType.SHULKER_BOX) {
                Inventory shulkerInv = event.getInventory();
                for (ItemStack item : shulkerInv.getContents()) {
                    if (item != null) {
                        if (item.getItemMeta() instanceof BlockStateMeta) {
                            BlockStateMeta blockStateMeta = (BlockStateMeta) item.getItemMeta();
                            if (blockStateMeta.getBlockState() instanceof ShulkerBox) {
                                shulkerInv.remove(item);
                            }
                        }
                    }
                }
            }
        }
    }
}