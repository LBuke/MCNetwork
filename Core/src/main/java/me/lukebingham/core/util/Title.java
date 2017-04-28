package me.lukebingham.core.util;

/**
 * Created by LukeBingham on 16/04/2017.
 */
public class Title {

    private final String title, subtitle;

    public Title(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }
}
