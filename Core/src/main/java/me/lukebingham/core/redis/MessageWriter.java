package me.lukebingham.core.redis;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by LukeBingham on 16/03/2017.
 */
public class MessageWriter {

    private String serverName;
    private Gson gson = new Gson();
    private JedisPool pool;

    public MessageWriter(String serverName, JedisPool pool) {
        this.serverName = serverName;
        this.pool = pool;
    }

    public void publishPacket(Object message, String recipient) {
        JsonObject label = new JsonObject();
        label.addProperty("name", message.getClass().getName());
        label.addProperty("sender", serverName);
        label.addProperty("recipient", recipient);
        label.add("content", gson.toJsonTree(message));

        try (Jedis jedis = pool.getResource()) {
            jedis.publish("network", label.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
