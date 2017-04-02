package me.lukebingham.core.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by LukeBingham on 23/03/2017.
 */
public abstract class EventFactory extends Event {
    private static final HandlerList handlers = new HandlerList();

    /**
     * Construct a new Event
     */
    public EventFactory(boolean async) {
        super(async);
    }

    @Override
    public final HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
