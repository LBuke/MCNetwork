package me.lukebingham.lobby.profile;

import me.lukebingham.core.cosmetic.gadget.Gadget;
import me.lukebingham.core.cosmetic.gadget.GadgetData;
import me.lukebingham.core.cosmetic.gadget.GadgetType;
import me.lukebingham.core.profile.CoreProfile;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by LukeBingham on 23/02/2017.
 */
public class LobbyProfile extends CoreProfile {

    private HashSet<GadgetData> gadgetDataList;

    public LobbyProfile(UUID uniqueId, String name) {
        super(uniqueId, name);

        this.gadgetDataList = new HashSet<>(GadgetType.values().length);
    }

    public HashSet<GadgetData> getGadgetDataList() {
        return gadgetDataList;
    }

    public void addGadgetData(GadgetData gadgetData) {
        gadgetDataList.add(gadgetData);
    }

    public GadgetData getGadgetData(Gadget gadget) {
        Optional<GadgetData> optional = gadgetDataList.stream().filter(data -> data.getGadget().getName().equals(gadget.getName())).findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

    public void removeGadgetData(GadgetData gadgetData) {
        gadgetDataList.remove(gadgetData);
    }

    public boolean hasGadget(Gadget gadget) {
        Optional<GadgetData> optional = gadgetDataList.stream().filter(data -> data.getGadget().getName().equals(gadget.getName())).findFirst();
        return optional.isPresent();
    }

    public boolean hasGadget(GadgetType gadgetType) {
        Optional<GadgetData> optional = gadgetDataList.stream().filter(data -> data.getType() == gadgetType).findFirst();
        return optional.isPresent();
    }
}
