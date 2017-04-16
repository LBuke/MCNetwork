package me.lukebingham.gta;

import me.lukebingham.core.CorePlugin;
import me.lukebingham.core.module.Module;
import me.lukebingham.core.module.PluginState;
import me.lukebingham.core.util.ServerUtil;
import me.lukebingham.gta.chat.ChatComponent;
import me.lukebingham.gta.guns.GunComponent;
import me.lukebingham.gta.guns.GunManager;
import me.lukebingham.gta.guns.command.GunCommand;
import me.lukebingham.gta.player.PlayerComponent;
import me.lukebingham.gta.profile.GTAProfile;
import me.lukebingham.util.ServerType;

/**
 * Created by LukeBingham on 13/04/2017.
 */
@Module(version = "1.0.0-SNAPSHOT", state = PluginState.PRE_ALPHA, type = ServerType.GTA)
public final class GTA extends CorePlugin<GTAProfile> {

    /**
     * This method is fired when the plugin starts.
     */
    @Override
    protected void load() {
        GunManager gunManager = new GunManager();

        //Commands
        new GunCommand(gunManager);

        //Components
        GunComponent gunComponent = new GunComponent(gunManager, super.profileManager);
        ChatComponent chatComponent = new ChatComponent(super.profileManager);
        PlayerComponent playerComponent = new PlayerComponent(super.profileManager);

        ServerUtil.registerComponent(gunComponent, chatComponent, playerComponent);
    }

    /**
     * This method is fired before the plugin disables
     */
    @Override
    protected void unload() {

    }

    /**
     * @return Name of the plugin
     */
    @Override
    public String getPluginName() {
        return "GTA";
    }
}
