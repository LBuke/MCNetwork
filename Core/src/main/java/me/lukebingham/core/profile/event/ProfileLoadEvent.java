package me.lukebingham.core.profile.event;

import me.lukebingham.core.event.EventFactory;
import me.lukebingham.core.profile.CoreProfile;

/**
 * Created by LukeBingham on 26/03/2017.
 */
public final class ProfileLoadEvent<Profile extends CoreProfile> extends EventFactory {

    private final Profile profile;

    public ProfileLoadEvent(Profile profile) {
        super(false);

        this.profile = profile;
    }

    public final Profile getProfile() {
        return profile;
    }
}
