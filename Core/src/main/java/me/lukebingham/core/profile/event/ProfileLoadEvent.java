package me.lukebingham.core.profile.event;

import me.lukebingham.core.event.EventFactory;
import me.lukebingham.core.profile.CoreProfile;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by LukeBingham on 26/03/2017.
 */
public class ProfileLoadEvent<Profile extends CoreProfile> extends EventFactory {

    private Profile profile;

    public ProfileLoadEvent(Profile profile) {
        super(false);

        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }
}
