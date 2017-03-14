package me.lukebingham.core.util.factory;

/**
 * Created by LukeBingham on 24/02/2017.
 */
public abstract class Factory<T> {
    protected T object;

    public abstract T build();
}
