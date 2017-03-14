package me.lukebingham.core.database.dao;

import me.lukebingham.core.database.Database;
import me.lukebingham.core.util.Callback;

/**
 * Created by LukeBingham on 26/02/2017.
 */
public abstract class DAO<T> {
    public abstract void fetch(Database database, Callback<T> callback);
}
