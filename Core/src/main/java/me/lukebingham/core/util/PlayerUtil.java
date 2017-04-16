package me.lukebingham.core.util;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Created by LukeBingham on 16/04/2017.
 */
public class PlayerUtil {

    public static void sendActionBar(Player p, String msg) {
        IChatBaseComponent baseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
        PacketPlayOutChat packet = new PacketPlayOutChat(baseComponent, (byte) 2);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }
}
