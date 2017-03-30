package me.lukebingham.game.event;

import me.lukebingham.core.event.EventFactory;
import me.lukebingham.game.Game;

/**
 * Created by LukeBingham on 29/03/2017.
 */
public class GameStartEvent extends EventFactory {

    private Game<?> game;

    /**
     * Construct a new Event
     */
    public GameStartEvent(Game<?> game) {
        super(false);

        this.game = game;
    }

    /**
     * @return The current {@link me.lukebingham.game.Game}
     */
    public Game<?> getGame() {
        return game;
    }
}
