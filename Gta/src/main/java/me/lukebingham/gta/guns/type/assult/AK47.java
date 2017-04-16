package me.lukebingham.gta.guns.type.assult;

import me.lukebingham.gta.attributes.*;
import me.lukebingham.gta.guns.attachments.Attachment;
import me.lukebingham.gta.guns.attachments.AttachmentType;
import me.lukebingham.gta.guns.attributes.AssultRifle;
import me.lukebingham.util.Dev;

/**
 * Created by LukeBingham on 15/04/2017.
 */
@Damage(5.5f)
@Accuracy(4.5f)
@FireRate(4.6f)
@Range(5.2f)
@Dev
public final class AK47 implements AssultRifle, BuyableWeapon, UnlockableWeapon {

    /**
     * This is the name of the gun.
     *
     * @return Gun name
     */
    @Override
    public final String getName() {
        return "AK47";
    }

    /**
     * This is the Gun's id (meta data)
     *
     * @return Id of the Gun
     */
    @Override
    public final short getGunId() {
        return 1;
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
        return 18500;
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
