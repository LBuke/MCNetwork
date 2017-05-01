package me.lukebingham.core.packet;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

/**
 * Created by LukeBingham on 19/03/2017.
 */
public final class PacketModule extends ChannelDuplexHandler {

    private static final HashMap<PacketType, List<PacketHandler>> handlers;
    static { handlers = Maps.newHashMap(); }

    private final Player player;

    private PacketModule(Player player) {
        this.player = player;
    }

    protected static void hook(Player player) {
        final Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
        channel.pipeline().addBefore("packet_handler", "network", new PacketModule(player));
    }

    protected static void unHook(Player player){
        final Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
        channel.eventLoop().submit(() -> channel.pipeline().remove("network"));
    }

    @Override
    public final void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        Packet<?> packet = (Packet<?>) msg;
        PacketType packetType = PacketType.getByClass(packet.getClass());
        if(handlers.containsKey(packetType)) {
            boolean override = false;
            for(PacketHandler handler : handlers.get(packetType)) {
                if(!override && handler.isCancelled()) override = true;
                handler.handle(this.player, packet);
            }

            if(override) return;
        }

        super.write(ctx, msg, promise);
    }

    @Override
    public final void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Packet<?> packet = (Packet<?>) msg;
        PacketType packetType = PacketType.getByClass(packet.getClass());
        if(handlers.containsKey(packetType)) {
            boolean override = false;
            for(PacketHandler handler : handlers.get(packetType)) {
                if(!override && handler.isCancelled()) override = true;
                handler.handle(this.player, packet);
            }

            if(override) return;
        }

        super.channelRead(ctx, msg);
    }

    public static void addPacketListener(PacketHandler handler) {
        List<PacketHandler> list = handlers.containsKey(handler.getPacketType()) ? handlers.get(handler.getPacketType()) : Lists.newArrayList();
        list.add(handler);

        handlers.put(handler.getPacketType(), list);
    }

    public static void clear() {
        handlers.clear();
    }
}
