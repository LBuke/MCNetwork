package me.lukebingham.core.redis;

/**
 * Created by LukeBingham on 16/03/2017.
 */
public interface MessageListener<T extends JMessage> {
    void onReceive(String sender, T msg);
}
