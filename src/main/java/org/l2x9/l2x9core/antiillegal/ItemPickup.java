package org.l2x9.l2x9core.antiillegal;

import org.bukkit.block.ShulkerBox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.l2x9.l2x9core.L2X9Core;

@SuppressWarnings("deprecation")
public class ItemPickup implements Listener {
    ItemUtils itemUtils;
    L2X9Core plugin;

    public ItemPickup(L2X9Core plugin) {
        this.plugin = plugin;
        itemUtils = new ItemUtils(plugin);
    }

    @EventHandler
    @AntiIllegal(EventName = "PlayerPickupItemEvent")
    public void onPickup(PlayerPickupItemEvent event) {
        if (plugin.getConfig().getBoolean("Antiillegal.ItemPickup-Enabled")) {
            ItemStack item = event.getItem().getItemStack();
            if (itemUtils.isEnchantedBlock(item) || itemUtils.hasIllegalNBT(item) || itemUtils.hasIllegalEnchants(item)
                    || itemUtils.isOverstacked(item) || itemUtils.isIllegal(item)) {
                event.setCancelled(true);
                event.getItem().remove();
            }
            if (item.getItemMeta() instanceof BlockStateMeta) {
                BlockStateMeta itemMeta = (BlockStateMeta) item.getItemMeta();
                if (itemMeta.getBlockState() instanceof ShulkerBox) {
                    ShulkerBox box = (ShulkerBox) itemMeta.getBlockState();
                    for (ItemStack shulkerItem : box.getInventory().getContents()) {
                        if (shulkerItem != null) {
                            if (itemUtils.isArmor(item) || itemUtils.isTool(item)) {
                                if (item.getDurability() > item.getType().getMaxDurability()) {
                                    event.getItem().remove();
                                    event.setCancelled(true);
                                }
                                if (item.getDurability() < 0) {
                                    event.getItem().remove();
                                    event.setCancelled(true);
                                }
                            }
                            if (itemUtils.isIllegal(shulkerItem)) {
                                event.getItem().remove();
                            }
                            if (itemUtils.hasIllegalNBT(shulkerItem)) {
                                event.getItem().remove();
                                event.setCancelled(true);

                            }
                            if (itemUtils.isOverstacked(shulkerItem)) {
                                event.getItem().remove();
                                event.setCancelled(true);
                            }
                            if (itemUtils.hasIllegalEnchants(shulkerItem)) {
                                event.getItem().remove();
                                event.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }

    }
}
