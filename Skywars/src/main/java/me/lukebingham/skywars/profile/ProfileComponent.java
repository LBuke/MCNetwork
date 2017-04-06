package me.lukebingham.skywars.profile;

import me.lukebingham.core.module.Module;
import me.lukebingham.core.module.PluginState;
import me.lukebingham.core.profile.CoreProfile;
import me.lukebingham.core.profile.ProfileManager;
import me.lukebingham.util.C;
import me.lukebingham.core.util.Component;
import me.lukebingham.core.util.ServerUtil;
import me.lukebingham.core.util.factory.ItemFactory;
import me.lukebingham.core.util.rank.Role;
import me.lukebingham.skywars.Skywars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by LukeBingham on 30/03/2017.
 */
public final class ProfileComponent implements Component {

    private final Skywars<?> skywars;
    private final ProfileManager<CoreProfile> profileManager;

    public ProfileComponent(Skywars<?> skywars, ProfileManager<CoreProfile> profileManager) {
        this.skywars = skywars;
        this.profileManager = profileManager;
    }

    @EventHandler
    protected final void onPlayerLogin(AsyncPlayerPreLoginEvent event) {
        //TODO pull from database
        profileManager.cacheProfile(new CoreProfile(event.getUniqueId(), event.getName()), call -> {
            if (skywars.getClass().isAnnotationPresent(Module.class)) {
                PluginState state = skywars.getClass().getAnnotation(Module.class).state();
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

    @EventHandler
    protected final void onPlayerJoin(PlayerJoinEvent event) {
        if(event.getPlayer() == null) return;

        if(!event.getPlayer().isOp())
            event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0.5, 34, 0.5, 45.0f, 5.0f));

        event.getPlayer().getInventory().clear();
        event.getPlayer().getInventory().setItem(4, new ItemFactory(Material.SKULL_ITEM, (byte) 3).setName("Profile").setOwner(event.getPlayer().getName()).build());
    }
}
