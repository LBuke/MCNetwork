package me.lukebingham.core.packet;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInSettings;
import net.minecraft.server.v1_8_R3.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;

/**
 * Created by LukeBingham on 30/04/2017.
 */
public enum PacketType {

    IN_SETTINGS(PacketPlayInSettings.class),
    IN_STEER_VEHICLE(PacketPlayInSteerVehicle.class),
    TEST(PacketPlayOutAttachEntity.class),
    ;

    private final Class<? extends Packet<?>> packet;

    PacketType(Class<? extends Packet<?>> packet) {
        this.packet = packet;
    }

    public Class<? extends Packet<?>> getPacketClass() {
        return packet;
    }

    public static PacketType getByClass(Class<? extends Packet> packetClass) {
        for(PacketType type : values()) {
            if(packetClass.equals(type.packet)) return type;
        }

        return null;
    }
}
