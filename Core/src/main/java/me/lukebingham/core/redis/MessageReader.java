package me.lukebingham.core.redis;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import redis.clients.jedis.JedisPubSub;

import java.util.List;

/**
 * Created by LukeBingham on 16/03/2017.
 */
public class MessageReader extends JedisPubSub {

    private String serverName;
    private Gson gson = new Gson();
    private JsonParser jsonParser = new JsonParser();

    public MessageReader(String name) {
        this.serverName = name;
    }

    @Override
    public void onMessage(String channel, String message) {
        if (!isValid(message)) return;

        try {
            JsonObject label = (JsonObject) jsonParser.parse(message);
            String messageName = label.get("name").getAsString();
            String sender = label.get("sender").getAsString();
            String recipient = label.get("recipient").getAsString();

            if (!recipient.equals("all") && sender.equals(serverName)) return;
            if (recipient.equalsIgnoreCase("all") || recipient.equalsIgnoreCase(serverName)) {
                Class<? extends JMessage> messageClass = (Class<? extends JMessage>) Class.forName(messageName);
                JMessage msg = gson.fromJson(label.getAsJsonObject("content"), messageClass);

                List<MessageListener> listenerList = JedisModule.listeners.get(messageClass);
                if (listenerList != null) listenerList.forEach(c -> c.onReceive(sender, msg));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPMessage(String s, String s1, String s2) {

    }

    @Override
    public void onSubscribe(String s, int i) {

    }

    @Override
    public void onUnsubscribe(String s, int i) {

    }

    @Override
    public void onPUnsubscribe(String s, int i) {

    }

    @Override
    public void onPSubscribe(String s, int i) {

    }

    private boolean isValid(String str) {
        try {
            jsonParser.parse(str);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
