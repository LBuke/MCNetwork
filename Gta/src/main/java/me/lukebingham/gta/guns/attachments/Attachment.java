package me.lukebingham.gta.guns.attachments;

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
     * This is the {@link me.lukebingham.gta.attributes.Accuracy} modifier that is applied<br>
     * To the {@link me.lukebingham.gta.guns.Gun} when the Attachment is equipped.
     *
     * @return Accuracy modifier
     */
    float getAccuracyModifier();

    /**
     * This is the {@link me.lukebingham.gta.attributes.Damage} modifier that is applied<br>
     * To the {@link me.lukebingham.gta.guns.Gun} when the Attachment is equipped.
     *
     * @return Damage modifier
     */
    float getDamageModifier();

    /**
     * This is the {@link me.lukebingham.gta.attributes.Range} modifier that is applied<br>
     * To the {@link me.lukebingham.gta.guns.Gun} when the Attachment is equipped.
     *
     * @return Range modifier
     */
    float getRangeModifier();
}
