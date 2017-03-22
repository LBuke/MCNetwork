package me.lukebingham.core.redis.message;

import me.lukebingham.core.redis.JMessage;
import me.lukebingham.core.util.ServerType;

/**
 * Created by LukeBingham on 17/03/2017.
 */
public class RemoveServerMessage implements JMessage {

    private ServerType serverType;
    private int serverId;

    private int port;

    public RemoveServerMessage(ServerType serverType, int serverId, int port) {
        this.serverType = serverType;
        this.serverId = serverId;
        this.port = port;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public int getServerId() {
        return serverId;
    }

    public int getPort() {
        return port;
    }
}
