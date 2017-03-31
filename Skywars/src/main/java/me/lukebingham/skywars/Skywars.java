package me.lukebingham.skywars;

import me.lukebingham.core.util.ServerUtil;
import me.lukebingham.game.GamePlatform;
import me.lukebingham.game.type.PlayMode;
import me.lukebingham.skywars.profile.ProfileComponent;

/**
 * Created by LukeBingham on 29/03/2017.
 */
public abstract class Skywars<Mode extends PlayMode> extends GamePlatform<Mode> {

    /**
     * This method is fired when the plugin starts.
     */
    @Override
    protected void load() {
        super.load();

        //Components
        ProfileComponent profileComponent = new ProfileComponent(this, super.profileManager);
        ServerUtil.registerComponent(profileComponent);
    }

    /**
     * @return Name of the current {@link me.lukebingham.game.Game}
     */
    @Override
    public String getGameName() {
        return "Skywars";
    }
}
