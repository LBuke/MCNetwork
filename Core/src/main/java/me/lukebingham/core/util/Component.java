package me.lukebingham.core.util;

import org.bukkit.event.Listener;

/**
 * Created by LukeBingham on 22/02/2017.
 */
public interface Component extends Listener {

    /**
     * This method will run when the component is loaded/registered.
     */
    default void onLoad() {}

    /**
     * This method will run when the component is disabled (ie, manually or server end)
     */
    default void onDisable() {}

    default void log(boolean enabled) {
        String log = (enabled ? C.GREEN + "Component loaded" : C.RED + "Component disabled") + C.WHITE + ": " + C.YELLOW + getClass().getSimpleName() + C.GRAY;
        int classLength = getClass().getSimpleName().length(), max = 35, difference = max - classLength;
        for(int i = 0; i < difference; i++) log += ".";
        log += (disableAble() ? C.GREEN : C.RED) + String.valueOf(disableAble()).toUpperCase();
        ServerUtil.log(log);
    }

    default boolean disableAble() {
        return true;
    }
}
