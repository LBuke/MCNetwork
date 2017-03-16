package me.lukebingham.lobby.dao;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import me.lukebingham.core.cosmetic.gadget.Gadget;
import me.lukebingham.core.cosmetic.gadget.GadgetData;
import me.lukebingham.core.cosmetic.gadget.GadgetManager;
import me.lukebingham.core.cosmetic.gadget.GadgetType;
import me.lukebingham.core.database.Database;
import me.lukebingham.core.database.dao.DAO;
import me.lukebingham.core.util.Callback;
import me.lukebingham.core.util.Role;
import me.lukebingham.core.util.Test;
import me.lukebingham.lobby.Lobby;
import me.lukebingham.lobby.profile.LobbyProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LukeBingham on 26/02/2017.
 */
public class GadgetDAO extends DAO<Boolean> {

    private LobbyProfile profile;

    public GadgetDAO(LobbyProfile profile) {
        this.profile = profile;
    }

    @Override
    public void fetch(Database database, Callback<Boolean> callback) {
        //TODO
        DBCollection collection = database.getCollection("MCNetwork", "players");
        DBCursor cursor = collection.find(new BasicDBObject("uuid", profile.getUniqueId().toString()));
        if(cursor.hasNext()) {
            BasicDBList list = (BasicDBList) cursor.next().get("gadgets");
            for(Object object : list) {
                Gadget gadget = GadgetManager.getGadgetsMap().get(GadgetType.valueOf((String) object));
                if(!profile.getRole().hasRole(Role.MODERATOR) && gadget.getClass().isAnnotationPresent(Test.class)) continue;
                profile.addGadgetData(new GadgetData(gadget));
            }
            list.clear();
            return;
        }

        BasicDBObject document = new BasicDBObject();
        document.put("uuid", profile.getUniqueId().toString());
        document.put("gadgets", new ArrayList<>());
        collection.insert(document);

        callback.call(true);
    }
}
