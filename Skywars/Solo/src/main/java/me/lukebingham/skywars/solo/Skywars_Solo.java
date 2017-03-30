package me.lukebingham.skywars.solo;

import me.lukebingham.core.module.Module;
import me.lukebingham.core.module.PluginState;
import me.lukebingham.core.util.ServerType;
import me.lukebingham.game.type.SoloMode;
import me.lukebingham.skywars.Skywars;

/**
 * Created by LukeBingham on 29/03/2017.
 */
@Module(version = "1.0.0-SNAPSHOT", state = PluginState.PRE_ALPHA, type = ServerType.SKYWARS)
public class Skywars_Solo extends Skywars<SoloMode> implements SoloMode {

    /**
     * @return Name of the plugin
     */
    @Override
    public String getPluginName() {
        return getGameName() + "-" + getMode().getName();
    }

    /**
     * @return Max player slots for the current {@link me.lukebingham.game.Game}
     */
    @Override
    public int getMaxPlayers() {
        return 0;
    }

    /**
     * @return {@link me.lukebingham.game.type.PlayMode} of the current {@link me.lukebingham.game.Game}
     */
    @Override
    public SoloMode getMode() {
        return this;
    }
}
