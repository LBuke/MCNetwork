package me.lukebingham.core.packet;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_11_R1.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Created by LukeBingham on 19/03/2017.
 */
public class PacketHandler extends ChannelDuplexHandler {
    private final Player player;

    private PacketHandler(Player player) {
        this.player = player;
    }

    public static void hook(Player player) {
        final Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
        channel.pipeline().addBefore("packet_handler", "network", new PacketHandler(player));
    }

    public static void unHook(Player player){
        final Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
        channel.eventLoop().submit(() -> channel.pipeline().remove("network"));
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        PacketEvent event = new PacketEvent(player, PacketEvent.PacketType.OUTWARDS, (Packet) msg);
        Bukkit.getPluginManager().callEvent(event);

        if(!event.isCancelled())
            super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PacketEvent event = new PacketEvent(player, PacketEvent.PacketType.INWARDS, (Packet) msg);
        Bukkit.getPluginManager().callEvent(event);

        if(!event.isCancelled())
            super.channelRead(ctx, msg);
    }

}
