package me.lukebingham.core.packet;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by LukeBingham on 19/03/2017.
 */
public class PacketEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private PacketType packetType;
    private Packet packet;
    private Player player;
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

    public PacketType getPacketType() {
        return packetType;
    }

    public Packet getPacket() {
        return packet;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public static enum PacketType {
        INWARDS, OUTWARDS
    }
}
