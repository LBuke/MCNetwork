package me.lukebingham.core.redis.message;

import me.lukebingham.core.redis.JMessage;
import me.lukebingham.core.util.ServerType;

/**
 * Created by LukeBingham on 17/03/2017.
 */
public final class ServerRemovedMessage implements JMessage {

    private final ServerType serverType;
    private final int serverId;

    public ServerRemovedMessage(ServerType serverType, int serverId) {
        this.serverType = serverType;
        this.serverId = serverId;
    }

    public final ServerType getServerType() {
        return serverType;
    }

    public final int getServerId() {
        return serverId;
    }
}
