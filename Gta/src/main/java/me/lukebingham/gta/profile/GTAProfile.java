package me.lukebingham.gta.profile;

import com.google.common.collect.Maps;
import me.lukebingham.core.profile.CoreProfile;
import me.lukebingham.gta.garage.Garage;
import me.lukebingham.gta.garage.GarageData;
import me.lukebingham.gta.weapon.gun.Gun;
import me.lukebingham.gta.weapon.gun.GunType;
import me.lukebingham.gta.level.Level;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by LukeBingham on 16/04/2017.
 */
public final class GTAProfile extends CoreProfile {

    private final HashMap<GunType, Long> ammoMap = Maps.newHashMap();
    private final HashMap<Gun, Integer> clipMap = Maps.newHashMap();
    private Garage garage;
    private Level level;

    public GTAProfile(UUID uniqueId, String name) {
        super(uniqueId, name);
    }

    public final Level getLevel() {
        return level;
    }

    public final void setLevel(Level level) {
        this.level = level;
    }

    public final void addAmmo(GunType gunType, long amount) {
        long current = getAmmo(gunType);
        ammoMap.put(gunType, current + amount > Long.MAX_VALUE ? Long.MAX_VALUE : current + amount);
    }

    public final void takeAmmo(GunType gunType, long amount) {
        long current = getAmmo(gunType);
        ammoMap.put(gunType, current - amount < 0 ? 0 : current - amount);
    }

    public final long getAmmo(GunType gunType) {
        if (ammoMap.containsKey(gunType)) return ammoMap.get(gunType);
        ammoMap.put(gunType, 100L);
        return ammoMap.get(gunType);
    }

    public final int getClip(Gun gun) {
        if (clipMap.containsKey(gun)) return clipMap.get(gun);
        clipMap.put(gun, gun.getAmmoPerClip());
        return clipMap.get(gun);
    }

    public final void refillClip(Gun gun) {
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

    public final boolean canReload(Gun gun) {
        if(getClip(gun) >= gun.getAmmoPerClip()) return false;
        if(getAmmo(gun.getGunType()) <= 0) return false;
        return true;
    }

    public final void takeClip(Gun gun, int amount) {
        int current = getClip(gun);
        clipMap.put(gun, current - amount <= 0 ? 0 : current - amount);
    }

    public final void setGarage(GarageData garage) {
        this.garage = garage;
    }

    public final Garage getGarage() {
        return garage;
    }
}
