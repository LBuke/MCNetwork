package me.lukebingham.core.util.rank;

import me.lukebingham.core.util.C;

/**
 * Created by LukeBingham on 16/03/2017.
 */
public enum Rank {
    VIP_PLUS(C.GREEN, "VIP+"),
    VIP(C.GREEN, "VIP"),

    MEMBER(C.YELLOW, null),
    ;

    private final String color, tag;

    Rank(String color, String tag) {
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
            case VIP_PLUS: return tag;
            case VIP:     return tag;
        }
        return "";
    }

    public final boolean hasRank(Rank rank) {
        return this.ordinal() <= rank.ordinal();
    }
}
