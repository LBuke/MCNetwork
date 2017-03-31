package me.lukebingham.game.player;

import com.google.common.collect.Sets;

import java.util.HashSet;

/**
 * Created by LukeBingham on 30/03/2017.
 */
public final class GamePlayerManager {

    private final HashSet<IGamePlayer> gamePlayers;

    public GamePlayerManager() {
        this.gamePlayers = Sets.newHashSet();
    }

    /**
     * @return {@link java.util.HashSet} of {@link IGamePlayer}'s
     */
    public final HashSet<IGamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    /**
     * @param gamePlayer The Game Player
     * @return The added Game Player
     */
    public final IGamePlayer addGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayers.add(gamePlayer);
        return gamePlayer;
    }
}
