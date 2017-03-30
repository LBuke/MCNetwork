package me.lukebingham.skywars;

import me.lukebingham.game.GamePlatform;
import me.lukebingham.game.type.PlayMode;

/**
 * Created by LukeBingham on 29/03/2017.
 */
public abstract class Skywars<Mode extends PlayMode> extends GamePlatform<Mode> {

    /**
     * @return Name of the current {@link me.lukebingham.game.Game}
     */
    @Override
    public String getGameName() {
        return "Skywars";
    }
}
