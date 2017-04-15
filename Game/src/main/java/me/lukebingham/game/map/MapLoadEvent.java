package me.lukebingham.game.map;

import me.lukebingham.core.event.EventFactory;

/**
 * Created by LukeBingham on 08/04/2017.
 */
public class MapLoadEvent extends EventFactory {

    private final Map map;

    /**
     * Construct a new Event
     */
    public MapLoadEvent(Map map) {
        super(false);
        this.map = map;
    }

    public Map getMap() {
        return map;
    }
}
