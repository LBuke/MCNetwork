package me.lukebingham.core.graphics;

import com.google.common.collect.Maps;
import me.lukebingham.core.graphics.event.GraphicsChangeEvent;
import me.lukebingham.core.profile.CoreProfile;
import me.lukebingham.core.profile.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by LukeBingham on 26/03/2017.
 */
public class GraphicsManager {

    private HashMap<GraphicsType, Graphics> graphicsMap;
    private boolean enabled = true;

    public GraphicsManager() {
        this.graphicsMap = Maps.newHashMap();

        for(GraphicsType type : GraphicsType.values()) {
            ClientGraphics graphic = null;
            try {
                graphic = type.clazz.newInstance();
            }
            catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                enabled = false;
            }

            if(graphic == null) continue;
            graphic.init();
            graphicsMap.put(type, graphic);
        }
    }

    public HashMap<GraphicsType, Graphics> getGraphicsMap() {
        return graphicsMap;
    }

    public void changeGraphics(CoreProfile profile, Player player, GraphicsType graphicsType) {
        if(!enabled) return;
        if(profile == null) return;
        GraphicsChangeEvent event = new GraphicsChangeEvent(player, graphicsType, graphicsMap.get(graphicsType));
        Bukkit.getPluginManager().callEvent(event);
        if(!event.isCancelled()) profile.setGraphics(graphicsType.getId());
    }
}
