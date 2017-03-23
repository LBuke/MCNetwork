package me.lukebingham.core.cosmetic.gadget;

import me.lukebingham.core.cosmetic.gadget.gadgets.CookieGadget;
import me.lukebingham.core.cosmetic.gadget.gadgets.LolGadget;
import me.lukebingham.core.cosmetic.gadget.gadgets.TestGadget;

/**
 * Created by LukeBingham on 25/02/2017.
 */
public enum GadgetType {
    //COMMON

    //UNCOMMON
    TEST(TestGadget.class),

    //RARE
    LOL(LolGadget.class),

    //EPIC

    //LEGENDARY
    COOKIE(CookieGadget.class),

    //SUPREME

    ;

    protected Class<? extends Gadget> gadgetClass;

    GadgetType(Class<? extends Gadget> gadgetClass) {
        this.gadgetClass = gadgetClass;
    }

    public Class<? extends Gadget> getGadgetClass() {
        return gadgetClass;
    }
}
