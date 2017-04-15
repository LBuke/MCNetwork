package me.lukebingham.gta.attributes;

import me.lukebingham.core.util.rank.Rank;

/**
 * Created by LukeBingham on 13/04/2017.
 */
public interface RankedWeapon {

    /**
     * This is the required rank to use this {@link me.lukebingham.gta.guns.Gun}.<br>
     * Any user can pickup this {@link me.lukebingham.gta.guns.Gun}, Just cannot shoot.
     *
     * @return The required rank.
     */
    Rank getRequiredRank();
}
