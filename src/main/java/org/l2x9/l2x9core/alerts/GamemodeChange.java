/*
 * ******************************************************
 *  * Copyright (C) 2010-2020 Mason Smith <l2x9.help@gmail.com>
 *  *
 *  * This file is part of L2X9Core.
 *  *
 *  * L2X9Core can not be copied and/or distributed without the express
 *  * permission of Mason Smith
 *  ******************************************************
 */

package org.l2x9.l2x9core.alerts;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.l2x9.l2x9core.L2X9Core;

public class GamemodeChange implements Listener {
    L2X9Core plugin;

    public GamemodeChange(L2X9Core plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent event) {
        if (plugin.discordWebhook.alertsEnabled()) {
            if (plugin.getConfigBoolean("AlertSystem.ChestLagFix")) {
                plugin.discordWebhook.setContent(plugin.getPingRole() + " [GamemodeChange] by " + event.getPlayer().getName() + " to " + event.getNewGameMode().name());
                plugin.discordWebhook.execute();
            }
        }
    }
}
