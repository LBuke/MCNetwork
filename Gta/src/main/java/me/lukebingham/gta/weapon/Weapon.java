package me.lukebingham.gta.weapon;

import org.bukkit.Material;

/**
 * Created by LukeBingham on 28/04/2017.
 */
public interface Weapon {

    /**
     * This is the name of the Weapon.
     *
     * @return Weapon name
     */
    String getName();

    /**
     * This is the type of the Weapon.
     *
     * @return Weapon type
     */
    WeaponType getWeaponType();

    /**
     * This is the Item Material used for the Weapon.
     *
     * @return Weapon Item Material
     */
    Material getGunItem();

    /**
     * This is the Weapon's id (meta data)
     *
     * @return Id of the Weapon
     */
    short getGunUniqueIdentifier();
}
