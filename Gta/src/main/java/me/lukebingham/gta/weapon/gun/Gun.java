package me.lukebingham.gta.weapon.gun;

import me.lukebingham.gta.weapon.Weapon;
import me.lukebingham.gta.weapon.WeaponType;
import me.lukebingham.gta.weapon.gun.attachments.Attachment;
import me.lukebingham.gta.weapon.gun.attachments.AttachmentType;
import me.lukebingham.gta.weapon.gun.attributes.BulletType;
import me.lukebingham.gta.weapon.gun.attributes.GunType;
import org.bukkit.Material;

/**
 * Created by LukeBingham on 13/04/2017.
 */
public interface Gun extends Weapon {

    /**
     * This is the type of the Weapon.
     *
     * @return Weapon type
     */
    @Override
    default WeaponType getWeaponType() {
        return WeaponType.GUN;
    }

    /**
     * This is the type of gun.
     *
     * @return Gun type
     */
    GunType getGunType();

    /**
     * This is the bullet type the gun will shoot.
     *
     * @return Bullet type
     */
    BulletType getBulletType();

    /**
     * This is the maximum amount of ammo the gun can hold.
     *
     * @return Max Ammo
     */
    default int getMaxAmmo() {
        return Integer.MAX_VALUE;
    }

    /**
     * This is the maximum amount of ammo the gun can hold per clip.
     *
     * @return Ammo Per Clip
     */
    int getAmmoPerClip();

    /**
     * This is an array of {@link Attachment}s the gun will support.
     *
     * @return Supported {@link Attachment}s
     */
    AttachmentType[] getSupportedAttachments();
}
