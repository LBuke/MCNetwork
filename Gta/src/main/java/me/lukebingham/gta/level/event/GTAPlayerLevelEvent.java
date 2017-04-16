package me.lukebingham.gta.level.event;

import me.lukebingham.core.event.EventFactory;
import me.lukebingham.gta.level.Level;
import me.lukebingham.gta.profile.GTAProfile;

/**
 * Created by LukeBingham on 16/04/2017.
 */
public final class GTAPlayerLevelEvent extends EventFactory {

    private final GTAProfile profile;
    private final Level level;

    /**
     * Construct a new Event
     */
    public GTAPlayerLevelEvent(GTAProfile profile, Level level) {
        super(false);
        this.profile = profile;
        this.level = level;
    }

    public final GTAProfile getProfile() {
        return profile;
    }

    public final Level getLevel() {
        return level;
    }
}
