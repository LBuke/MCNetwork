package me.lukebingham.gta.weapon.gun.attachments;

import me.lukebingham.gta.weapon.attributes.Accuracy;
import me.lukebingham.gta.weapon.attributes.Damage;
import me.lukebingham.gta.weapon.attributes.Range;
import me.lukebingham.gta.weapon.gun.Gun;

/**
 * Created by LukeBingham on 13/04/2017.
 */
public interface Attachment {

    /**
     * This is the name of the Attachment.
     *
     * @return Attachment name
     */
    String getName();

    /**
     * This will return the type of Attachment.
     *
     * @return Attachment type
     */
    AttachmentType getAttachmentType();

    /**
     * This is the cost of the Attachment.
     *
     * @return Attachment cost
     */
    int getCost();

    /**
     * This is the {@link Accuracy} modifier that is applied<br>
     * To the {@link Gun} when the Attachment is equipped.
     *
     * @return Accuracy modifier
     */
    float getAccuracyModifier();

    /**
     * This is the {@link Damage} modifier that is applied<br>
     * To the {@link Gun} when the Attachment is equipped.
     *
     * @return Damage modifier
     */
    float getDamageModifier();

    /**
     * This is the {@link Range} modifier that is applied<br>
     * To the {@link Gun} when the Attachment is equipped.
     *
     * @return Range modifier
     */
    float getRangeModifier();
}
