package me.lukebingham.gta.guns.attachments;

import me.lukebingham.gta.attributes.Accuracy;
import me.lukebingham.gta.attributes.Damage;
import me.lukebingham.gta.attributes.Range;
import me.lukebingham.gta.guns.Gun;

/**
 * Created by LukeBingham on 13/04/2017.
 */
public final class ExtendedClip implements Attachment {

    /**
     * This is the name of the Attachment.
     *
     * @return Attachment name
     */
    @Override
    public final String getName() {
        return "Extended Clip";
    }

    /**
     * This will return the type of Attachment.
     *
     * @return Attachment type
     */
    @Override
    public final AttachmentType getAttachmentType() {
        return AttachmentType.EXTENDED_CLIP;
    }

    /**
     * This is the cost of the Attachment.
     *
     * @return Attachment cost
     */
    @Override
    public final int getCost() {
        return 1250;
    }

    /**
     * This is the {@link Accuracy} modifier that is applied<br>
     * To the {@link Gun} when the Attachment is equipped.
     *
     * @return Accuracy modifier
     */
    @Override
    public final float getAccuracyModifier() {
        return 0;
    }

    /**
     * This is the {@link Damage} modifier that is applied<br>
     * To the {@link Gun} when the Attachment is equipped.
     *
     * @return Damage modifier
     */
    @Override
    public final float getDamageModifier() {
        return 0;
    }

    /**
     * This is the {@link Range} modifier that is applied<br>
     * To the {@link Gun} when the Attachment is equipped.
     *
     * @return Range modifier
     */
    @Override
    public final float getRangeModifier() {
        return 0;
    }
}
