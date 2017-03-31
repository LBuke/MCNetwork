package me.lukebingham.game.player;

import me.lukebingham.game.event.PlayerStateChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by LukeBingham on 30/03/2017.
 */
public final class GamePlayer implements IGamePlayer {

    private final UUID uniqueId;
    private GamePlayerState gamePlayerState;

    /**
     * Construct a new GamePlayer.
     *
     * @param uniqueId        {@link org.bukkit.entity.Player} unique id
     * @param gamePlayerState The Player state
     */
    public GamePlayer(UUID uniqueId, GamePlayerState gamePlayerState) {
        this.uniqueId = uniqueId;
        this.gamePlayerState = gamePlayerState;
    }

    /**
     * Construct a new GamePlayer.
     *
     * @param uniqueId {@link org.bukkit.entity.Player} unique id
     */
    public GamePlayer(UUID uniqueId) {
        this(uniqueId, GamePlayerState.WAITING);
    }

    /**
     * @return The {@link Bukkit} version of {@link Player}
     */
    @Override
    public final Player getPlayer() {
        return Bukkit.getPlayer(uniqueId);
    }

    /**
     * @return True if the player is spectating
     */
    @Override
    public final boolean isSpectator() {
        return this.gamePlayerState == GamePlayerState.SPECTATING;
    }

    /**
     * Set the {@link IGamePlayer} as a spectator
     */
    @Override
    public final void setSpectator() {
        setGamePlayerState(GamePlayerState.SPECTATING);
    }

    /**
     * @return Get the current Player state
     */
    @Override
    public final GamePlayerState getGamePlayerState() {
        return gamePlayerState;
    }

    /**
     * Set the {@link IGamePlayer}'s Player state
     *
     * @param gamePlayerState The Player state
     */
    @Override
    public final void setGamePlayerState(GamePlayerState gamePlayerState) {
        if (this.gamePlayerState == gamePlayerState) return;
        PlayerStateChangeEvent event = new PlayerStateChangeEvent(this.gamePlayerState, gamePlayerState);
        Bukkit.getPluginManager().callEvent(event);
        this.gamePlayerState = gamePlayerState;
    }
}
