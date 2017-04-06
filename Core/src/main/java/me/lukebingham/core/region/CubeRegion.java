package me.lukebingham.core.region;

import com.google.common.collect.Lists;
import me.lukebingham.util.DoubleCallback;
import me.lukebingham.core.util.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * Created by LukeBingham on 26/03/2017.
 */
public abstract class CubeRegion extends Region {

    /**
     * @param pointA This is the highest point & x,y = positive
     * @param pointB This is the lowest point & x,y = negative
     */
    public CubeRegion(Location pointA, Location pointB) {
        super(new Location[] { pointA, pointB });
    }

    public final void getList(int index, boolean barrier, DoubleCallback<ArrayList<Location>, Boolean> callback) {
        new BukkitRunnable() {
            @Override public void run() {
                ArrayList<Location> temp = Lists.newArrayList();
                boolean done = false;

                for (int y = (index > -1 ? index : 0) + points[1].getBlockY(); y <= points[0].getBlockZ(); y++) {
                    for (int x = points[1].getBlockX(); x <= points[0].getBlockX(); x++) {
                        for (int z = points[1].getBlockZ(); z <= points[0].getBlockZ(); z++) {
                            Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
                            if (loc.getBlock().getType() == Material.AIR || (barrier && loc.getBlock().getType() == Material.BARRIER)) continue;
                            temp.add(loc);
                        }
                    }
                    done = y >= points[0].getBlockY();
                    if (index > -1) break;
                }

                callback.call(temp, done);
            }
        }.runTaskAsynchronously(ServerUtil.getJavaPlugin());
    }
}
