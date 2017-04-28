package me.lukebingham.gta.weapon.gun;

import me.lukebingham.gta.attributes.FireRate;
import me.lukebingham.gta.weapon.gun.attributes.GunType;

/**
 * Created by LukeBingham on 18/04/2017.
 */
public final class GunCooldown {
    private final GunType gunType;
    private final Gun gun;

    private long cooldown;

    public GunCooldown(GunType gunType, Gun gun) {
        this.gunType = gunType;
        this.gun = gun;
    }

    public GunType getGunType() {
        return gunType;
    }

    public Gun getGun() {
        return gun;
    }

    /**
     * Start the cooldown.
     */
    public void start() {
        if(!gun.getClass().isAnnotationPresent(FireRate.class)) {
            //Default 5 if cannot be found.
            cooldown = System.currentTimeMillis() + 500;
            return;
        }

        double firerate = (double) gun.getClass().getAnnotation(FireRate.class).value();
        cooldown = (long) (System.currentTimeMillis() + (firerate * 49));
    }

    /**
     * Check if the cooldown has passed.
     */
    public boolean hasElapsed() {
        return System.currentTimeMillis() >= cooldown;
    }
}
