package me.lukebingham.game.type;

/**
 * Created by LukeBingham on 29/03/2017.
 */
public interface SoloMode extends PlayMode {

    /**
     * @return Name of the current {@link me.lukebingham.game.type.PlayMode}
     */
    @Override default String getModeName() {
        return "Solo";
    }
}
