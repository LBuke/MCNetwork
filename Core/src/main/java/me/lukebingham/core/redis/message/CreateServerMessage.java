package me.lukebingham.core.redis.message;

import me.lukebingham.core.redis.JMessage;
import me.lukebingham.core.util.ServerType;

/**
 * Created by LukeBingham on 17/03/2017.
 */
public class CreateServerMessage implements JMessage {

    private ServerType serverType;

    public CreateServerMessage(ServerType serverType) {
        this.serverType = serverType;
    }

    public ServerType getServerType() {
        return serverType;
    }
}
