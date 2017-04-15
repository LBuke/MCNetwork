package me.lukebingham.gta.guns.attributes;

import me.lukebingham.gta.guns.Gun;
import org.bukkit.Material;

/**
 * Created by LukeBingham on 16/04/2017.
 */
public interface Pistol extends Gun {

    /**
     * This is the Item Material used for the gun.
     *
     * @return Gun Item Material
     */
    @Override default Material getGunItem() {
        return Material.WOOD_HOE;
    }

    /**
     * This is the type of gun.
     *
     * @return Gun type
     */
    @Override default GunType getGunType() {
        return GunType.PISTOL;
    }

    /**
     * This is the bullet type the gun will shoot.
     *
     * @return Bullet type
     */
    @Override default BulletType getBulletType() {
        return BulletType.BULLET;
    }

    /**
     * This is the maximum amount of ammo the gun can hold per clip.
     *
     * @return Ammo Per Clip
     */
    @Override default int getAmmoPerClip() {
        return 8;
    }
}
