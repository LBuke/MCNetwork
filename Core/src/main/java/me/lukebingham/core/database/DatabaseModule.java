package me.lukebingham.core.database;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import me.lukebingham.core.util.C;
import me.lukebingham.core.util.ServerUtil;

/**
 * Created by LukeBingham on 01/03/2017.
 */
public final class DatabaseModule implements Database {

    private final MongoClient mongoClient;

    public DatabaseModule(String host, int port) {
        //MongoCredential credential = MongoCredential.createCredential(userName, database, password);
        //mongoClient = new MongoClient(new ServerAddress(host, port), Arrays.asList(credential));
        mongoClient = new MongoClient(new ServerAddress(host, port));
        ServerUtil.log(C.DATABASE + "MongoDB Client connected! (" + host + " - " + port + ")");
    }

    @Override
    public DBCollection getCollection(String database, String collection) {
        DB mongoDatabase = mongoClient.getDB(database);
        return mongoDatabase.getCollection(collection);
    }
}
