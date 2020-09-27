package org.l2x9.l2x9core.antiillegal;

import org.bukkit.block.ShulkerBox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.l2x9.l2x9core.Main;

@SuppressWarnings("deprecation")
public class ItemPickup implements Listener {
    ItemUtils ut = new ItemUtils();

    @EventHandler
    @AntiIllegal(EventName = "PlayerPickupItemEvent")
    public void onPickup(PlayerPickupItemEvent event) {
        if (Main.getPlugin().getConfig().getBoolean("Antiillegal.ItemPickup-Enabled")) {
            ItemStack item = event.getItem().getItemStack();
            if (ut.isEnchantedBlock(item) || ut.hasIllegalNBT(item) || ut.hasIllegalEnchants(item)
                    || ut.isOverstacked(item) || ut.isIllegal(item)) {
                event.setCancelled(true);
                event.getItem().remove();
            }
            if (item.getItemMeta() instanceof BlockStateMeta) {
                BlockStateMeta itemMeta = (BlockStateMeta) item.getItemMeta();
                if (itemMeta.getBlockState() instanceof ShulkerBox) {
                    ShulkerBox box = (ShulkerBox) itemMeta.getBlockState();
                    for (ItemStack shulkerItem : box.getInventory().getContents()) {
                        if (shulkerItem != null) {
                            if (ut.isArmor(item) || ut.isTool(item)) {
                                if (item.getDurability() > item.getType().getMaxDurability()) {
                                    event.getItem().remove();
                                    event.setCancelled(true);
                                }
                                if (item.getDurability() < 0) {
                                    event.getItem().remove();
                                    event.setCancelled(true);
                                }
                            }
                            if (ut.isIllegal(shulkerItem)) {
                                event.getItem().remove();
                            }
                            if (ut.hasIllegalNBT(shulkerItem)) {
                                event.getItem().remove();
                                event.setCancelled(true);

                            }
                            if (ut.isOverstacked(shulkerItem)) {
                                event.getItem().remove();
                                event.setCancelled(true);
                            }
                            if (ut.hasIllegalEnchants(shulkerItem)) {
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
