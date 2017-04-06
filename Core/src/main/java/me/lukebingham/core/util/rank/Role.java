package me.lukebingham.core.util.rank;

import me.lukebingham.util.C;

/**
 * Created by LukeBingham on 23/02/2017.
 */
public enum Role {
    ADMINISTRATOR(C.RED, "Administrator"),
    DEVELOPER(C.RED, "Developer"),
    MODERATOR(C.GREEN, "Moderator"),
    APPRENTICE(C.BLUE, "Apprentice"),

    NULL(null, null),
    ;

    private final String color, tag;

    Role(String color, String tag) {
        this.color = color;
        this.tag = tag;
    }

    public final String getColor() {
        return color;
    }

    public final String getTag() {
        return tag;
    }

    public final String getShortTag() {
        switch (this) {
            case ADMINISTRATOR: return tag.substring(0, 5);
            case DEVELOPER:     return tag.substring(0, 3);
            case MODERATOR:     return tag.substring(0, 3);
            case APPRENTICE:    return "Helper";
        }
        return "";
    }

    public final boolean hasRole(Role role) {
        return this.ordinal() <= role.ordinal();
    }
}
