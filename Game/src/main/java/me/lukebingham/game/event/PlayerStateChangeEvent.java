package me.lukebingham.game.event;

import me.lukebingham.core.event.EventFactory;
import me.lukebingham.game.player.GamePlayerState;

/**
 * Created by LukeBingham on 30/03/2017.
 */
public final class PlayerStateChangeEvent extends EventFactory {

    private final GamePlayerState from, to;

    /**
     * Construct a new Event
     */
    public PlayerStateChangeEvent(GamePlayerState from, GamePlayerState to) {
        super(false);

        this.from = from;
        this.to = to;
    }

    /**
     * @return The previous Game state
     */
    public final GamePlayerState getFrom() {
        return from;
    }

    /**
     * @return The newly updated Game state
     */
    public final GamePlayerState getTo() {
        return to;
    }
}
