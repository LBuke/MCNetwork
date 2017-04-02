package me.lukebingham.core.cosmetic;

import me.lukebingham.core.util.C;

/**
 * Created by LukeBingham on 25/02/2017.
 */
public enum RarityType {
    COMMON(C.WHITE, "Common"),
    UNCOMMON(C.GREEN, "Uncommon"),
    RARE(C.BLUE, "Rare"),
    EPIC(C.DARK_PURPLE, "Epic"),
    LEGENDARY(C.GOLD, "Legendary"),
    SUPREME(C.YELLOW, "Supreme"),
    ;

    private final String color;
    private final String name;

    RarityType(String color, String name) {
        this.color = color;
        this.name = name;
    }

    public final String getColor() {
        return color;
    }

    public final String getName() {
        return name;
    }

    public final String getName(boolean color) {
        return color ? getColor() + name : name;
    }

    public final int getTier() {
        return ordinal();
    }
}
