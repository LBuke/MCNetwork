package me.lukebingham.core.event;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

/**
 * Created by LukeBingham on 23/03/2017.
 */
public abstract class EventFactory extends Event {
    private static final HandlerList handlers = new HandlerList();

    public EventFactory(boolean async) {
        super(async);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
