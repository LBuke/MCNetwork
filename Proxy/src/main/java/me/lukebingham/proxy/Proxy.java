package me.lukebingham.proxy;

import me.lukebingham.redis.JedisModule;
import me.lukebingham.redis.message.RemoveServerMessage;
import me.lukebingham.redis.message.ServerCreatedMessage;
import net.md_5.bungee.api.plugin.Plugin;

import java.net.InetSocketAddress;

/**
 * Created by LukeBingham on 03/04/2017.
 */
public class Proxy extends Plugin {

    @Override
    public void onEnable() {
        //Keep this here in-case default code gets added.
        super.onEnable();

        JedisModule jedis = new JedisModule("Proxy");

        jedis.registerListener(ServerCreatedMessage.class, (sender, message) -> {
            InetSocketAddress socket = new InetSocketAddress(message.getIP(), message.getPort());
            String name = message.getServerType().name() + "_" + message.getServerId();
            getProxy().getServers().put(name, getProxy().constructServerInfo(name, socket, name, false));
        });

        jedis.registerListener(RemoveServerMessage.class, (sender, message) ->
                getProxy().getServers().remove(message.getServerType().name() + "_" + message.getServerId()));
    }

    @Override
    public void onDisable() {
        //Keep this here in-case default code gets added.
        super.onDisable();
    }
}
