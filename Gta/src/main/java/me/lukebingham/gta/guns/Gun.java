package me.lukebingham.gta.guns;

import me.lukebingham.gta.guns.attachments.Attachment;
import me.lukebingham.gta.guns.attachments.AttachmentType;
import me.lukebingham.gta.guns.attributes.BulletType;
import me.lukebingham.gta.guns.attributes.GunType;
import org.bukkit.Material;

/**
 * Created by LukeBingham on 13/04/2017.
 */
public interface Gun {

    /**
     * This is the name of the gun.
     *
     * @return Gun name
     */
    String getName();

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
     * This is the Item Material used for the gun.
     *
     * @return Gun Item Material
     */
    Material getGunItem();

    /**
     * This is the Gun's id (meta data)
     *
     * @return Id of the Gun
     */
    short getGunId();

    /**
     * This is the Gun's aim id (meta data)
     *
     * @return Id of the Gun's aim.
     */
    default short getAimId() { return (short) (getGunId() + 1); }

    /**
     * This is an array of {@link Attachment}s the gun will support.
     *
     * @return Supported {@link Attachment}s
     */
    AttachmentType[] getSupportedAttachments();

    /**
     * This is a unique id for {@link Gun}.
     *
     * @return Unique ID {@link Gun}
     */
    default double getUniqueId() {
        return Double.valueOf(getGunItem().getId() + "." + getGunId());
    }
}
