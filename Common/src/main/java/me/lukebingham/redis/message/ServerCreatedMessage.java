package me.lukebingham.redis.message;

import me.lukebingham.redis.JMessage;
import me.lukebingham.util.ServerType;

/**
 * Created by LukeBingham on 17/03/2017.
 */
public final class ServerCreatedMessage implements JMessage {

    private final ServerType serverType;
    private final int serverId;

    private final String ip;
    private final int port;

    public ServerCreatedMessage(ServerType serverType, int serverId, String ip, int port) {
        this.serverType = serverType;
        this.serverId = serverId;
        this.ip = ip;
        this.port = port;
    }

    public final ServerType getServerType() {
        return serverType;
    }

    public final int getServerId() {
        return serverId;
    }

    public String getIP() {
        return ip;
    }

    public final int getPort() {
        return port;
    }
}
