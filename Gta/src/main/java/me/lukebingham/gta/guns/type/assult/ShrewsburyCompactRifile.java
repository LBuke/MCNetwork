package me.lukebingham.gta.guns.type.assult;

import me.lukebingham.gta.attributes.Accuracy;
import me.lukebingham.gta.attributes.BuyableWeapon;
import me.lukebingham.gta.attributes.Damage;
import me.lukebingham.gta.attributes.FireRate;
import me.lukebingham.gta.attributes.Range;
import me.lukebingham.gta.attributes.UnlockableWeapon;
import me.lukebingham.gta.guns.attachments.Attachment;
import me.lukebingham.gta.guns.attachments.AttachmentType;
import me.lukebingham.gta.guns.attributes.AssultRifle;
import me.lukebingham.util.Dev;

/**
 * Created by LukeBingham on 15/04/2017.
 */
@Damage(3.5f)
@Accuracy(3.5f)
@FireRate(6.0f)
@Range(4.5f)
@Dev
public final class ShrewsburyCompactRifile implements AssultRifle, BuyableWeapon, UnlockableWeapon {

    /**
     * This is the name of the gun.
     *
     * @return Gun name
     */
    @Override
    public final String getName() {
        return "Shrewsbury Compact Rifile";
    }

    /**
     * This is the Gun's id (meta data)
     *
     * @return Id of the Gun
     */
    @Override
    public final short getGunId() {
        return 7;
    }

    /**
     * This is an array of {@link Attachment}s the gun will support.
     *
     * @return Supported {@link Attachment}s
     */
    @Override
    public final AttachmentType[] getSupportedAttachments() {
        return new AttachmentType[] {AttachmentType.EXTENDED_CLIP, AttachmentType.GRIP};
    }

    /**
     * This is the cost of the weapon.
     *
     * @return Weapon cost
     */
    @Override
    public final double getCost() {
        return 14650;
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
