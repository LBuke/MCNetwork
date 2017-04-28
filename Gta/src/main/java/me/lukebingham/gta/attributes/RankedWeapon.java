package me.lukebingham.gta.attributes;

import me.lukebingham.core.util.rank.Rank;
import me.lukebingham.gta.weapon.gun.Gun;

/**
 * Created by LukeBingham on 13/04/2017.
 */
public interface RankedWeapon {

    /**
     * This is the required rank to use this {@link Gun}.<br>
     * Any user can pickup this {@link Gun}, Just cannot shoot.
     *
     * @return The required rank.
     */
    Rank getRequiredRank();
}
