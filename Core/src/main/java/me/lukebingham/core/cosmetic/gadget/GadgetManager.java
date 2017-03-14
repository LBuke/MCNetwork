package me.lukebingham.core.cosmetic.gadget;

import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by LukeBingham on 24/02/2017.
 */
public class GadgetManager {

    private static HashMap<GadgetType, Gadget> gadgets;

    private boolean enabled;

    public GadgetManager(boolean enabled) {
        this.gadgets = Maps.newHashMap();
        this.enabled = enabled;

        Arrays.stream(GadgetType.values()).filter(type -> type.gadgetClass != null).forEach(type -> {
            try {
                gadgets.put(type, type.gadgetClass.newInstance());
            }
            catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public boolean isEnabled() {
        return enabled;
    }

    public static HashMap<GadgetType, Gadget> getGadgetsMap() {
        return gadgets;
    }
}
