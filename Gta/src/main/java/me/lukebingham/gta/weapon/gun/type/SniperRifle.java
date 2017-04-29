package me.lukebingham.gta.weapon.gun.type;

import me.lukebingham.gta.weapon.gun.Gun;
import me.lukebingham.gta.weapon.gun.misc.BulletType;
import me.lukebingham.gta.weapon.gun.GunType;
import org.bukkit.Material;

/**
 * Created by LukeBingham on 16/04/2017.
 */
public interface SniperRifle extends Gun {

    /**
     * This is the Item Material used for the gun.
     *
     * @return Gun Item Material
     */
    @Override default Material getGunItem() {
        return Material.IRON_SPADE;
    }

    /**
     * This is the type of gun.
     *
     * @return Gun type
     */
    @Override default GunType getGunType() {
        return GunType.SNIPER_RIFLE;
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
        return 5;
    }
}
