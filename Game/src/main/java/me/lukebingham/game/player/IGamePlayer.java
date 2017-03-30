package me.lukebingham.game.player;

import org.bukkit.entity.Player;

/**
 * Created by LukeBingham on 30/03/2017.
 */
public interface IGamePlayer {

    /**
     * @return The {@link org.bukkit.Bukkit} version of {@link org.bukkit.entity.Player}
     */
    Player getPlayer();

    /**
     * @return True if the player is spectating
     */
    boolean isSpectator();

    /**
     * Set the {@link IGamePlayer} as a spectator
     */
    void setSpectator();

    /**
     * @return Get the current Player state
     */
    GamePlayerState getGamePlayerState();

    /**
     * Set the {@link IGamePlayer}'s Player state
     * @param gamePlayerState The Player state
     */
    void setGamePlayerState(GamePlayerState gamePlayerState);
}
