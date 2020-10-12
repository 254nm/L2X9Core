package org.l2x9.l2x9core.mute;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.l2x9.l2x9core.Main;
import org.l2x9.l2x9core.util.Utils;

public class MuteCommand implements CommandExecutor {
    Main plugin;

    public MuteCommand(Main main) {
        plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("mute.use")) {
            if (args.length > 0) {
                DataHandler dataHandler = plugin.dh;
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                if (!dataHandler.mutedPlayers.contains(offlinePlayer.getName())) {
                    dataHandler.mutedPlayers.add(offlinePlayer.getName());
                    dataHandler.getMutedConfig().set(dataHandler.path, dataHandler.mutedPlayers);
                    Utils.sendMessage(sender, Utils.getPrefix() + "&c" + offlinePlayer.getName() + "&r&a Has been muted");
                } else {
                    dataHandler.mutedPlayers.remove(offlinePlayer.getName());
                    Utils.sendMessage(sender, Utils.getPrefix() + "&c" + offlinePlayer.getName() + "&r&a Has been unmuted");
                    dataHandler.getMutedConfig().set(dataHandler.path, dataHandler.mutedPlayers);
                }
                dataHandler.saveMutedYml();
            } else {
                Utils.sendMessage(sender, "&4Error&r&c Not enough arguments /mute <playerName>");
            }
        } else {
            Utils.sendMessage(sender, "&cNo permission");
        }
        return true;
    }
}
