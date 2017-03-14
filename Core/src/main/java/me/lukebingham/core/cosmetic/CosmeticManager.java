package me.lukebingham.core.cosmetic;

import me.lukebingham.core.cosmetic.gadget.GadgetManager;

/**
 * Created by LukeBingham on 24/02/2017.
 */
public class CosmeticManager {

    private final GadgetManager gadgetManager;

    public CosmeticManager(boolean gadget) {
        gadgetManager = new GadgetManager(gadget);
    }

    public GadgetManager getGadgetManager() {
        return gadgetManager;
    }
}
