package me.lukebingham.lobby;

import me.lukebingham.core.CorePlugin;
import me.lukebingham.core.cosmetic.CosmeticManager;
import me.lukebingham.core.module.Module;
import me.lukebingham.core.module.PluginState;
import me.lukebingham.core.redis.message.ServerCreatedMessage;
import me.lukebingham.core.util.ServerType;
import me.lukebingham.core.util.ServerUtil;
import me.lukebingham.lobby.components.ChatComponent;
import me.lukebingham.lobby.components.CreatureComponent;
import me.lukebingham.lobby.components.PlayerComponent;
import me.lukebingham.lobby.components.ServerComponent;
import me.lukebingham.lobby.profile.LobbyProfile;

/**
 * Created by LukeBingham on 22/02/2017.
 */
@Module(version = "1.0.0-SNAPSHOT", state = PluginState.PRE_ALPHA, type = ServerType.LOBBY)
public class Lobby extends CorePlugin<LobbyProfile> {

    @Override
    public void load() {
        CosmeticManager cosmeticManager = new CosmeticManager(true);

        //Components
        PlayerComponent playerComponent = new PlayerComponent(this, super.database, cosmeticManager, profileManager);
        CreatureComponent creatureComponent = new CreatureComponent();
        ChatComponent chatComponent = new ChatComponent(profileManager);
        ServerComponent serverComponent = new ServerComponent(super.database, super.jedisModule);

        super.jedisModule.registerListener(ServerCreatedMessage.class, serverComponent);

        ServerUtil.registerComponent(playerComponent, creatureComponent, chatComponent, serverComponent);

        ServerUtil.logDebug("TEST");
    }

    @Override
    public void unload() {

    }

    @Override
    public String getPluginName() {
        return "Lobby";
    }
}
