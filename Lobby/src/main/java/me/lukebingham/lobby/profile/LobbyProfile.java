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
public final class LobbyProfile extends CoreProfile {

    private final HashSet<GadgetData> gadgetDataList;

    public LobbyProfile(UUID uniqueId, String name) {
        super(uniqueId, name);

        this.gadgetDataList = new HashSet<>(GadgetType.values().length);
    }

    public HashSet<GadgetData> getGadgetDataList() {
        return gadgetDataList;
    }

    public final void addGadgetData(GadgetData gadgetData) {
        Optional<GadgetData> optional = gadgetDataList.stream().filter(data -> data.getType() == gadgetData.getType()).findAny();
        if(optional.isPresent()) return;

        gadgetDataList.add(gadgetData);
    }

    public final void addGadgetData(Gadget gadget) {
        Optional<GadgetData> optional = gadgetDataList.stream().filter(data -> data.getType() == gadget.getGadgetType()).findAny();
        if(optional.isPresent()) return;

        gadgetDataList.add(new GadgetData(gadget));
    }

    public final GadgetData getGadgetData(Gadget gadget) {
        Optional<GadgetData> optional = gadgetDataList.stream().filter(data -> data.getGadget().getName().equals(gadget.getName())).findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

    public final void removeGadgetData(GadgetData gadgetData) {
        gadgetDataList.remove(gadgetData);
    }

    public final boolean hasGadget(Gadget gadget) {
        Optional<GadgetData> optional = gadgetDataList.stream().filter(data -> data.getType() == gadget.getGadgetType()).findFirst();
        return optional.isPresent();
    }

    public final boolean hasGadget(GadgetType gadgetType) {
        Optional<GadgetData> optional = gadgetDataList.stream().filter(data -> data.getType() == gadgetType).findFirst();
        return optional.isPresent();
    }
}
