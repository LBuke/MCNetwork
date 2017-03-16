package me.lukebingham.core.redis.message;

import me.lukebingham.core.redis.JMessage;

/**
 * Created by LukeBingham on 16/03/2017.
 */
public class CommandMessage implements JMessage {

    private String command = "";

    public CommandMessage() {
    }

    public CommandMessage(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
