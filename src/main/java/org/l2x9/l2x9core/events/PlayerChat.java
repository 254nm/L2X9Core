package org.l2x9.l2x9core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.l2x9.l2x9core.Main;
import org.l2x9.l2x9core.mute.ChatEvent;
import org.l2x9.l2x9core.util.Cooldown;
import org.l2x9.l2x9core.util.Utils;

import java.util.List;

public class PlayerChat implements Listener {
    Cooldown cm = new Cooldown();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!player.isOp()) {
            if (cm.checkCooldown(player)) {
                cm.setCooldown(player, Main.getPlugin().getConfig().getInt("Chat.Cooldown"));
            } else {
                event.setCancelled(true);
            }
        }
        if (!player.isOp()) {
            if (Main.getPlugin().getConfig().getStringList("Chat.Blocked-words") != null) {
                List<String> list = Main.getPlugin().getConfig().getStringList("Chat.Blocked-words");
                boolean hasBlackListedWord = false;
                for (String word : list) {
                    if (event.getMessage().toLowerCase().contains(word)) {
                        hasBlackListedWord = true;
                        break;
                    }
                }
                if (hasBlackListedWord) {
                    Utils.println(Utils.getPrefix() + "&6Prevented &r&c" + player.getName() + " &r&6from advertising");
                    ChatEvent.sendFakeMessage(event, player);
                }
            }
        }
    }
}