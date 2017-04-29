package me.lukebingham.gta.garage;

import me.lukebingham.gta.vehicles.Vehicle;

/**
 * Created by LukeBingham on 28/04/2017.
 */
public interface Garage {

    /**
     * Get the Vehicles stored/ saved in the Garage.
     *
     * @return Saved Vehicles
     */
    Vehicle[] getVehicles();

    /**
     * Get the current Garage size.
     *
     * @return Garage size
     */
    default int getGarageSize() {
        return getVehicles() == null ? 0 : getVehicles().length;
    }
}
