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

    private String color;
    private String name;

    RarityType(String color, String name) {
        this.color = color;
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getName(boolean color) {
        return color ? getColor() + name : name;
    }

    public int getTier() {
        return ordinal();
    }
}
