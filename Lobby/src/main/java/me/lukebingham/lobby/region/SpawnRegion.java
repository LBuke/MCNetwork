package me.lukebingham.lobby.region;

import me.lukebingham.core.region.CubeRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * Created by LukeBingham on 26/03/2017.
 */
public final class SpawnRegion extends CubeRegion {

    private static final Location pointA = new Location(Bukkit.getWorld("world"), 119, 79, 125),
                                  pointB = new Location(Bukkit.getWorld("world"), -116, 10, -132);

    public SpawnRegion() {
        super(pointA, pointB);
    }
}
