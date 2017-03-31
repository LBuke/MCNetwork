package me.lukebingham.game.chat;

import me.lukebingham.core.profile.CoreProfile;
import me.lukebingham.core.profile.ProfileManager;
import me.lukebingham.core.util.C;
import me.lukebingham.core.util.Component;
import me.lukebingham.core.util.rank.Role;
import me.lukebingham.game.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by LukeBingham on 31/03/2017.
 */
public final class ChatComponent<Profile extends CoreProfile> implements Component {

    private final Game<?> game;
    private final ProfileManager<Profile> profileManager;

    public ChatComponent(Game<?> game, ProfileManager<Profile> profileManager) {
        this.game = game;
        this.profileManager = profileManager;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        String format = "";

        Profile profile = profileManager.getCache(event.getPlayer().getUniqueId());
        Role role = profile.getRole();
        if(role != Role.NULL) {
            format += role.getColor() + role.getShortTag() + C.WHITE + " | ";
        }

        //TODO Check if donor & prioritise name color
        if(role != Role.NULL) {
            format += role.getColor() + (profile.getDisplayName().equals("null") ? event.getPlayer().getName() : profile.getDisplayName()) + C.WHITE + ": ";
        }

        format += C.GRAY + event.getMessage();

        event.setFormat(format);
    }
}
