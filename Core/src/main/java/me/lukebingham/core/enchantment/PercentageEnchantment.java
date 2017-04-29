package me.lukebingham.core.enchantment;

/**
 * Created by LukeBingham on 22/04/2017.
 */
public interface PercentageEnchantment {

    /**
     * This is the starting percentage of the Enchantment upgrades.<br>
     * ei, 20%(starting percentage) * 4(level) = 80%
     * @return
     */
    int getStartingPercentage();
}
