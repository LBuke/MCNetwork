package me.lukebingham.core.graphics.event;

import me.lukebingham.core.event.EventFactory;
import me.lukebingham.core.graphics.Graphics;
import me.lukebingham.core.graphics.GraphicsType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by LukeBingham on 26/03/2017.
 */
public final class GraphicsChangeEvent extends EventFactory implements Cancellable {

    private final Player player;
    private final GraphicsType graphicsType;
    private final Graphics graphics;

    private boolean b;

    public GraphicsChangeEvent(Player player, GraphicsType graphicsType, Graphics graphics) {
        super(false);
        this.player = player;
        this.graphicsType = graphicsType;
        this.graphics = graphics;
    }

    public final Player getPlayer() {
        return player;
    }

    public final GraphicsType getGraphicsType() {
        return graphicsType;
    }

    public final Graphics getGraphics() {
        return graphics;
    }

    /**
     * Gets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins
     *
     * @return true if this event is cancelled
     */
    @Override
    public final boolean isCancelled() {
        return b;
    }

    /**
     * Sets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins.
     *
     * @param cancel true if you wish to cancel this event
     */
    @Override
    public final void setCancelled(boolean cancel) {
        b = cancel;
    }
}
