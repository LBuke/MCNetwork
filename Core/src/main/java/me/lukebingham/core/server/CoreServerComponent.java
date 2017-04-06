package me.lukebingham.core.server;

import com.mongodb.BasicDBObject;
import me.lukebingham.core.CorePlugin;
import me.lukebingham.core.module.Module;
import me.lukebingham.core.util.Component;
import me.lukebingham.core.util.ServerUtil;
import me.lukebingham.database.Database;
import me.lukebingham.redis.JedisModule;
import me.lukebingham.redis.message.RemoveServerMessage;
import me.lukebingham.util.ServerType;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Created by LukeBingham on 03/04/2017.
 */
public class CoreServerComponent<T extends CorePlugin<?>> implements Component {

    private final String db = "Network", collection = "servers";

    private final T plugin;
    private final Database database;
    private final JedisModule jedisModule;

    private int serverId = -1;
    private Module module = null;

    public CoreServerComponent(T plugin, Database database, JedisModule jedisModule) {
        this.plugin = plugin;
        this.database = database;
        this.jedisModule = jedisModule;

        if (this.module == null && plugin.getClass().isAnnotationPresent(Module.class))
            this.module = plugin.getClass().getAnnotation(Module.class);

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
    public final void onDisable() {
        if(this.module.type() == ServerType.LOBBY && this.serverId == 1) return;

        this.database.getCollection(this.db, this.collection).remove(
                new BasicDBObject("type", this.module.type().name()).append("serverId", this.serverId));
        this.jedisModule.sendMessage(new RemoveServerMessage(this.module.type(), this.serverId, Bukkit.getPort()), "OPERATOR");
    }

    @EventHandler
    protected final void onListPing(ServerListPingEvent event) {
        if (this.module == null && plugin.getClass().isAnnotationPresent(Module.class))
            this.module = plugin.getClass().getAnnotation(Module.class);

        if (this.module == null) {
            Bukkit.shutdown();
            return;
        }

        event.setMotd(ServerUtil.SERVER_NAME + "-" + module.type().name() + "_" + serverId + "-" + this.module.state().name());
    }
}
