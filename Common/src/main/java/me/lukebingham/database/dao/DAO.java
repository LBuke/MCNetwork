package me.lukebingham.database.dao;

import me.lukebingham.database.Database;
import me.lukebingham.util.Callback;

/**
 * Created by LukeBingham on 26/02/2017.
 */
public abstract class DAO<T> {

    protected final String db, coll;

    /**
     * Construct a new DAO
     *
     * @param db   Which Database to use
     * @param coll Which Collection to use
     */
    public DAO(String db, String coll) {
        this.db = db;
        this.coll = coll;
    }

    /**
     * Find and grab database from the specified database.
     *
     * @param database
     * @param callback
     */
    public abstract void fetch(Database database, Callback<T> callback);

    /**
     * Save / Update a specific entry.
     *
     * @param database
     * @param callback
     */
    public abstract void save(Database database, Callback<T> callback);
}
