package me.lukebingham.core.cosmetic.gadget.event;

import me.lukebingham.core.cosmetic.gadget.Gadget;
import me.lukebingham.core.event.EventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * Created by LukeBingham on 23/03/2017.
 */
public final class PlayerGadgetEvent extends EventFactory implements Cancellable {

    private boolean cancelled;

    private final Player player;
    private final Gadget gadget;

    public PlayerGadgetEvent(boolean async, Player player, Gadget gadget) {
        super(async);
        this.player = player;
        this.gadget = gadget;
    }

    public final Player getPlayer() {
        return player;
    }

    public final Gadget getGadget() {
        return gadget;
    }

    /**
     * Gets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins
     *
     * @return true if this event is cancelled
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins.
     *
     * @param cancel true if you wish to cancel this event
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
