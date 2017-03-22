package me.lukebingham.core.currency;

import me.lukebingham.core.util.C;

/**
 * Created by LukeBingham on 19/03/2017.
 */
public enum CurrencyType {
    COINS(C.YELLOW, Coins.class),
    CREDITS(C.AQUA, Credits.class),
    ;

    private String color;

    CurrencyType(String color, Class<? extends Currency> clazz) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
