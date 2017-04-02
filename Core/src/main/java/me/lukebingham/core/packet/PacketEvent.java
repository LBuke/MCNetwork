package me.lukebingham.core.packet;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by LukeBingham on 19/03/2017.
 */
public final class PacketEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final PacketType packetType;
    private final Packet packet;
    private final Player player;
    private boolean cancelled = false;

    public PacketEvent(Player player, PacketType packetType, Packet packet) {
        super(true);

        this.packetType = packetType;
        this.packet = packet;
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public final PacketType getPacketType() {
        return packetType;
    }

    public final Packet getPacket() {
        return packet;
    }

    public final Player getPlayer() {
        return player;
    }

    @Override
    public final boolean isCancelled() {
        return cancelled;
    }

    @Override
    public final void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public enum PacketType {
        INWARDS, OUTWARDS
    }
}
