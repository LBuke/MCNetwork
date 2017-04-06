package me.lukebingham.lobby.dao;

import com.mongodb.*;
import me.lukebingham.core.cosmetic.gadget.Gadget;
import me.lukebingham.core.cosmetic.gadget.GadgetData;
import me.lukebingham.core.cosmetic.gadget.GadgetManager;
import me.lukebingham.core.cosmetic.gadget.GadgetType;
import me.lukebingham.database.Database;
import me.lukebingham.database.dao.DAO;
import me.lukebingham.util.Callback;
import me.lukebingham.core.util.rank.Role;
import me.lukebingham.util.Dev;
import me.lukebingham.lobby.profile.LobbyProfile;

import java.util.ArrayList;

/**
 * Created by LukeBingham on 26/02/2017.
 */
public final class GadgetDAO extends DAO<Boolean> {

    private final LobbyProfile profile;

    /**
     * Construct a new DAO
     */
    public GadgetDAO(LobbyProfile profile) {
        super("MCNetwork", "players");
        this.profile = profile;
    }

    /**
     * Find and grab database from the specified database.
     *
     * @param database
     * @param callback
     */
    @Override
    public final void fetch(Database database, Callback<Boolean> callback) {
        if (database == null || profile == null) {
            callback.call(false);
            return;
        }

        DBCollection collection = database.getCollection(db, coll);
        DBCursor cursor = collection.find(new BasicDBObject("uuid", profile.getUniqueId().toString()));
        if (cursor.hasNext()) {
            BasicDBList list = (BasicDBList) cursor.next().get("gadgets");
            for (Object object : list) {
                Gadget gadget = GadgetManager.getGadgetsMap().get(GadgetType.valueOf((String) object));
                if (!profile.getRole().hasRole(Role.MODERATOR) && gadget.getClass().isAnnotationPresent(Dev.class))
                    continue;
                profile.addGadgetData(new GadgetData(gadget));
            }
            list.clear();
        }
        else {
            BasicDBObject document = new BasicDBObject();
            document.put("uuid", profile.getUniqueId().toString());
            document.put("gadgets", new ArrayList<>());
            collection.insert(document);
        }

        callback.call(true);
    }

    /**
     * Save / Update a specific entry.
     *
     * @param database
     * @param callback
     */
    @Override
    public final void save(Database database, Callback<Boolean> callback) {
        if (database == null || profile == null) {
            callback.call(false);
            return;
        }

        DBCollection collection = database.getCollection(db, coll);
        BasicDBList dbList = new BasicDBList();
        profile.getGadgetDataList().forEach(data -> dbList.add(data.getType().toString()));
        BasicDBObject gadgetObject = new BasicDBObject("gadgets", dbList);
        WriteResult result = collection.update(new BasicDBObject("uuid", profile.getUniqueId().toString()), new BasicDBObject("$set", gadgetObject));

        callback.call(result.wasAcknowledged());
    }
}
