package org.l2x9.l2x9core.api;

import io.netty.channel.ChannelHandlerContext;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PacketReadEvent extends Event implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private boolean cancelled;
    Player player;
    ChannelHandlerContext channelHandlerContext;
    Object packet;

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Player getPlayer() {
        return player;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    public Object getPacket() {
        return packet;
    }

    public PacketReadEvent(Player player, ChannelHandlerContext ctx, Object packet) {
        this.player = player;
        channelHandlerContext = ctx;
        this.packet = packet;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
