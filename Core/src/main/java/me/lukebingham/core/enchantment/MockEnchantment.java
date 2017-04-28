package me.lukebingham.core.enchantment;

import org.bukkit.enchantments.Enchantment;

/**
 * Created by LukeBingham on 20/04/2017.
 */
public abstract class MockEnchantment extends Enchantment {

    /**
     * Construct new enchantment
     */
    public MockEnchantment(int id) {
        super(id);
    }

    public String getName(String addition) {
        return getName() + " " + addition;
    }
}
