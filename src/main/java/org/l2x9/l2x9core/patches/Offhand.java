package org.l2x9.l2x9core.patches;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.l2x9.l2x9core.Main;
import org.l2x9.l2x9core.util.SecondPassEvent;
import org.l2x9.l2x9core.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class Offhand implements Listener {
    HashMap<Player, Integer> offhandMap = new HashMap<>();

    @EventHandler
    public void PlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if (isRetardTryingToCrashTheFuckingServerLikeAFuckingFaggot(player)) {
            if (offhandMap.containsKey(player)) {
                offhandMap.replace(player, offhandMap.get(player) + 1);
            } else {
                offhandMap.put(player, 1);
            }
            if (offhandMap.get(player) > 10) {
                player.kickPlayer("Packet Exploit Detected");
                if (Main.getPlugin().discordWebhook.alertsEnabled()) {
                    if (Main.getPlugin().getConfigBoolean("AlertSystem.OffhandServerCrash")) {
                        Main.getPlugin().discordWebhook.setContent(Main.getPlugin().getPingRole() + " [Possible offhand server crash attempt] by " + player.getName());
                        Main.getPlugin().discordWebhook.execute();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSecond(SecondPassEvent event) {
        Utils.secondPass(offhandMap);
    }

    public boolean isRetardTryingToCrashTheFuckingServerLikeAFuckingFaggot(Player player) {
        ItemStack stack = player.getInventory().getItemInMainHand();
        ArrayList<Material> materialArrayList = new ArrayList<>();
        for (Material material : Material.values()) {
            if (material.equals(Material.BOOK)) {
                materialArrayList.add(material);
            }
            if (material.equals(Material.BOOK_AND_QUILL)) {
                materialArrayList.add(material);
            }
            if (material.toString().contains("SHULKER_BOX")) {
                materialArrayList.add(material);
            }
        }
        return materialArrayList.contains(stack.getType());
    }
}

