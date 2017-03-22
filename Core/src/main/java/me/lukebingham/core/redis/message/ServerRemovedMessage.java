package me.lukebingham.core.redis.message;

import me.lukebingham.core.redis.JMessage;
import me.lukebingham.core.util.ServerType;

/**
 * Created by LukeBingham on 17/03/2017.
 */
public class ServerRemovedMessage implements JMessage {

    private ServerType serverType;
    private int serverId;

    public ServerRemovedMessage(ServerType serverType, int serverId) {
        this.serverType = serverType;
        this.serverId = serverId;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public int getServerId() {
        return serverId;
    }
}
