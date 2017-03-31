package me.lukebingham.game.event;

import me.lukebingham.core.event.EventFactory;
import me.lukebingham.game.util.GameState;

/**
 * Created by LukeBingham on 30/03/2017.
 */
public final class GameStateChangeEvent extends EventFactory {

    private final GameState from, to;

    /**
     * Construct a new Event
     */
    public GameStateChangeEvent(GameState from, GameState to) {
        super(false);

        this.from = from;
        this.to = to;
    }

    /**
     * @return The previous Game state
     */
    public final GameState getFrom() {
        return from;
    }

    /**
     * @return The newly updated Game state
     */
    public final GameState getTo() {
        return to;
    }
}
