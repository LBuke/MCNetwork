package me.lukebingham.core.module;

import me.lukebingham.core.util.C;

/**
 * Created by LukeBingham on 22/02/2017.
 */
public enum PluginState {
    PRE_ALPHA(C.RED + "Pre Alpha"),
    ALPHA(C.GOLD + "Alpha"),
    BETA(C.YELLOW + "Beta"),
    RELEASE(C.GREEN + "Release"),
    ;

    private String name;

    PluginState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getName(boolean uppercase, boolean bold) {
        return bold ? (uppercase ? C.BOLD + name.toUpperCase() : C.BOLD + name) : (uppercase ? name.toUpperCase() : name);
    }
}
