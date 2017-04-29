package me.lukebingham.gta.weapon.attributes;

/**
 * Created by LukeBingham on 13/04/2017.
 */
public interface BuyableWeapon {

    /**
     * This is the cost of the weapon.
     *
     * @return Weapon cost
     */
    double getCost();

    /**
     * This is the required level a user needs<br>
     * to be in order to buy the weapon.
     *
     * @return Required user level
     */
    default int getRequiredLevel() {
        return 1;
    }
}
