package org.l2x9.l2x9core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.l2x9.l2x9core.util.Utils;

public class SpeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("l2x9core.speed")) {
                Player player = (Player) sender;
                try {
                    if (!(Float.parseFloat(args[0]) > 1)) {
                        player.setFlySpeed(Float.parseFloat(args[0]));
                        Utils.sendMessage(player, "&6Fly speed set to&r&c " + Float.parseFloat(args[0]) + "");
                    } else Utils.sendMessage(player, "&4Error: &r&cFlying speed must not be above 1");
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    Utils.sendMessage(player, "&4Error: &r&cFlying speed cant be a string");

                }
            }
        } else {
            Utils.sendMessage(sender, "&4Error:&r&c You cant set the speed of console");
        }

        return true;
    }

}
