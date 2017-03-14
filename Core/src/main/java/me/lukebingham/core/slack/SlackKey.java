package me.lukebingham.core.slack;

/**
 * Created by LukeBingham on 22/02/2017.
 */
public enum SlackKey {

    CHANNEL("channel"),
    USERNAME("username"),
    ICON_URL("icon_url"),
    ICON_EMOJI("icon_emoji"),
    TEXT("text");

    /**
     * The ID representation of the key in the SlackServer message
     */
    private final String id;

    /**
     * Construct a SlackKey.
     * <p>
     * This is used as the dictionary key references for the payload messaging
     * being sent to SlackServer.
     *
     * @param id - the id of the key
     */
    SlackKey(String id) {
        this.id = id;
    }

    /**
     * Get the ID of the slack key.
     *
     * @return The string representation of the ID that represents the
     * dictionary key for the slack payload.
     */
    public String getId() {
        return id;
    }
}

