package org.l2x9.l2x9core.command.commands;

import org.bukkit.command.CommandSender;
import org.l2x9.l2x9core.Main;
import org.l2x9.l2x9core.command.BaseCommand;

public class DiscordCommand extends BaseCommand {

    public DiscordCommand() {
        super(
                "discord",
                "/discord",
                "l2x9core.command.discord",
                "Shows a discord link");
    }

    @Override
    public void execute(CommandSender sender, String[] args, Main plugin) {
        sendMessage(sender, plugin.getConfig().getString("Discord"));
    }
}
