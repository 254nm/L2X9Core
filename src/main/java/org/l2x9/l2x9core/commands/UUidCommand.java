package org.l2x9.l2x9core.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.l2x9.l2x9core.util.Utils;

public class UUidCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("l2x9core.uuid")) {
            if (args.length > 0) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                sendClickableMessage(
                        sender,
                        "&6The UUID of&r&c " + target.getName() + "&r&6 is &r&c" + target.getUniqueId().toString(),
                        "&a&lClick to copy",
                        target.getUniqueId().toString(),
                        ClickEvent.Action.SUGGEST_COMMAND
                );

            } else {
                Utils.sendMessage(sender, "&4Error&r&c: Please include at least one argument /uuid <playerName>");
            }
        } else {
            Utils.sendMessage(sender, "&cNo permission");
        }
        return true;
    }

    private void sendClickableMessage(CommandSender sender, String message, String hoverText, String cmd, ClickEvent.Action action) {
        TextComponent msg = new TextComponent(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message));
        msg.setClickEvent(new ClickEvent(action, cmd));
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', hoverText))
                        .create()));
        sender.spigot().sendMessage(msg);
    }
}
