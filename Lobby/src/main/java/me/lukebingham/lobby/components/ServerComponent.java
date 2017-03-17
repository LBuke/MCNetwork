package me.lukebingham.lobby.components;

import com.mongodb.BasicDBObject;
import me.lukebingham.core.database.Database;
import me.lukebingham.core.module.Module;
import me.lukebingham.core.redis.MessageListener;
import me.lukebingham.core.redis.message.ServerCreatedMessage;
import me.lukebingham.core.util.Component;
import me.lukebingham.core.util.ServerUtil;
import me.lukebingham.lobby.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Created by LukeBingham on 17/03/2017.
 */
public class ServerComponent implements Component, MessageListener<ServerCreatedMessage> {

    private final String db = "Network", collection = "servers";

    private int serverId = -1;
    private Module module = null;
    private Database database;

    public ServerComponent(Database database) {
        this.database = database;

        if (this.module == null && Lobby.class.isAnnotationPresent(Module.class))
            this.module = Lobby.class.getAnnotation(Module.class);
    }

    @Override
    public void onDisable() {
        this.database.getCollection(this.db, this.collection).remove(
                new BasicDBObject("type", this.module.type().name()).append("serverId", this.serverId));
    }

    @EventHandler
    protected void onListPing(ServerListPingEvent event) {
        if (this.module == null && Lobby.class.isAnnotationPresent(Module.class))
            this.module = Lobby.class.getAnnotation(Module.class);

        if(this.module == null) {
            Bukkit.shutdown();
            return;
        }

        event.setMotd(ServerUtil.SERVER_NAME + "-" + module.type().name() + "_" + serverId + "-" + this.module.state().name());
    }

    @Override
    public void onReceive(String sender, ServerCreatedMessage message) {
        database.getCollection("Network", "servers").insert(new BasicDBObject("type", message.getServerType().name())
                .append("serverId", message.getServerId()).append("port", message.getPort())
                .append("startTime", System.currentTimeMillis()).append("playerCount", 0));
    }
}
