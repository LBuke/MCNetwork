package me.lukebingham.core.region;

import org.bukkit.Location;

/**
 * Created by LukeBingham on 26/03/2017.
 */
public abstract class Region {

    protected final Location[] points;

    public Region(Location[] points) {
        this.points = points;
    }

    public final Location[] getPoints() {
        return points;
    }
}
