package me.lukebingham.core.cosmetic.misc;

import me.lukebingham.core.cosmetic.RarityType;

/**
 * Created by LukeBingham on 25/02/2017.
 */
public interface CosmeticUnlockable {
    double getRarity();

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
