package org.l2x9.l2x9core.util;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.l2x9.l2x9core.L2X9Core;

import java.util.logging.Logger;

public class SecondPassEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Logger logger;
    private final L2X9Core plugin;

    public SecondPassEvent(Logger logger, L2X9Core l2X9Core) {
        this.logger = logger;
        plugin = l2X9Core;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Logger getLogger() {
        return logger;
    }

    public L2X9Core getPlugin() {
        return plugin;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
