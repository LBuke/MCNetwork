package me.lukebingham.game.map;

import com.google.common.collect.Lists;
import me.lukebingham.core.util.WorldUtil;
import me.lukebingham.database.Database;
import me.lukebingham.game.Game;
import me.lukebingham.game.GamePlatform;
import me.lukebingham.game.type.PlayMode;
import me.lukebingham.util.FileUtil;
import me.lukebingham.util.RandomUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by LukeBingham on 08/04/2017.
 */
public final class MapManager<Mode extends PlayMode> {

    private final List<Map> maps;
    private final GamePlatform<Mode> platform;

    public MapManager(GamePlatform<Mode> platform, Game<Mode> game, Database database) {
        this.platform = platform;
        this.maps = Lists.newArrayList();
        MapDAO<Mode> dao = new MapDAO<>(game);
        dao.fetch(database, maps::addAll);
    }

    public Map getRandomMap() {
        return maps.get(RandomUtil.getRandomInt(maps.size()));
    }

    public void loadMap(Map map) {
        try {
            File container = platform.getServer().getWorldContainer();
            File source = new File(container.getCanonicalPath() + "/../../maps/" + platform.getGameType() + "/" + map.getName() + ".zip");
            File output = new File(container, map.getName());
            FileUtil.unzip(source, output);

            YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(output, "data.yml"));
            WorldUtil.generateWorld(map.getName(), World.Environment.valueOf(config.getString("environment")), false);
            map.setConfig(config);

            MapLoadEvent event = new MapLoadEvent(map);
            Bukkit.getPluginManager().callEvent(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
