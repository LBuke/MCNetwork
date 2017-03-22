package me.lukebingham.core.cosmetic.attributes;

import me.lukebingham.core.currency.CurrencyType;

/**
 * Created by LukeBingham on 25/02/2017.
 */
public interface CosmeticBuyable {
    double getCost();
    default CurrencyType getCurrencyType() {
        return CurrencyType.COINS;
    }
}
