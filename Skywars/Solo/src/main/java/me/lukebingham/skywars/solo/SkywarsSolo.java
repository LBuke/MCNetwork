package me.lukebingham.skywars.solo;

import me.lukebingham.core.module.Module;
import me.lukebingham.core.module.PluginState;
import me.lukebingham.core.util.ServerType;
import me.lukebingham.core.util.ServerUtil;
import me.lukebingham.game.combat.indicator.DamageIndicator;
import me.lukebingham.game.combat.indicator.PlayerDamageIndicator;
import me.lukebingham.game.type.SoloMode;
import me.lukebingham.skywars.Skywars;

/**
 * Created by LukeBingham on 29/03/2017.
 */
@Module(version = "1.0.0-SNAPSHOT", state = PluginState.PRE_ALPHA, type = ServerType.SKYWARS)
public class SkywarsSolo extends Skywars<SoloMode> implements SoloMode {

    /**
     * This method is fired when the plugin starts.
     */
    @Override
    protected void load() {
        super.load();

        //Components
        PlayerDamageIndicator damageIndicator = new PlayerDamageIndicator(this);
        ServerUtil.registerComponent(damageIndicator);
    }

    /**
     * @return Name of the plugin
     */
    @Override
    public String getPluginName() {
        return getGameName() + "-" + getModeName();
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
