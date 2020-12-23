package org.l2x9.l2x9core.listeners;

import io.netty.channel.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.l2x9.l2x9core.api.PacketReadEvent;
import org.l2x9.l2x9core.api.PacketWriteEvent;

public class PacketInject implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        inject(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        detach(player);
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        Player player = event.getPlayer();
        detach(player);
    }

    private void inject(Player player) {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object packet) {
                try {
                    PacketReadEvent packetReadEvent = new PacketReadEvent(player, ctx, packet);
                    Bukkit.getServer().getPluginManager().callEvent(packetReadEvent);
                    if (packetReadEvent.isCancelled()) {
                        return;
                    }
                    super.channelRead(ctx, packet);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) {
                try {
                    PacketWriteEvent packetWriteEvent = new PacketWriteEvent(player, ctx, promise, packet);
                    Bukkit.getServer().getPluginManager().callEvent(packetWriteEvent);
                    if (packetWriteEvent.isCancelled()) {
                        return;
                    }
                    super.write(ctx, packet, promise);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        };
            Channel channel = getChannel(player);

            ChannelPipeline pipeline = channel.pipeline();
            pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
    }

        private void detach (Player player){
            try {
                Channel channel = getChannel(player);

                channel.eventLoop().submit(() -> {
                    channel.pipeline().remove(player.getName());
                    return null;
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private Channel getChannel (Player player){
            try {
                Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + getBukkitVersion() + ".entity.CraftPlayer");
                Object object = craftPlayerClass.cast(player).getClass().getMethod("getHandle").invoke(craftPlayerClass.cast(player));
                Object playerConnection = object.getClass().getField("playerConnection").get(object);
                Object networkManager = playerConnection.getClass().getField("networkManager").get(playerConnection);
                return (Channel) (networkManager.getClass().getField("channel").get(networkManager));
            } catch (Exception exception) {
                exception.printStackTrace();
                return null;
            }
        }

        private String getBukkitVersion () {
            try {
                return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                arrayIndexOutOfBoundsException.printStackTrace();
                return "Could not find version";
            }
        }
    }
