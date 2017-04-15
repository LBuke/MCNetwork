package me.lukebingham.game.map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.lukebingham.core.util.BlockData;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.List;

/**
 * Created by LukeBingham on 08/04/2017.
 */
public final class Map {

    private final String name;
    private final List<String> authors;
    private final HashMap<BlockData, List<Location>> data;

    private YamlConfiguration config;

    public Map(String name) {
        this.name = name;
        this.authors = Lists.newArrayList();
        this.data = Maps.newHashMap();
    }

    public String getName() {
        return name;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public HashMap<BlockData, List<Location>> getData() {
        return data;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void setConfig(YamlConfiguration config) {
        this.config = config;
    }
}
