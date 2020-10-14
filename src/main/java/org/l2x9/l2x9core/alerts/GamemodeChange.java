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
import org.l2x9.l2x9core.Main;

public class GamemodeChange implements Listener {
    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent event) {
        if (Main.getPlugin().discordWebhook.alertsEnabled()) {
            if (Main.getPlugin().getConfigBoolean("AlertSystem.ChestLagFix")) {
                Main.getPlugin().discordWebhook.setContent(Main.getPlugin().getPingRole() + " [GamemodeChange] by " + event.getPlayer().getName() + " to " + event.getNewGameMode().name());
                Main.getPlugin().discordWebhook.execute();
            }
        }
    }
}
