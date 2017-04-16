package me.lukebingham.gta.level;

/**
 * Created by LukeBingham on 16/04/2017.
 */
public interface Level {

    /**
     * Get's the current level of a user.
     *
     * @return Current level
     */
    int getLevel();

    /**
     * Get's the previous level.
     *
     * @return Previous level
     */
    int getPreviousLevel();

    /**
     * Get's the next level.
     *
     * @return Next level
     */
    int getNextLevel();

    /**
     * Get's the current experience of a user.
     *
     * @return Current Experience
     */
    float getExp();

    /**
     * Add experience to the current experience count.
     *
     * @param exp Experience to add
     */
    void addExp(float exp);

    /**
     * Get's the required amount of experience needed to level up.
     *
     * @return Required Experience
     */
    float getRequiredExp();

    /**
     * Get's the previous level's required experience.
     *
     * @return Previous required exp
     */
    float getPreviousRequiredExp();
}
