package me.lukebingham.gta.chat;

import me.lukebingham.core.profile.ProfileManager;
import me.lukebingham.core.util.Component;
import me.lukebingham.core.util.rank.Role;
import me.lukebingham.gta.profile.GTAProfile;
import me.lukebingham.util.C;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by LukeBingham on 16/04/2017.
 */
public final class ChatComponent implements Component {

    private final ProfileManager<GTAProfile> profileManager;

    public ChatComponent(ProfileManager<GTAProfile> profileManager) {
        this.profileManager = profileManager;
    }

    @EventHandler
    protected final void onChat(AsyncPlayerChatEvent event) {

        String format = "";

        GTAProfile profile = profileManager.getCache(event.getPlayer().getUniqueId());
        if(profile == null) return;
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
