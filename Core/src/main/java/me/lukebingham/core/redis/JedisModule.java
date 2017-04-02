package me.lukebingham.core.redis;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import me.lukebingham.core.util.Callback;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.List;

/**
 * Created by LukeBingham on 16/03/2017.
 */
public class JedisModule {

    private final Gson gson = new Gson();
    private JedisPool jedisPool;
    private MessageReader reader;
    private MessageWriter writer;

    protected static HashMap<Class<? extends JMessage>, List<MessageListener>> listeners;

    static {
        listeners = new HashMap<>();
    }

    public JedisModule() {}

    public JedisModule(String serverName) {
        this.jedisPool = new JedisPool(new JedisPoolConfig(), "localhost", 6379, 0, null);

        try (Jedis jedis = jedisPool.getResource()) {
            jedis.ping();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        reader = new MessageReader(serverName);
        writer = new MessageWriter(serverName, jedisPool);

        new Thread(() -> {
            try (Jedis j = getJedisPool().getResource()) {
                j.subscribe(reader, "network");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public final void connect(String serverName) {
        this.jedisPool = new JedisPool(new JedisPoolConfig(), "localhost", 6379, 0, null);

        try (Jedis jedis = jedisPool.getResource()) {
            jedis.ping();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        reader = new MessageReader(serverName);
        writer = new MessageWriter(serverName, jedisPool);

        new Thread(() -> {
            try (Jedis j = getJedisPool().getResource()) {
                j.subscribe(reader, "network");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public final void sendMessage(Object message, String server) {
        writer.publishPacket(message, server);
    }

    public final void disable() {
        if (reader.isSubscribed())
            reader.unsubscribe();
        jedisPool.destroy();
    }

    public final JedisPool getJedisPool() {
        return jedisPool;
    }

    public final <T extends JMessage> void registerListener(Class<T> msg, MessageListener<T> listener) {
        if (listeners.containsKey(msg)) {
            listeners.get(msg).add(listener);
        }
        else {
            List<MessageListener> list = Lists.newArrayList();
            list.add(listener);
            listeners.put(msg, list);
        }
    }
}
