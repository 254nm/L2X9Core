package org.l2x9.l2x9core.antiillegal;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.l2x9.l2x9core.Main;

import java.util.Map.Entry;

public class BlockPlace implements Listener {
    ItemUtils itemUtils = new ItemUtils();

    @EventHandler
    @AntiIllegal(EventName = "BlockPlaceEvent")
    public void onPlace(BlockPlaceEvent event) {
        if (Main.getPlugin().getConfig().getBoolean("Antiillegal.Block-Place-Enabled")) {
            if (itemUtils.isIllegal(event.getItemInHand())) {
                event.setCancelled(true);
                event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
            }
            ItemStack itemStack = event.getItemInHand();
            BlockStateMeta blockStateMeta = null;
            ShulkerBox shulkerBox = null;
            boolean illegalsFound = false;
            if (itemStack.getItemMeta() instanceof BlockStateMeta) {
                blockStateMeta = (BlockStateMeta) itemStack.getItemMeta();
                if (blockStateMeta.getBlockState() instanceof ShulkerBox) {
                    shulkerBox = (ShulkerBox) blockStateMeta.getBlockState();
                    Inventory boxInventory = shulkerBox.getInventory();
                    for (ItemStack item : boxInventory.getContents()) {
                        if (item != null) {
                            if (itemUtils.isArmor(item) || itemUtils.isTool(item)) {
                                if (item.getDurability() > item.getType().getMaxDurability()) {
                                    item.setDurability(item.getType().getMaxDurability());
                                    illegalsFound = true;
                                    event.setCancelled(true);
                                }
                                if (item.getDurability() < 0) {
                                    item.setDurability((short) 1);
                                    illegalsFound = true;
                                    event.setCancelled(true);
                                }
                            }
                            if (itemUtils.isIllegal(item)) {
                                boxInventory.remove(item);
                                illegalsFound = true;
                                event.setCancelled(true);
                            }
                            if (itemUtils.hasIllegalNBT(item)) {
                                boxInventory.remove(item);
                                illegalsFound = true;
                                event.setCancelled(true);
                            }
                            if (itemUtils.isOverstacked(item)) {
                                item.setAmount(item.getMaxStackSize());
                                illegalsFound = true;
                                event.setCancelled(true);
                            }
                            if (itemUtils.hasIllegalEnchants(item)) {
                                for (Entry<Enchantment, Integer> enchantmentIntegerEntry : item.getEnchantments().entrySet()) {
                                    item.removeEnchantment(enchantmentIntegerEntry.getKey());
                                }
                                illegalsFound = true;
                                event.setCancelled(true);
                            }
                            if (item.hasItemMeta()) {
                                ItemMeta meta = item.getItemMeta();
                                if (meta.getDisplayName() != null) {
                                    String name = ChatColor.stripColor(meta.getDisplayName());
                                    meta.setDisplayName(ChatColor.stripColor(meta.getDisplayName()));
                                    if (name.toCharArray().length > 35) {
                                        String newName = name.substring(0, 35);
                                        meta.setDisplayName(newName);
                                    }
                                    item.setItemMeta(meta);
                                    blockStateMeta.setBlockState(shulkerBox);
                                    itemStack.setItemMeta(blockStateMeta);
                                }
                                if (itemUtils.isEnchantedBlock(item)) {
                                    for (Entry<Enchantment, Integer> enchantmentIntegerEntry : item.getEnchantments().entrySet()) {
                                        item.removeEnchantment(enchantmentIntegerEntry.getKey());
                                        illegalsFound = true;
                                        event.setCancelled(true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (illegalsFound) {
                blockStateMeta.setBlockState(shulkerBox);
                itemStack.setItemMeta(blockStateMeta);
                event.setCancelled(true);
            }
        }

    }
}