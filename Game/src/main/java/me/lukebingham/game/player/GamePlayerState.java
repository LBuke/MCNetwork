package me.lukebingham.game.player;

/**
 * Created by LukeBingham on 30/03/2017.
 */
public enum GamePlayerState {

    /**
     * This value is used when the game
     * is either in the process of
     * Loading or Starting or in
     * a Waiting state.
     */
    WAITING,

    /**
     * This value is used when the player
     * is playing the game.
     */
    PLAYING,

    /**
     * This value is used when the player
     * is no longer playing the game but
     * is still in the game server.
     */
    SPECTATING,
    ;
}
