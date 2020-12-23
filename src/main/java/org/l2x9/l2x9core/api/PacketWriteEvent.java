package org.l2x9.l2x9core.api;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PacketWriteEvent extends Event implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    Player player;
    ChannelHandlerContext channelHandlerContext;
    Object packet;
    ChannelPromise promise;
    private boolean cancelled;

    public PacketWriteEvent(Player player, ChannelHandlerContext ctx, ChannelPromise promise, Object packet) {
        this.player = player;
        channelHandlerContext = ctx;
        this.packet = packet;
        this.promise = promise;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Player getPlayer() {
        return player;
    }

    public ChannelPromise getPromise() {
        return promise;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    public Object getPacket() {
        return packet;
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
