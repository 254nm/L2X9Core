package org.l2x9.l2x9core.command.commands;


import org.bukkit.command.CommandSender;
import org.l2x9.l2x9core.Main;
import org.l2x9.l2x9core.command.BaseCommand;
import org.l2x9.l2x9core.util.Utils;

public class UptimeCommand extends BaseCommand {

    public UptimeCommand() {
        super(
                "uptime",
                "/uptime",
                "l2x9core.command.uptime",
                "Show the uptime of the server");
    }

    @Override
    public void execute(CommandSender sender, String[] args, Main plugin) {
        sendMessage(sender, "&6The server has had &r&c" + Utils.getFormattedInterval(System.currentTimeMillis() - Main.startTime) + "&r&6 uptime");
    }
}
