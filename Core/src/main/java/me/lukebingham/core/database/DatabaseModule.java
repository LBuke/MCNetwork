package me.lukebingham.core.database;

import com.mongodb.*;
import me.lukebingham.core.util.C;
import me.lukebingham.core.util.ServerUtil;

/**
 * Created by LukeBingham on 01/03/2017.
 */
public final class DatabaseModule implements Database {

    private final MongoClient mongoClient;

    public DatabaseModule(String host, int port, boolean log) {
        //MongoCredential credential = MongoCredential.createCredential(userName, database, password);
        //mongoClient = new MongoClient(new ServerAddress(host, port), Arrays.asList(credential));
        mongoClient = new MongoClient(new ServerAddress(host, port));
        mongoClient.setWriteConcern(WriteConcern.SAFE);
        if(log) ServerUtil.log(C.DATABASE + "MongoDB Client connected! (" + host + " - " + port + ")");
    }

    @Override
    public final DBCollection getCollection(String database, String collection) {
        DB mongoDatabase = mongoClient.getDB(database);
        return mongoDatabase.getCollection(collection);
    }

    @Override
    public final void close() {
        mongoClient.close();
    }
}
