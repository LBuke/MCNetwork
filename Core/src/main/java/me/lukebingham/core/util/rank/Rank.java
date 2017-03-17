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

    private String color, tag;

    Rank(String color, String tag) {
        this.color = color;
        this.tag = tag;
    }

    public String getColor() {
        return color;
    }

    public String getTag() {
        return tag;
    }

    public String getShortTag() {
        switch (this) {
            case VIP_PLUS: return tag;
            case VIP:     return tag;
        }
        return "";
    }

    public boolean hasRank(Rank rank) {
        return this.ordinal() <= rank.ordinal();
    }
}
