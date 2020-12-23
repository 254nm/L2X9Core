package org.l2x9.l2x9core.listeners.patches;

import net.minecraft.server.v1_12_R1.PacketPlayInFlying;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.l2x9.l2x9core.api.PacketReadEvent;

public class PacketFly implements Listener {
    @EventHandler
    public void onPacket(PacketReadEvent event) {
        if (event.getPacket() instanceof PacketPlayInFlying.PacketPlayInPosition) {
            PacketPlayInFlying.PacketPlayInPosition packet = (PacketPlayInFlying.PacketPlayInPosition) event.getPacket();
            System.out.println(packet.a());
        }
    }
}
