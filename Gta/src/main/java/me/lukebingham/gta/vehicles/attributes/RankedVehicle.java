package me.lukebingham.gta.vehicles.attributes;

import me.lukebingham.core.util.rank.Rank;

/**
 * Created by LukeBingham on 28/04/2017.
 */
public interface RankedVehicle {

    /**
     * This is the required rank to use this Vehicle.<br><br>
     *
     * Any user can find & drive this Vehicle, However,<br>
     * Only ranked users can store/insure this Vehicle.
     *
     * @return The required rank.
     */
    Rank getRequiredRank();
}
