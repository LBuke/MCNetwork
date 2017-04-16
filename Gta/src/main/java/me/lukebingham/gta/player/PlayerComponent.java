package me.lukebingham.gta.player;

import me.lukebingham.core.Core;
import me.lukebingham.core.cosmetic.CosmeticManager;
import me.lukebingham.core.cosmetic.gadget.gadgets.CookieGadget;
import me.lukebingham.core.graphics.GraphicsManager;
import me.lukebingham.core.module.Module;
import me.lukebingham.core.module.PluginState;
import me.lukebingham.core.profile.ProfileManager;
import me.lukebingham.core.util.Component;
import me.lukebingham.core.util.ServerUtil;
import me.lukebingham.core.util.rank.Role;
import me.lukebingham.database.Database;
import me.lukebingham.gta.profile.GTAProfile;
import me.lukebingham.util.C;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by LukeBingham on 16/04/2017.
 */
public final class PlayerComponent implements Component {

    private final ProfileManager<GTAProfile> profileManager;

    public PlayerComponent(ProfileManager<GTAProfile> profileManager) {
        this.profileManager = profileManager;
    }

    @Override
    public final boolean disableAble() {
        return false;
    }

    @Override
    public final void onDisable() {
        profileManager.getPlayerCache().forEach(cache -> profileManager.saveCache(cache.getUniqueId(), call -> {}));
    }

    @EventHandler
    protected final void onPlayerLogin(AsyncPlayerPreLoginEvent event) {
        //TODO pull from database
        profileManager.cacheProfile(new GTAProfile(event.getUniqueId(), event.getName()), call -> {
            if (GTAProfile.class.isAnnotationPresent(Module.class)) {
                PluginState state = GTAProfile.class.getAnnotation(Module.class).state();
                if (state == PluginState.PRE_ALPHA || state == PluginState.ALPHA) {
                    if (!call.getRole().hasRole(Role.MODERATOR)) {
                        event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
                        event.setKickMessage(C.RED + "You don't have permission to join this server.");
                    }
                }
            }
        });
    }

    @EventHandler
    protected final void onPlayerQuit(PlayerQuitEvent event) {
        if(event.getPlayer() == null) return;
        profileManager.saveCache(event.getPlayer().getUniqueId(), call -> {
            ServerUtil.logDebug("Profile saved for " + call.getName());
        });
    }
}
