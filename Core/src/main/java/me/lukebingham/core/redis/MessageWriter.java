package me.lukebingham.core.redis;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by LukeBingham on 16/03/2017.
 */
public final class MessageWriter {

    private final String serverName;
    private final Gson gson = new Gson();
    private final JedisPool pool;

    public MessageWriter(String serverName, JedisPool pool) {
        this.serverName = serverName;
        this.pool = pool;
    }

    public final void publishPacket(Object message, String recipient) {
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
