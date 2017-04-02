package me.lukebingham.core.packet;

import com.google.common.collect.Sets;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.lukebingham.core.util.ServerUtil;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashSet;

/**
 * Created by LukeBingham on 19/03/2017.
 */
public final class PacketHandler extends ChannelDuplexHandler {
    private static final HashSet<Class<? extends Packet<?>>> packets;
    static {
        packets = Sets.newHashSet();
    }

    private final Player player;

    private PacketHandler(Player player) {
        this.player = player;
    }

    protected static void hook(Player player) {
        final Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
        channel.pipeline().addBefore("packet_handler", "network", new PacketHandler(player));
    }

    protected static void unHook(Player player){
        final Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
        channel.eventLoop().submit(() -> channel.pipeline().remove("network"));
    }

    @Override
    public final void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        Packet<?> packet = (Packet<?>) msg;
        PacketEvent event = null;
        if(packets.contains(packet.getClass())) {
            event = new PacketEvent(player, PacketEvent.PacketType.OUTWARDS, packet);
            Bukkit.getPluginManager().callEvent(event);
        }

        if(event == null) {
            super.write(ctx, msg, promise);
            return;
        }

        if(!event.isCancelled()) {
            super.write(ctx, msg, promise);
        }
    }

    @Override
    public final void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Packet<?> packet = (Packet<?>) msg;
        PacketEvent event = null;
        if(packets.contains(packet.getClass())) {
            event = new PacketEvent(player, PacketEvent.PacketType.INWARDS, packet);
            Bukkit.getPluginManager().callEvent(event);
        }

        if(event == null) {
            super.channelRead(ctx, msg);
            return;
        }

        if(!event.isCancelled()) {
            super.channelRead(ctx, msg);
        }
    }

    public static void addPacketListener(Class<? extends Packet<?>> packet) {
        if(packets.contains(packet)) return;
        packets.add(packet);
    }

    public static void clear() {
        packets.clear();
    }
}
