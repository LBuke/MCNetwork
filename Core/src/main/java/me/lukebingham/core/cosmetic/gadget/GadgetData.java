package me.lukebingham.core.cosmetic.gadget;

/**
 * Created by LukeBingham on 26/02/2017.
 */
public final class GadgetData {

    private final Gadget gadget;
    private GadgetType type;

    private long cooldown;

    public GadgetData(Gadget gadget) {
        this.gadget = gadget;

        for(GadgetType gadgetType : GadgetType.values()) {
            if(gadgetType.gadgetClass.getSimpleName().equals(gadget.getClass().getSimpleName())) {
                type = gadgetType;
                break;
            }
        }
    }

    public final Gadget getGadget() {
        return gadget;
    }

    public final GadgetType getType() {
        return type;
    }

    public final long getCooldown() {
        return this.cooldown;
    }

    public final boolean hasElapsed() {
        boolean b = this.cooldown - System.currentTimeMillis() <= 0;
        if(b) this.cooldown = 0;
        return b;
    }

    public final void initCooldown() {
        this.cooldown = System.currentTimeMillis() + gadget.getCooldown()[0];
    }
}
