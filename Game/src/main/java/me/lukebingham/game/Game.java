package me.lukebingham.game;

import me.lukebingham.core.util.ServerType;
import me.lukebingham.game.player.GamePlayerManager;
import me.lukebingham.game.type.PlayMode;
import me.lukebingham.game.util.GameState;

/**
 * Created by LukeBingham on 29/03/2017.
 */
public interface Game<Mode extends PlayMode> {

    /**
     * @return Name of the current {@link me.lukebingham.game.Game}
     */
    String getGameName();

    /**
     * @return {@link me.lukebingham.game.type.PlayMode} of the current {@link me.lukebingham.game.Game}
     */
    Mode getMode();

    /**
     * This {@link me.lukebingham.core.util.ServerType} will be referenced as GameType.
     * @return The server type
     */
    ServerType getGameType();

    /**
     * @return The {@link me.lukebingham.game.util.GameState} of the {@link me.lukebingham.game.Game}
     */
    GameState getGameState();

    /**
     * Set the {@link me.lukebingham.game.util.GameState} of the {@link me.lukebingham.game.Game}
     * @param gameState The GameState
     */
    void setGameState(GameState gameState);
}
