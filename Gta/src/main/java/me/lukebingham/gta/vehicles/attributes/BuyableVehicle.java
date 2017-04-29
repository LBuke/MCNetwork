package me.lukebingham.gta.vehicles.attributes;

/**
 * Created by LukeBingham on 28/04/2017.
 */
public interface BuyableVehicle {

    /**
     * This is the cost of the Vehicle.
     *
     * @return Vehicle cost
     */
    double getCost();

    /**
     * This is the required level a user needs<br>
     * to be in order to buy the Vehicle.
     *
     * @return Required user level
     */
    default int getRequiredLevel() {
        return 1;
    }
}
