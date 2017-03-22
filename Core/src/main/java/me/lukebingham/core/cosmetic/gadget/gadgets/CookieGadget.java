package me.lukebingham.core.cosmetic.gadget.gadgets;

import me.lukebingham.core.cosmetic.gadget.Gadget;
import me.lukebingham.core.cosmetic.gadget.GadgetTriggerType;
import me.lukebingham.core.cosmetic.attributes.CosmeticBuyable;
import me.lukebingham.core.cosmetic.attributes.CosmeticRequireRank;
import me.lukebingham.core.cosmetic.attributes.CosmeticUnlockable;
import me.lukebingham.core.currency.CurrencyType;
import me.lukebingham.core.util.C;
import me.lukebingham.core.util.factory.ItemFactory;
import me.lukebingham.core.util.rank.Rank;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by LukeBingham on 22/03/2017.
 */
public class CookieGadget implements Gadget, CosmeticBuyable, CosmeticUnlockable, CosmeticRequireRank {

    @Override
    public int getUniqueId() {
        return 3;
    }

    @Override
    public String getName() {
        return "Fortune Cookie";
    }

    @Override
    public String[] getDescription() {
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
    public GadgetTriggerType getTriggerType() {
        return GadgetTriggerType.RIGHT_CLICK;
    }

    @Override
    public long[] getCooldown() {
        return new long[] {
                5 * 1000, //Non-Supporter cooldown time.
                3 * 1000, //Supporter cooldown time.
        };
    }

    @Override
    public ItemFactory getItemFactory() {
        return new ItemFactory(Material.BONE);
    }

    @Override
    public void action(Player player) {
        player.sendMessage(C.YELLOW + "Really? this is all this does? WOW..");
    }

    @Override
    public double getCost() {
        return 1000;
    }

    @Override
    public CurrencyType getCurrencyType() {
        return CurrencyType.CREDITS;
    }

    @Override
    public double getRarity() {
        return 3;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.VIP_PLUS;
    }
}
