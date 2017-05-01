package me.lukebingham.core.packet;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

/**
 * Created by LukeBingham on 29/04/2017.
 */
public abstract class PacketHandler {

    private boolean cancelled;
    private final PacketType packetType;

    public PacketHandler(PacketType packetType) {
        this.packetType = packetType;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public PacketType getPacketType() {
        return packetType;
    }

    public abstract void handle(Player player, Packet<?> packet);

    protected <T> T getField(String field) {
        try {
            Field f = this.packetType.getPacketClass().getDeclaredField(field);
            if(!f.isAccessible()) f.setAccessible(true);
            return (T) f.get(this.packetType.getPacketClass());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
