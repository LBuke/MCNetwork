package me.lukebingham.gta.vehicles.upgrade;

import me.lukebingham.core.enchantment.MockEnchantment;
import me.lukebingham.gta.vehicles.attributes.Acceleration;
import me.lukebingham.util.C;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

/**
 * Created by LukeBingham on 29/04/2017.
 */
@Acceleration(0.23f)
public final class VehicleTurbo extends MockEnchantment implements VehicleUpgradeEnchant<Acceleration> {

    /**
     * Construct new enchantment
     */
    public VehicleTurbo() {
        super(105);
    }

    /**
     * Gets the unique name of this enchantment
     *
     * @return Unique name
     */
    @Override
    public final String getName() {
        return C.GRAY + "Turbo";
    }

    /**
     * Gets the maximum level that this Enchantment may become.
     *
     * @return Maximum level of the Enchantment
     */
    @Override
    public final int getMaxLevel() {
        return 1;
    }

    /**
     * Gets the level that this Enchantment should start at
     *
     * @return Starting level of the Enchantment
     */
    @Override
    public final int getStartLevel() {
        return 1;
    }

    /**
     * Gets the type of {@link ItemStack} that may fit this Enchantment.
     *
     * @return Target type of the Enchantment
     */
    @Override
    public final EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }

    /**
     * Check if this enchantment conflicts with another enchantment.
     *
     * @param other The enchantment to check against
     * @return True if there is a conflict.
     */
    @Override
    public final boolean conflictsWith(Enchantment other) {
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
    public final boolean canEnchantItem(ItemStack item) {
        return true;
    }

    /**
     * Get the Vehicle Attribute class.
     *
     * @return Attribute Class
     */
    @Override
    public final Class<Acceleration> getAttributeAnnotation() {
        return Acceleration.class;
    }
}
