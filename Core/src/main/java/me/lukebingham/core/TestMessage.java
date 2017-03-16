package me.lukebingham.core;

import me.lukebingham.core.redis.MessageListener;
import me.lukebingham.core.redis.message.CommandMessage;
import me.lukebingham.core.util.ServerUtil;

/**
 * Created by LukeBingham on 16/03/2017.
 */
public class TestMessage implements MessageListener<CommandMessage> {

    @Override
    public void onReceive(String sender, CommandMessage msg) {
        ServerUtil.logDebug("onReceive: " + msg.getCommand());
    }
}
