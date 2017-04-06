package me.lukebingham.skywars.server;

import me.lukebingham.core.server.CoreServerComponent;
import me.lukebingham.core.util.ServerUtil;
import me.lukebingham.database.Database;
import me.lukebingham.game.type.PlayMode;
import me.lukebingham.redis.JedisModule;
import me.lukebingham.redis.MessageListener;
import me.lukebingham.redis.message.ServerCreatedMessage;
import me.lukebingham.skywars.Skywars;
import me.lukebingham.util.ServerType;

/**
 * Created by LukeBingham on 03/04/2017.
 */
public class ServerComponent<Mode extends PlayMode> extends CoreServerComponent<Skywars<Mode>> implements MessageListener<ServerCreatedMessage> {

    public ServerComponent(Skywars<Mode> skywars, Database database, JedisModule jedisModule) {
        super(skywars, database, jedisModule);
    }

    @Override
    public final void onReceive(String sender, ServerCreatedMessage message) {
        if(message.getServerType() == ServerType.LOBBY) {
            ServerUtil.logDebug("A new " + message.getServerType().name() + " was created with the ID of " + message.getServerId());
        }
    }
}
