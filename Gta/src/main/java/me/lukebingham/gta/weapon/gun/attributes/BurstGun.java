package me.lukebingham.gta.weapon.gun.attributes;

/**
 * Created by LukeBingham on 13/04/2017.
 */
public interface BurstGun {

    /**
     * This is the amount of burst shots fired.
     *
     * @return Amount of burst shots
     */
    int getBurst();

    /**
     * This is the interval time between each burst shot (In milliseconds).
     *
     * @return Burst shot interval
     */
    long getBurstInterval();
}
