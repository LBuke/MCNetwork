package me.lukebingham.core.region;

import org.bukkit.Location;

/**
 * Created by LukeBingham on 26/03/2017.
 */
public abstract class Region {

    protected Location[] points;

    public Region(Location[] points) {
        this.points = points;
    }

    public Location[] getPoints() {
        return points;
    }
}
