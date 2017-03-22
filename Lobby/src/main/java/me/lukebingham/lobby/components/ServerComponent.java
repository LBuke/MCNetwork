package me.lukebingham.lobby.components;

import com.mongodb.BasicDBObject;
import me.lukebingham.core.database.Database;
import me.lukebingham.core.module.Module;
import me.lukebingham.core.redis.JedisModule;
import me.lukebingham.core.redis.MessageListener;
import me.lukebingham.core.redis.message.RemoveServerMessage;
import me.lukebingham.core.redis.message.ServerCreatedMessage;
import me.lukebingham.core.util.Component;
import me.lukebingham.core.util.ServerType;
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

    private final Database database;
    private final JedisModule jedisModule;

    private int serverId = -1;
    private Module module = null;

    public ServerComponent(Database database, JedisModule jedisModule) {
        this.database = database;
        this.jedisModule = jedisModule;

        if (this.module == null && Lobby.class.isAnnotationPresent(Module.class))
            this.module = Lobby.class.getAnnotation(Module.class);

        BasicDBObject dbObject = (BasicDBObject) database.getCollection(db, collection).findOne(new BasicDBObject("port", Bukkit.getPort()));
        if(dbObject == null) {
            if(module.type() == ServerType.LOBBY) {
                this.serverId = 1;
                return;
            }

            ServerUtil.logError("Server port cannot be found in database.");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
            return;
        }

        this.serverId = dbObject.getInt("serverId");
    }

    @Override
    public void onDisable() {
        if(this.module.type() == ServerType.LOBBY && this.serverId == 1) return;

        this.database.getCollection(this.db, this.collection).remove(
                new BasicDBObject("type", this.module.type().name()).append("serverId", this.serverId));
        this.jedisModule.sendMessage(new RemoveServerMessage(this.module.type(), this.serverId, Bukkit.getPort()), "OPERATOR");
    }

    @EventHandler
    protected void onListPing(ServerListPingEvent event) {
        if (this.module == null && Lobby.class.isAnnotationPresent(Module.class))
            this.module = Lobby.class.getAnnotation(Module.class);

        if (this.module == null) {
            Bukkit.shutdown();
            return;
        }

        event.setMotd(ServerUtil.SERVER_NAME + "-" + module.type().name() + "_" + serverId + "-" + this.module.state().name());
    }

    @Override
    public void onReceive(String sender, ServerCreatedMessage message) {
        if(message.getServerType() == ServerType.LOBBY) {
            ServerUtil.logDebug("A new " + message.getServerType().name() + " was created with the ID of " + message.getServerId());
        }
    }
}
