package me.lukebingham.gta.vehicles.attributes;

import me.lukebingham.gta.vehicles.upgrade.UpgradeType;

/**
 * Created by LukeBingham on 28/04/2017.
 */
public interface VehicleUpgradeable {

    /**
     * Get the available upgrades the Vehicle can use.
     *
     * @return Available Upgrades
     */
    UpgradeType[] getAvailableUpgrades();
}
