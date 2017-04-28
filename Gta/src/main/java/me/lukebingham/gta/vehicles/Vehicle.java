package me.lukebingham.gta.vehicles;

import org.bukkit.Material;

/**
 * Created by LukeBingham on 20/04/2017.
 */
public interface Vehicle {

    /**
     * This is the name of the Vehicle.
     *
     * @return Vehicle name
     */
    String getName();

    /**
     * This is the type of Vehicle Type.
     *
     * @return Vehicle type
     */
    VehicleType getVehicleType();

    /**
     * This is the Item Material used for the vehicle.
     *
     * @return Vehicle Item Material
     */
    Material getVehicleItem();

    /**
     * This is the Vehicles's id (meta data)
     *
     * @return Id of the Vehicle
     */
    short getVehicleUniqueIdentifier();
}
