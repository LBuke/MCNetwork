package me.lukebingham.core.enchantment;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

/**
 * Created by LukeBingham on 23/04/2017.
 */
public final class GlowEnchantment extends MockEnchantment {

    /**
     * Construct new enchantment
     */
    public GlowEnchantment() {
        super(100);
    }

    /**
     * Gets the unique name of this enchantment
     *
     * @return Unique name
     */
    @Override
    public String getName() {
        return "Glow";
    }

    /**
     * Gets the maximum level that this Enchantment may become.
     *
     * @return Maximum level of the Enchantment
     */
    @Override
    public int getMaxLevel() {
        return 1;
    }

    /**
     * Gets the level that this Enchantment should start at
     *
     * @return Starting level of the Enchantment
     */
    @Override
    public int getStartLevel() {
        return 1;
    }

    /**
     * Gets the type of {@link ItemStack} that may fit this Enchantment.
     *
     * @return Target type of the Enchantment
     */
    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }

    /**
     * Check if this enchantment conflicts with another enchantment.
     *
     * @param other The enchantment to check against
     * @return True if there is a conflict.
     */
    @Override
    public boolean conflictsWith(Enchantment other) {
        return false;
    }

    /**
     * Checks if this Enchantment may be applied to the given {@link
     * ItemStack}.
     * <p>
     * This does not check if it conflicts with any enchantments already
     * applied to the item.
     *
     * @param item Item to test
     * @return True if the enchantment may be applied, otherwise False
     */
    @Override
    public boolean canEnchantItem(ItemStack item) {
        return true;
    }
}
