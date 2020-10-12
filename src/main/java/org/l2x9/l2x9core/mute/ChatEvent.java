package org.l2x9.l2x9core.mute;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.l2x9.l2x9core.Main;
import org.l2x9.l2x9core.util.Utils;

public class ChatEvent implements Listener {
    Main plugin;

    public ChatEvent(Main main) {
        plugin = main;
    }

    public static void sendFakeMessage(AsyncPlayerChatEvent event, Player player) {
        event.setCancelled(true);
        if (!event.getMessage().startsWith(">")) {
            if (!event.getMessage().startsWith("#")) {
                Utils.sendMessage(event.getPlayer(), "<" + player.getName() + "> " + event.getMessage());
            } else {
                Utils.sendMessage(event.getPlayer(), "<" + player.getName() + "> " + "&e" + event.getMessage());
            }
        } else {
            Utils.sendMessage(event.getPlayer(), "<" + player.getName() + "> " + "&a" + event.getMessage());
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent event) {
        DataHandler dataHandler = plugin.dh;
        if (dataHandler.mutedPlayers.contains(event.getPlayer().getName())) {
            Player player = event.getPlayer();
            sendFakeMessage(event, player);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        DataHandler dataHandler = plugin.dh;
        if (dataHandler.mutedPlayers.contains(event.getPlayer().getName())) {
            String firstCommand = event.getMessage().split(" ")[0];
            for (String word : plugin.getConfig().getStringList("Mute.BlockedCommands")) {
                if (firstCommand.equalsIgnoreCase("/" + word)) {
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }
}
