package me.lukebingham.core.i18n;

/**
 * Created by LukeBingham on 19/03/2017.
 */
public enum I18nMessage {
    ID("id"),

    // CURRENCY & ECONOMY
    MONEY_SYMBOL("money-symbol"),
    COINS_SYMBOL("coins-symbol"),

    // COSMETICS
    COSMETICS("cosmetics"),
    GADGETS("gadgets"),
    HATS("hats"),
    TRAILS("trails"),
    PETS("pets"),

    // GENERAL WORDS
    RARITY("rarity"),
    COST("cost"),
    PROFILE("profile"),
    BACK("back"),

    TEST("test"),
    ;

    private String key;

    I18nMessage(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
