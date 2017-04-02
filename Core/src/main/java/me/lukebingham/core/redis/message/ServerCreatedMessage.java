package me.lukebingham.core.redis.message;

import me.lukebingham.core.redis.JMessage;
import me.lukebingham.core.util.ServerType;

/**
 * Created by LukeBingham on 17/03/2017.
 */
public final class ServerCreatedMessage implements JMessage {

    private final ServerType serverType;
    private final int serverId;

    private final int port;

    public ServerCreatedMessage(ServerType serverType, int serverId, int port) {
        this.serverType = serverType;
        this.serverId = serverId;
        this.port = port;
    }

    public final ServerType getServerType() {
        return serverType;
    }

    public final int getServerId() {
        return serverId;
    }

    public final int getPort() {
        return port;
    }
}
