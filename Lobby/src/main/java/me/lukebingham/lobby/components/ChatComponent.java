package me.lukebingham.lobby.components;

import me.lukebingham.core.profile.ProfileManager;
import me.lukebingham.core.util.C;
import me.lukebingham.core.util.Component;
import me.lukebingham.core.util.rank.Role;
import me.lukebingham.lobby.profile.LobbyProfile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by LukeBingham on 23/02/2017.
 */
public class ChatComponent implements Component {

    private ProfileManager<LobbyProfile> profileManager;

    public ChatComponent(ProfileManager<LobbyProfile> profileManager) {
        this.profileManager = profileManager;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        String format = "";

        LobbyProfile lobbyPlayer = profileManager.getCache(event.getPlayer().getUniqueId());
        Role role = lobbyPlayer.getRole();
        if(role != Role.NULL) {
            format += role.getColor() + role.getShortTag() + C.WHITE + " | ";
        }

        //TODO Check if donor & prioritise name color
        if(role != Role.NULL) {
            format += role.getColor() + (lobbyPlayer.getDisplayName().equals("null") ? event.getPlayer().getName() : lobbyPlayer.getDisplayName()) + C.WHITE + ": ";
        }

        format += C.GRAY + event.getMessage();

        event.setFormat(format);
    }
}
