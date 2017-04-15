package me.lukebingham.gta;

import me.lukebingham.core.CorePlugin;
import me.lukebingham.core.module.Module;
import me.lukebingham.core.module.PluginState;
import me.lukebingham.core.profile.CoreProfile;
import me.lukebingham.core.util.StringUtil;
import me.lukebingham.gta.guns.GunManager;
import me.lukebingham.gta.guns.command.GunCommand;
import me.lukebingham.util.C;
import me.lukebingham.util.ServerType;

/**
 * Created by LukeBingham on 13/04/2017.
 */
@Module(version = "1.0.0-SNAPSHOT", state = PluginState.PRE_ALPHA, type = ServerType.GTA)
public class GTA extends CorePlugin<CoreProfile> {

    /**
     * This method is fired when the plugin starts.
     */
    @Override
    protected void load() {
        GunManager gunManager = new GunManager();
        new GunCommand(gunManager);
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
