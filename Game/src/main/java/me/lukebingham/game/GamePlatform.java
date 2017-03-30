package me.lukebingham.game;

import me.lukebingham.core.CorePlugin;
import me.lukebingham.core.profile.CoreProfile;
import me.lukebingham.core.util.ServerType;
import me.lukebingham.game.event.GameStateChangeEvent;
import me.lukebingham.game.player.GamePlayerManager;
import me.lukebingham.game.type.PlayMode;
import me.lukebingham.game.util.GameState;
import org.bukkit.Bukkit;

/**
 * Created by LukeBingham on 29/03/2017.
 */
public abstract class GamePlatform<Mode extends PlayMode> extends CorePlugin<CoreProfile> implements Game<Mode> {

    private GameState gameState;

    protected GamePlayerManager gamePlayerManager;

    /**
     * This method is fired when the plugin starts.
     */
    @Override
    protected void load() {
        gamePlayerManager = new GamePlayerManager();
    }

    /**
     * This method is fired before the plugin disables
     */
    @Override
    protected void unload() {

    }

    /**
     * This {@link me.lukebingham.core.util.ServerType} will be referenced as GameType.
     * @return The server type
     */
    @Override
    public ServerType getGameType() {
        return getModule().type();
    }

    /**
     * @return The {@link me.lukebingham.game.util.GameState} of the {@link me.lukebingham.game.Game}
     */
    @Override
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Set the {@link me.lukebingham.game.util.GameState} of the {@link me.lukebingham.game.Game}
     * @param gameState The GameState
     */
    @Override
    public void setGameState(GameState gameState) {
        if(this.gameState == gameState) return;
        GameStateChangeEvent event = new GameStateChangeEvent(gameState, this.gameState);
        this.gameState = gameState;
        Bukkit.getPluginManager().callEvent(event);
    }
}
