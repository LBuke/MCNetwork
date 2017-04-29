package me.lukebingham.gta.weapon.gun.attachments;

import me.lukebingham.gta.weapon.attributes.Accuracy;
import me.lukebingham.gta.weapon.attributes.Damage;
import me.lukebingham.gta.weapon.attributes.Range;
import me.lukebingham.gta.weapon.gun.Gun;

/**
 * Created by LukeBingham on 13/04/2017.
 */
public class Grip implements Attachment {

    /**
     * This is the name of the Attachment.
     *
     * @return Attachment name
     */
    @Override
    public final String getName() {
        return "Grip";
    }

    /**
     * This will return the type of Attachment.
     *
     * @return Attachment type
     */
    @Override
    public final AttachmentType getAttachmentType() {
        return AttachmentType.GRIP;
    }

    /**
     * This is the cost of the Attachment.
     *
     * @return Attachment cost
     */
    @Override
    public final int getCost() {
        return 2200;
    }

    /**
     * This is the {@link Accuracy} modifier that is applied<br>
     * To the {@link Gun} when the Attachment is equipped.
     *
     * @return Accuracy modifier
     */
    @Override
    public final float getAccuracyModifier() {
        return 2.0f;
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
