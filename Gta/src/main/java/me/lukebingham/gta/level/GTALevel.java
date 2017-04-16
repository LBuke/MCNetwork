package me.lukebingham.gta.level;

import me.lukebingham.gta.level.event.GTAPlayerLevelEvent;
import me.lukebingham.gta.profile.GTAProfile;
import org.bukkit.Bukkit;

/**
 * Created by LukeBingham on 16/04/2017.
 */
public final class GTALevel implements Level {

    private final GTAProfile profile;

    private int level;
    private float pExp = 0f, exp;

    public GTALevel(GTAProfile profile, int level) {
        this.profile = profile;
        this.level = level;
    }

    /**
     * Get's the current level of a user.
     *
     * @return Current level
     */
    @Override
    public int getLevel() {
        return this.level;
    }

    /**
     * Get's the previous level.
     *
     * @return Previous level
     */
    @Override
    public int getPreviousLevel() {
        return this.level - 1 <= 0 ? 1 : this.level - 1;
    }

    /**
     * Get's the next level.
     *
     * @return Next level
     */
    @Override
    public int getNextLevel() {
        return this.level + 1;
    }

    /**
     * Get's the current experience of a user.
     *
     * @return Current Experience
     */
    @Override
    public float getExp() {
        return this.exp;
    }

    /**
     * Add experience to the current experience count.
     *
     * @param exp Experience to add
     */
    @Override
    public void addExp(float exp) {
        this.exp += exp;
        if(this.exp >= getRequiredExp()) {
            this.level += 1;
            GTAPlayerLevelEvent event = new GTAPlayerLevelEvent(profile, this);
            Bukkit.getPluginManager().callEvent(event);
        }
    }

    /**
     * Get's the required amount of experience needed to level up.
     *
     * @return Required Experience
     */
    @Override
    public float getRequiredExp() {
        return Math.round(500 * ((getLevel() * getPreviousLevel()) + getPreviousRequiredExp() / 2500));
    }

    /**
     * Get's the previous level's required experience.
     *
     * @return Previous required exp
     */
    @Override
    public float getPreviousRequiredExp() {
        return this.pExp;
    }
}
