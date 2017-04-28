package me.lukebingham.gta.weapon.gun.attributes;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;

/**
 * Created by LukeBingham on 13/04/2017.
 */
public enum BulletType {
    BULLET(Snowball.class),
    ROCKET(EnderPearl.class),
    ;

    private final Class<? extends Projectile> projectile;

    BulletType(Class<? extends Projectile> projectile) {
        this.projectile = projectile;
    }

    public Class<? extends Projectile> getProjectile() {
        return projectile;
    }
}
