package me.lukebingham.core.cosmetic.gadget.gadgets;

import me.lukebingham.core.cosmetic.attributes.CosmeticBuyable;
import me.lukebingham.core.cosmetic.attributes.CosmeticRequireRank;
import me.lukebingham.core.cosmetic.attributes.CosmeticUnlockable;
import me.lukebingham.core.cosmetic.gadget.Gadget;
import me.lukebingham.core.cosmetic.gadget.GadgetTriggerType;
import me.lukebingham.core.cosmetic.gadget.GadgetType;
import me.lukebingham.core.currency.CurrencyType;
import me.lukebingham.util.C;
import me.lukebingham.core.util.factory.ItemFactory;
import me.lukebingham.core.util.rank.Rank;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by LukeBingham on 22/03/2017.
 */
public final class CookieGadget implements Gadget, CosmeticBuyable, CosmeticUnlockable, CosmeticRequireRank {

    @Override
    public final int getUniqueId() {
        return 3;
    }

    @Override
    public final String getName() {
        return "Fortune Cookie";
    }

    @Override
    public final String[] getDescription() {
        return new String[] {
                "What's better than a",
                "cookie? A cookie that gives",
                "solid life advice! Enjoy a",
                "sweet treat a wise words",
                "with this Fortune Cookie",
                "gadget.",
        };
    }

    @Override
    public final GadgetTriggerType getTriggerType() {
        return GadgetTriggerType.RIGHT_CLICK;
    }

    @Override
    public final long[] getCooldown() {
        return new long[] {
                5 * 1000, //Non-Supporter cooldown time.
                3 * 1000, //Supporter cooldown time.
        };
    }

    @Override
    public final ItemFactory getItemFactory() {
        return new ItemFactory(Material.COOKIE);
    }

    @Override
    public final GadgetType getGadgetType() {
        return GadgetType.COOKIE;
    }

    @Override
    public final void action(Player player) {
        player.sendMessage(C.YELLOW + "Really? this is all this does? WOW..");
    }

    @Override
    public final double getCost() {
        return 1000;
    }

    @Override
    public final CurrencyType getCurrencyType() {
        return CurrencyType.CREDITS;
    }

    @Override
    public final double getRarity() {
        return 3;
    }

    @Override
    public final Rank getRequiredRank() {
        return Rank.VIP_PLUS;
    }
}
