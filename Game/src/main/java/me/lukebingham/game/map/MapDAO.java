package me.lukebingham.game.map;

import com.google.common.collect.Sets;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import me.lukebingham.database.Database;
import me.lukebingham.database.dao.DAO;
import me.lukebingham.game.Game;
import me.lukebingham.game.type.PlayMode;
import me.lukebingham.util.Callback;

import java.util.Set;

/**
 * Created by LukeBingham on 08/04/2017.
 */
public final class MapDAO<Mode extends PlayMode> extends DAO<Set<Map>> {

    private final Game<Mode> game;

    /**
     * Construct a new DAO
     */
    public MapDAO(Game<Mode> game) {
        super("Games", "maps");
        this.game = game;
    }

    /**
     * Find and grab database from the specified database.
     *
     * @param database
     * @param callback
     */
    @Override
    public final void fetch(Database database, Callback<Set<Map>> callback) {
        if(database == null || game == null) return;
        DBCursor cursor = database.getCollection(super.db, super.coll).find(new BasicDBObject("game", game.getGameName() + "-" + game.getMode().getModeName()));
        if(!cursor.hasNext()) callback.call(Sets.newHashSet());
        BasicDBList list = (BasicDBList) cursor.next().get("maps");
        Set<Map> maps = Sets.newHashSet();
        for(Object object : list) {
            if(!(object instanceof String)) continue;
            maps.add(new Map((String) object));
        }

        callback.call(maps);
    }

    /**
     * Save / Update a specific entry.
     *
     * @param database
     * @param callback
     */
    @Override
    public final void save(Database database, Callback<Set<Map>> callback) {

    }
}
