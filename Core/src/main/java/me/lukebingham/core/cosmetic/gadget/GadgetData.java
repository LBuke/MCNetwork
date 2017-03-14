package me.lukebingham.core.cosmetic.gadget;

/**
 * Created by LukeBingham on 26/02/2017.
 */
public class GadgetData {

    private Gadget gadget;
    private GadgetType type;

    public GadgetData(Gadget gadget) {
        this.gadget = gadget;

        for(GadgetType gadgetType : GadgetType.values()) {
            if(gadgetType.gadgetClass.getSimpleName().equals(gadget.getClass().getSimpleName())) {
                type = gadgetType;
                break;
            }
        }
    }

    public Gadget getGadget() {
        return gadget;
    }

    public GadgetType getType() {
        return type;
    }
}
