package me.lukebingham.gta.guns.type.pistols;

import me.lukebingham.gta.attributes.Accuracy;
import me.lukebingham.gta.attributes.BuyableWeapon;
import me.lukebingham.gta.attributes.Damage;
import me.lukebingham.gta.attributes.FireRate;
import me.lukebingham.gta.attributes.Range;
import me.lukebingham.gta.attributes.UnlockableWeapon;
import me.lukebingham.gta.guns.attachments.Attachment;
import me.lukebingham.gta.guns.attachments.AttachmentType;
import me.lukebingham.gta.guns.attributes.Pistol;
import me.lukebingham.util.Dev;

/**
 * Created by LukeBingham on 15/04/2017.
 */
@Damage(8.0f)
@Accuracy(3.0f)
@FireRate(1.5f)
@Range(3.5f)
@Dev
public final class MarksmanPistol implements Pistol, BuyableWeapon, UnlockableWeapon {

    /**
     * This is the name of the gun.
     *
     * @return Gun name
     */
    @Override
    public final String getName() {
        return "Marksman Pistol";
    }

    /**
     * This is the Gun's id (meta data)
     *
     * @return Id of the Gun
     */
    @Override
    public final short getGunId() {
        return 2;
    }

    /**
     * This is an array of {@link Attachment}s the gun will support.
     *
     * @return Supported {@link Attachment}s
     */
    @Override
    public final AttachmentType[] getSupportedAttachments() {
        return new AttachmentType[] {};
    }

    /**
     * This is the cost of the weapon.
     *
     * @return Weapon cost
     */
    @Override
    public final double getCost() {
        return 4350;
    }

    /**
     * This is the rarity of the weapon being won.
     *
     * @return Weapon rarity
     */
    @Override
    public final double getRarity() {
        return 40;
    }
}