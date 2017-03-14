package me.lukebingham.core.cosmetic.gadget;

import me.lukebingham.core.cosmetic.Cosmetic;
import me.lukebingham.core.util.factory.ItemFactory;
import org.bukkit.entity.Player;

/**
 * Created by LukeBingham on 24/02/2017.
 */
public interface Gadget extends Cosmetic {
    GadgetTriggerType getTriggerType();
    long[] getCooldown();
    ItemFactory getItemFactory();

    void action(Player player);
}
