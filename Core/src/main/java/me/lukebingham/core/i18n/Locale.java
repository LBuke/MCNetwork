package me.lukebingham.core.i18n;

/**
 * Created by LukeBingham on 20/03/2017.
 */
public enum Locale {
    en_GB("en_gb"),
    en_US("en_us"),
    de_DE("de_de"),
    it_IT("it_it"),
    ;

    private String tag;

    Locale(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public static Locale fromString(String key) {
        for(Locale locale : values()) {
            if(!locale.getTag().equals(key)) continue;
            return locale;
        }
        return en_US;
    }
}
