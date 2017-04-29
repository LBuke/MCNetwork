package me.lukebingham.gta.vehicles.type;

import me.lukebingham.gta.vehicles.Vehicle;
import me.lukebingham.gta.vehicles.VehicleType;
import me.lukebingham.gta.vehicles.attributes.GroundVehicle;
import me.lukebingham.gta.vehicles.attributes.VehicleUpgradeable;
import org.bukkit.Material;

/**
 * Created by LukeBingham on 28/04/2017.
 */
public interface Car extends Vehicle, GroundVehicle, VehicleUpgradeable {

    /**
     * This is the type of Vehicle Type.
     *
     * @return Vehicle type
     */
    @Override
    default VehicleType getVehicleType() {
        return VehicleType.CAR;
    }

    /**
     * This is the Item Material used for the vehicle.
     *
     * @return Vehicle Item Material
     */
    @Override
    default Material getVehicleItem() {
        return Material.DIAMOND_AXE;
    }
}
