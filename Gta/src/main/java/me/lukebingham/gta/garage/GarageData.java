package me.lukebingham.gta.garage;

import me.lukebingham.gta.vehicles.Vehicle;

/**
 * Created by LukeBingham on 28/04/2017.
 */
public final class GarageData implements Garage {

    private final Vehicle[] vehicles;

    public GarageData(Vehicle[] vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * Get the Vehicles stored/ saved in the Garage.
     *
     * @return Saved Vehicles
     */
    @Override
    public Vehicle[] getVehicles() {
        return this.vehicles;
    }
}
