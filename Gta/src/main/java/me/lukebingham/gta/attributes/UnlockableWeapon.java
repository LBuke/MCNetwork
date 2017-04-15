package me.lukebingham.gta.attributes;

import me.lukebingham.util.RarityType;

/**
 * Created by LukeBingham on 13/04/2017.
 */
public interface UnlockableWeapon {

    /**
     * This is the rarity of the weapon being won.
     *
     * @return Weapon rarity
     */
    double getRarity();

    /**
     * This is the rarity type of the weapon.<br>
     * By default, automatically calculated
     *
     * @return Weapon rarity type
     */
    default RarityType getRarityType() {
        double r = getRarity();
        if (r > 100) r = 100;
        if (r < 0) r = 0.1;

        if (r > 50 && r <= 100)      return RarityType.COMMON;
        else if (r > 30 && r <= 50)  return RarityType.UNCOMMON;
        else if (r > 13 && r <= 30)  return RarityType.RARE;
        else if (r > 5 && r <= 13.5) return RarityType.EPIC;
        else if (r > 0.5 && r <= 5)  return RarityType.LEGENDARY;
        else if (r <= 0.5)           return RarityType.SUPREME;

        return RarityType.COMMON;
    }
}
