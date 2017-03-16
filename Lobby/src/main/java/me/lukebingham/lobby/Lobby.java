package me.lukebingham.lobby;

import me.lukebingham.core.CorePlugin;
import me.lukebingham.core.cosmetic.CosmeticManager;
import me.lukebingham.core.module.ModuleState;
import me.lukebingham.core.module.PluginState;
import me.lukebingham.core.profile.ProfileManager;
import me.lukebingham.core.util.ServerUtil;
import me.lukebingham.lobby.components.ChatComponent;
import me.lukebingham.lobby.components.CreatureComponent;
import me.lukebingham.lobby.components.PlayerComponent;
import me.lukebingham.lobby.profile.LobbyProfile;

/**
 * Created by LukeBingham on 22/02/2017.
 */
@ModuleState(state = PluginState.PRE_ALPHA)
public class Lobby extends CorePlugin {

    @Override
    public void load() {
        ProfileManager<LobbyProfile> profileManager = new ProfileManager<>();
        CosmeticManager cosmeticManager = new CosmeticManager(true);

        //Components
        PlayerComponent playerComponent = new PlayerComponent(this, super.database, cosmeticManager, profileManager);
        CreatureComponent creatureComponent = new CreatureComponent();
        ChatComponent chatComponent = new ChatComponent(profileManager);

        ServerUtil.registerComponent(playerComponent, creatureComponent, chatComponent);
    }

    @Override
    public void unload() {

    }

    @Override
    public String getPluginName() {
        return "Lobby";
    }
}
