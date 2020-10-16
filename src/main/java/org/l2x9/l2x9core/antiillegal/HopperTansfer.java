package org.l2x9.l2x9core.antiillegal;

import org.bukkit.ChatColor;
import org.bukkit.block.ShulkerBox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.l2x9.l2x9core.L2X9Core;

public class HopperTansfer implements Listener {
    ItemUtils itemUtils;
    L2X9Core plugin;

    public HopperTansfer(L2X9Core plugin) {
        this.plugin = plugin;
        itemUtils = new ItemUtils(plugin);
    }

    @EventHandler
    @AntiIllegal(EventName = "InventoryMoveItemEvent")
    public void onInventoryClose(InventoryMoveItemEvent event) {
        if (plugin.getConfig().getBoolean("Antiillegal.HopperTransfer-Enabled")) {
            Inventory inv = event.getSource();
            if (inv.getContents() != null) {
                for (ItemStack item : inv.getStorageContents()) {
                    if (item != null) {
                        if (itemUtils.isArmor(item) || itemUtils.isTool(item)) {
                            if (item.getDurability() > item.getType().getMaxDurability()) {
                                item.setDurability(item.getType().getMaxDurability());
                            }
                            if (item.getDurability() < 0) {
                                item.setDurability((short) 1);
                            }

                        }
                        if (itemUtils.isIllegal(item)) {
                            inv.remove(item);
                            event.setCancelled(true);
                        }
                        if (itemUtils.hasIllegalNBT(item)) {
                            inv.remove(item);
                            event.setCancelled(true);

                        }
                        if (itemUtils.isOverstacked(item)) {
                            item.setAmount(item.getMaxStackSize());
                            event.setCancelled(true);
                        }
                        if (itemUtils.hasIllegalEnchants(item)) {
                            inv.remove(item);
                            event.setCancelled(true);
                        }
                        if (item.hasItemMeta()) {
                            ItemMeta meta = item.getItemMeta();
                            if (meta.hasDisplayName()) {
                                String name = ChatColor.stripColor(meta.getDisplayName());
                                meta.setDisplayName(ChatColor.stripColor(meta.getDisplayName()));
                                event.setCancelled(true);
                                if (name.toCharArray().length > 35) {
                                    String newName = name.substring(0, 35);
                                    meta.setDisplayName(newName);
                                    event.setCancelled(true);
                                }
                                item.setItemMeta(meta);
                            }
                            if (itemUtils.isEnchantedBlock(item)) {
                                event.setCancelled(true);
                            }
                            if (item.getItemMeta() instanceof BlockStateMeta) {
                                BlockStateMeta itemMeta = (BlockStateMeta) item.getItemMeta();
                                if (itemMeta.getBlockState() instanceof ShulkerBox) {
                                    ShulkerBox shulker = (ShulkerBox) itemMeta.getBlockState();
                                    for (ItemStack shulkerItem : shulker.getInventory().getContents()) {
                                        if (shulkerItem != null) {
                                            if (itemUtils.isArmor(item) || itemUtils.isTool(item)) {
                                                if (item.getDurability() > item.getType().getMaxDurability()) {
                                                    inv.remove(item);
                                                    event.setCancelled(true);
                                                }
                                                if (item.getDurability() < 0) {
                                                    inv.remove(item);
                                                    event.setCancelled(true);
                                                }
                                            }
                                            if (itemUtils.isIllegal(shulkerItem)) {
                                                inv.remove(item);
                                            }
                                            if (itemUtils.hasIllegalNBT(shulkerItem)) {
                                                inv.remove(item);
                                                event.setCancelled(true);
                                            }
                                            if (itemUtils.isOverstacked(shulkerItem)) {
                                                inv.remove(item);
                                                event.setCancelled(true);
                                            }
                                            if (itemUtils.hasIllegalEnchants(shulkerItem)) {
                                                inv.remove(item);
                                                event.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}