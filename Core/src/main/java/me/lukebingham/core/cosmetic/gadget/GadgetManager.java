package me.lukebingham.core.cosmetic.gadget;

import com.google.common.collect.Maps;
import me.lukebingham.core.cosmetic.gadget.event.PlayerGadgetEvent;
import me.lukebingham.core.util.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.*;

/**
 * Created by LukeBingham on 24/02/2017.
 */
public class GadgetManager implements Component {

    public static final int SLOT_INDEX = 5;

    private static TreeMap<GadgetType, Gadget> gadgets;
    private final HashMap<UUID, GadgetData> activeGadgets;

    private boolean enabled;

    public GadgetManager(boolean enabled) {
        this.gadgets = Maps.newTreeMap();
        this.activeGadgets = Maps.newHashMap();
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
        return this.enabled;
    }

    public static TreeMap<GadgetType, Gadget> getGadgetsMap() {
        return gadgets;
    }

    public void setActiveGadget(UUID uniqueId, GadgetData gadget) {
        this.activeGadgets.put(uniqueId, gadget);
    }

    public GadgetData getActiveGadget(UUID uniqueId) {
        if(!hasActiveGadget(uniqueId)) return null;
        return this.activeGadgets.get(uniqueId);
    }

    public void removeActiveGadget(UUID uniqueId) {
        this.activeGadgets.remove(uniqueId);
    }

    public boolean hasActiveGadget(UUID uniqueId) {
        return this.activeGadgets.containsKey(uniqueId);
    }

    public PlayerGadgetEvent fireEvent(boolean async, Player player, Gadget gadget) {
        PlayerGadgetEvent event = new PlayerGadgetEvent(async, player, gadget);
        if(!this.enabled) return event;
        Bukkit.getPluginManager().callEvent(event);
        return event;
    }

    @EventHandler
    protected void onPlayerInteract(PlayerInteractEvent event) {
        if(!this.enabled) return;

        if(event.getHand() != EquipmentSlot.HAND) return;
        if(event.getPlayer() == null) return;
        if(event.getItem() == null || event.getItem().getType() == Material.AIR) return;
        if(event.getPlayer().getInventory().getHeldItemSlot() != SLOT_INDEX) return;
        if(!hasActiveGadget(event.getPlayer().getUniqueId())) return;

        GadgetData gadget = getActiveGadget(event.getPlayer().getUniqueId());
        if(!gadget.hasElapsed()) {
            event.getPlayer().sendMessage("Wait x seconds until you can use this again.");
            return;
        }

        switch (gadget.getGadget().getTriggerType()) {
            case RIGHT_CLICK:
                if(event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) return;
                gadget.initCooldown();
                gadget.getGadget().action(event.getPlayer());
                fireEvent(false, event.getPlayer(), gadget.getGadget());
                break;

            case LEFT_CLICK:
                if(event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.LEFT_CLICK_AIR) return;
                gadget.initCooldown();
                gadget.getGadget().action(event.getPlayer());
                fireEvent(false, event.getPlayer(), gadget.getGadget());
                break;
        }
    }
}
