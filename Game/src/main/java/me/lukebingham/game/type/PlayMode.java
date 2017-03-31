package me.lukebingham.game.type;

/**
 * Created by LukeBingham on 29/03/2017.
 */
public interface PlayMode {

    /**
     * @return Name of the current {@link me.lukebingham.game.type.PlayMode}
     */
    String getModeName();

    /**
     * @return Max player slots for the current {@link me.lukebingham.game.Game}
     */
    int getMaxPlayers();
}
