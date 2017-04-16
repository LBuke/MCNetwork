package me.lukebingham.gta.profile;

import com.google.common.collect.Maps;
import me.lukebingham.core.profile.CoreProfile;
import me.lukebingham.gta.guns.Gun;
import me.lukebingham.gta.guns.attributes.GunType;
import me.lukebingham.gta.level.Level;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by LukeBingham on 16/04/2017.
 */
public final class GTAProfile extends CoreProfile {

    private final HashMap<GunType, Long> ammoMap = Maps.newHashMap();
    private final HashMap<Gun, Integer> clipMap = Maps.newHashMap();
    private Level level;

    public GTAProfile(UUID uniqueId, String name) {
        super(uniqueId, name);
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void addAmmo(GunType gunType, long amount) {
        long current = getAmmo(gunType);
        ammoMap.put(gunType, current + amount > Long.MAX_VALUE ? Long.MAX_VALUE : current + amount);
    }

    public void takeAmmo(GunType gunType, long amount) {
        long current = getAmmo(gunType);
        ammoMap.put(gunType, current - amount < 0 ? 0 : current - amount);
    }

    public long getAmmo(GunType gunType) {
        if (ammoMap.containsKey(gunType)) return ammoMap.get(gunType);
        ammoMap.put(gunType, 100L);
        return ammoMap.get(gunType);
    }

    public int getClip(Gun gun) {
        if (clipMap.containsKey(gun)) return clipMap.get(gun);
        clipMap.put(gun, gun.getAmmoPerClip());
        return clipMap.get(gun);
    }

    public void refillClip(Gun gun) {
        int currentClip = getClip(gun);
        long currentAmmo = getAmmo(gun.getGunType());
        if(currentClip <= 0) {
            currentClip = currentAmmo - gun.getAmmoPerClip() >= 0 ? gun.getAmmoPerClip() : (int) currentAmmo;
            takeAmmo(gun.getGunType(), currentClip);
        } else {
            int take = gun.getAmmoPerClip() - currentClip;
            currentClip += currentAmmo >= take ? take : currentAmmo;
            takeAmmo(gun.getGunType(), take);
        }
        clipMap.put(gun, currentClip);
    }

    public boolean canReload(Gun gun) {
        if(getClip(gun) >= gun.getAmmoPerClip()) return false;
        if(getAmmo(gun.getGunType()) <= 0) return false;
        return true;
    }

    public void takeClip(Gun gun, int amount) {
        int current = getClip(gun);
        clipMap.put(gun, current - amount <= 0 ? 0 : current - amount);
    }
}
