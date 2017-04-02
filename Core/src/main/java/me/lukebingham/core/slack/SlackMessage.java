package me.lukebingham.core.slack;

import com.google.gson.JsonObject;

/**
 * Created by LukeBingham on 22/02/2017.
 */
public final class SlackMessage {

    /**
     * The channel in the Slack Server to post to
     */
    private final String channel;
    /**
     * The username of the 'fake' user that is posting
     */
    private final String username;
    /**
     * The icon assigned to the user
     */
    private final String icon;
    /**
     * The text contents of the message
     */
    private final String text;

    /**
     * Construct a new SlackMessage.
     *
     * @param channel  - the channel to post the message
     * @param username - the username of the message
     * @param icon     - the icon of the message, ex: `:smile:` or a url link
     * @param text     - the text of the message
     */
    public SlackMessage(String channel, String username, String icon, String text) {
        this.channel = channel;
        this.username = username;
        this.icon = icon;
        this.text = text;
    }

    /**
     * Converts the SlackMessage to JSON.
     *
     * @return The JSON representation of this message.
     */
    public final JsonObject toJSON() {

        JsonObject slackMessage = new JsonObject();

        if (channel != null) {
            slackMessage.addProperty(SlackKey.CHANNEL.getId(), channel);
        }

        if (username != null) {
            slackMessage.addProperty(SlackKey.USERNAME.getId(), username);
        }

        if (icon != null) {
            if (icon.contains("http")) {
                slackMessage.addProperty(SlackKey.ICON_URL.getId(), icon);
            }
            else {
                slackMessage.addProperty(SlackKey.ICON_EMOJI.getId(), icon);
            }
        }

        if (text == null) {
            throw new IllegalArgumentException("Missing text contents in " + toString());
        }
        else {
            slackMessage.addProperty(SlackKey.TEXT.getId(), text);
        }

        return slackMessage;
    }

    @Override
    public final String toString() {
        return "SlackMessage [channel=" + channel + ", username=" + username + ", icon=" + icon + ", text=" + text + "]";
    }
}

