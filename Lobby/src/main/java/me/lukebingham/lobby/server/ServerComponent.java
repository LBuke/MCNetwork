package me.lukebingham.lobby.server;

import me.lukebingham.core.server.CoreServerComponent;
import me.lukebingham.core.util.ServerUtil;
import me.lukebingham.database.Database;
import me.lukebingham.lobby.Lobby;
import me.lukebingham.redis.JedisModule;
import me.lukebingham.redis.MessageListener;
import me.lukebingham.redis.message.ServerCreatedMessage;
import me.lukebingham.util.ServerType;

/**
 * Created by LukeBingham on 17/03/2017.
 */
public final class ServerComponent extends CoreServerComponent<Lobby> implements MessageListener<ServerCreatedMessage> {

    public ServerComponent(Lobby lobby, Database database, JedisModule jedisModule) {
        super(lobby, database, jedisModule);
    }

    @Override
    public final void onReceive(String sender, ServerCreatedMessage message) {
        if(message.getServerType() == ServerType.LOBBY) {
            ServerUtil.logDebug("A new " + message.getServerType().name() + " was created with the ID of " + message.getServerId());
        }
    }
}
