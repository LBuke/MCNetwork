package me.lukebingham.gta.vehicles.type.car.sport;

import me.lukebingham.core.util.rank.Rank;
import me.lukebingham.gta.vehicles.attributes.*;
import me.lukebingham.gta.vehicles.type.Car;
import me.lukebingham.gta.vehicles.upgrade.UpgradeType;

/**
 * Created by LukeBingham on 28/04/2017.
 */
@Speed(8.6f)
@Acceleration(7.3f)
@Braking(5.5f)
@Handling(6.6f)
public final class AudiR8 implements Car, RankedVehicle {

    /**
     * This is the name of the Vehicle.
     *
     * @return Vehicle name
     */
    @Override
    public final String getName() {
        return "Audi R8";
    }

    /**
     * This is the Vehicles's id (meta data)
     *
     * @return Id of the Vehicle
     */
    @Override
    public final short getVehicleUniqueIdentifier() {
        return 12;
    }

    /**
     * Get the available upgrades the Vehicle can use.
     *
     * @return Available Upgrades
     */
    @Override
    public final UpgradeType[] getAvailableUpgrades() {
        return new UpgradeType[] {
                UpgradeType.ARMOUR,
                UpgradeType.BRAKES,
                UpgradeType.ENGINE,
                UpgradeType.TRACKER,
                UpgradeType.TRANSMISSION,
                UpgradeType.TURBO
        };
    }

    /**
     * This is the required rank to use this Vehicle.<br><br>
     * <p>
     * Any user can find & drive this Vehicle, However,<br>
     * Only ranked users can store/insure this Vehicle.
     *
     * @return The required rank.
     */
    @Override
    public final Rank getRequiredRank() {
        return Rank.VIP;
    }
}
