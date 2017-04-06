package me.lukebingham.skywars;

import me.lukebingham.core.util.ServerUtil;
import me.lukebingham.game.GamePlatform;
import me.lukebingham.game.type.PlayMode;
import me.lukebingham.skywars.profile.ProfileComponent;
import me.lukebingham.skywars.server.ServerComponent;

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
        ServerComponent<Mode> serverComponent = new ServerComponent<>(this, super.database, super.jedisModule);
        ServerUtil.registerComponent(profileComponent, serverComponent);
    }

    /**
     * @return Name of the current {@link me.lukebingham.game.Game}
     */
    @Override
    public final String getGameName() {
        return "Skywars";
    }
}
