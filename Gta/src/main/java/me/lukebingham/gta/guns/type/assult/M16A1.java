package me.lukebingham.gta.guns.type.assult;

import me.lukebingham.core.util.rank.Rank;
import me.lukebingham.gta.attributes.*;
import me.lukebingham.gta.guns.Gun;
import me.lukebingham.gta.guns.attachments.Attachment;
import me.lukebingham.gta.guns.attachments.AttachmentType;
import me.lukebingham.gta.guns.attributes.AssultRifle;
import me.lukebingham.gta.guns.attributes.BurstGun;
import me.lukebingham.util.Dev;

/**
 * Created by LukeBingham on 13/04/2017.
 */
@Damage(3.5f)
@Accuracy(7.6f)
@FireRate(3.2f)
@Range(6.8f)
@Dev
public final class M16A1 implements AssultRifle, BurstGun, BuyableWeapon, UnlockableWeapon, RankedWeapon {

    /**
     * This is the name of the gun.
     *
     * @return Gun name
     */
    @Override
    public final String getName() {
        return "M16A1";
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
        return new AttachmentType[] { AttachmentType.EXTENDED_CLIP, AttachmentType.GRIP };
    }

    /**
     * This is the amount of burst shots fired.
     *
     * @return Amount of burst shots
     */
    @Override
    public final int getBurst() {
        return 3;
    }

    /**
     * This is the interval time between each burst shot (In milliseconds).
     *
     * @return Burst shot interval
     */
    @Override
    public final long getBurstInterval() {
        return 125;
    }

    /**
     * This is the cost of the weapon.
     *
     * @return Weapon cost
     */
    @Override
    public final double getCost() {
        return 14500;
    }

    /**
     * This is the rarity of the weapon being won.
     *
     * @return Weapon rarity
     */
    @Override
    public final double getRarity() {
        return 60;
    }

    /**
     * This is the required rank to use this {@link Gun}.<br>
     * Any user can pickup this {@link Gun}, Just cannot shoot.
     *
     * @return The required rank.
     */
    @Override
    public final Rank getRequiredRank() {
        return Rank.VIP;
    }
}
