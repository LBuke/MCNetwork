package me.lukebingham.game.map;

import com.google.common.collect.Lists;
import me.lukebingham.core.util.BlockData;
import me.lukebingham.core.util.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;

import java.util.List;

/**
 * Created by LukeBingham on 10/04/2017.
 */
public class MapComponent implements Component {

    @EventHandler
    protected void onMapLoad(MapLoadEvent event) {
        if(event.getMap() == null) return;
        event.getMap().getAuthors().addAll(event.getMap().getConfig().getStringList("authors"));
        for(String key : event.getMap().getConfig().getConfigurationSection("data").getKeys(false)) {
            List<Location> temp = Lists.newArrayList();
            World world = Bukkit.getWorld(event.getMap().getName());
            for(String loc : event.getMap().getConfig().getStringList("data." + key)) {
                String[] xyz = loc.split(";");
                temp.add(new Location(world, Double.valueOf(xyz[0]), Double.valueOf(xyz[1]), Double.valueOf(xyz[2])));
            }

            BlockData blockData = new BlockData(Material.valueOf(key.split(";")[0]), Byte.valueOf(key.split(";")[1]));
            event.getMap().getData().put(blockData, temp);
        }
    }
}
