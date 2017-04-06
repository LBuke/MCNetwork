package me.lukebingham.lobby;

import me.lukebingham.core.CorePlugin;
import me.lukebingham.core.cosmetic.CosmeticManager;
import me.lukebingham.core.module.Module;
import me.lukebingham.core.module.PluginState;
import me.lukebingham.redis.message.ServerCreatedMessage;
import me.lukebingham.util.ServerType;
import me.lukebingham.core.util.ServerUtil;
import me.lukebingham.lobby.chat.ChatComponent;
import me.lukebingham.lobby.component.CreatureComponent;
import me.lukebingham.lobby.component.PlayerComponent;
import me.lukebingham.lobby.server.ServerComponent;
import me.lukebingham.lobby.settings.graphics.GraphicsComponent;
import me.lukebingham.lobby.profile.LobbyProfile;
import me.lukebingham.lobby.region.SpawnRegion;

/**
 * Created by LukeBingham on 22/02/2017.
 */
@Module(version = "1.0.0-SNAPSHOT", state = PluginState.PRE_ALPHA, type = ServerType.LOBBY)
public final class Lobby extends CorePlugin<LobbyProfile> {

    /**
     * This method is fired when the plugin starts.
     */
    @Override
    public final void load() {
        CosmeticManager cosmeticManager = new CosmeticManager(true);
        SpawnRegion spawnRegion = new SpawnRegion();

        //Components
        PlayerComponent playerComponent = new PlayerComponent(this, super.database, cosmeticManager, super.graphicsManager, profileManager);
        CreatureComponent creatureComponent = new CreatureComponent();
        ChatComponent chatComponent = new ChatComponent(profileManager);
        ServerComponent serverComponent = new ServerComponent(this, super.database, super.jedisModule);
        GraphicsComponent graphicsComponent = new GraphicsComponent(this, spawnRegion);

        super.jedisModule.registerListener(ServerCreatedMessage.class, serverComponent);

        ServerUtil.registerComponent(playerComponent, creatureComponent, chatComponent, serverComponent, graphicsComponent);
    }

    /**
     * This method is fired before the plugin disables
     */
    @Override
    public final void unload() {

    }

    /**
     * @return Name of the plugin
     */
    @Override
    public final String getPluginName() {
        return "Lobby";
    }
}
