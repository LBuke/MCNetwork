package me.lukebingham.lobby.cosmetic.gadget;

import me.lukebingham.core.cosmetic.CosmeticManager;
import me.lukebingham.core.cosmetic.attributes.CosmeticRequireRank;
import me.lukebingham.core.cosmetic.gadget.Gadget;
import me.lukebingham.core.cosmetic.gadget.GadgetManager;
import me.lukebingham.core.cosmetic.attributes.CosmeticBuyable;
import me.lukebingham.core.cosmetic.attributes.CosmeticUnlockable;
import me.lukebingham.core.i18n.I18n;
import me.lukebingham.core.i18n.I18nMessage;
import me.lukebingham.core.inventory.MenuModule;
import me.lukebingham.core.inventory.item.ClickableItem;
import me.lukebingham.core.inventory.item.DummyItem;
import me.lukebingham.core.inventory.item.button.BackPage;
import me.lukebingham.core.inventory.item.button.NextPage;
import me.lukebingham.core.util.C;
import me.lukebingham.core.util.InventoryUtil;
import me.lukebingham.core.util.StringUtil;
import me.lukebingham.core.util.factory.ItemFactory;
import me.lukebingham.core.util.rank.Rank;
import me.lukebingham.lobby.cosmetic.CosmeticInventory;
import me.lukebingham.lobby.profile.LobbyProfile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LukeBingham on 25/02/2017.
 */
public class GadgetInventory extends MenuModule {

    public GadgetInventory(CosmeticManager cosmeticManager, LobbyProfile profile) {
        this(cosmeticManager, profile, 1);
    }

    private GadgetInventory(CosmeticManager cosmeticManager, LobbyProfile profile, int page) {
        super(6, I18n.get(profile, I18nMessage.GADGETS));

        ItemFactory back = new ItemFactory(Material.ARROW).setName(I18n.get(profile, I18nMessage.BACK));
        ItemFactory profileHead = new ItemFactory(Material.SKULL_ITEM, (byte) 3).setName(I18n.get(profile, I18nMessage.PROFILE)).setOwner(profile.getName());

        int[] slots = InventoryUtil.getSlots(3);

        List<ClickableGadgetItem> items = new ArrayList<>();
        for (Gadget gadget : GadgetManager.getGadgetsMap().values()) {
            boolean unlocked = profile.hasGadget(gadget), usable = true;
            String name = I18n.get(profile, "gadget." + gadget.getUniqueId() + ".name");
            ItemFactory item = unlocked ? gadget.getItemFactory().clone().setName(C.GREEN + name) : new ItemFactory(Material.CLAY_BALL).setName(C.RED + name);

            List<String> lore = new ArrayList<>();
//            for(String str : gadget.getDescription()) {
//                lore.add(C.GRAY + str);
//            }
            for(String str : StringUtil.breakUp(I18n.get(profile, "gadget." + gadget.getUniqueId() + ".desc"), 20)) {
                lore.add(C.GRAY + str);
            }
            if(gadget instanceof CosmeticUnlockable) {
                CosmeticUnlockable g = (CosmeticUnlockable) gadget;
                lore.add(" ");
                lore.add(C.GRAY + I18n.get(profile, I18nMessage.RARITY) + C.WHITE + ": " + g.getRarityType().getColor() + g.getRarityType().name());
            }
            if(gadget instanceof CosmeticRequireRank) {
                CosmeticRequireRank g = (CosmeticRequireRank) gadget;
                if(!profile.getRank().hasRank(g.getRequiredRank())) {
                    usable = false;
                    lore.add(" ");
                    lore.add(C.GRAY + "Requires rank" + C.WHITE + ": " + g.getRequiredRank().getColor() + g.getRequiredRank().getTag());
                }
            }
            if(!unlocked && gadget instanceof CosmeticBuyable) {
                lore.add(" ");
                lore.add(C.GOLD + I18n.get(profile, I18nMessage.COST) + C.WHITE + ": " + C.GOLD + I18n.get(profile, I18nMessage.MONEY_SYMBOL) + C.YELLOW + String.format("%,.0f", ((CosmeticBuyable) gadget).getCost()));
            }
            item.setLore(lore);
            lore.clear();

            items.add(new ClickableGadgetItem(item.build(), profile, gadget, unlocked, usable));
        }

        int perPage = slots.length, pages = 1, x = items.size(), z = 0;
        if (x / perPage > 0) pages = x / perPage;
        if (x % perPage > 0) pages += 1;
        if (items.size() <= perPage) pages = 1;
        if (page > pages) return;
//        System.out.println((page * perPage) - perPage + " - " + page * perPage);
        for (int i = ((page * perPage) - perPage); i < (page * perPage); i++) {
            if (i >= x) {
//                for(int j = z; j < perPage; j++) {//This is to complete the page null spaces.
//                    addItem(new DummyItem(slots[z++], new ItemFactory(Material.DIRT).build(), true));
//                }
                break;
            }

            ClickableGadgetItem gadgetItem = items.get(i);
            gadgetItem.setIndex(slots[z++]);
            addItem(gadgetItem);
        }

        if (page < pages) {
            int slot = ((getRows() - 1) * 9) + 9 - 1;
            addItem(new NextPage(slot) {
                @Override
                public void click(Player player, ClickType clickType) {
                    super.click(player, clickType);
                    new GadgetInventory(cosmeticManager, profile, page + 1).openInventory(player);
                }
            });
        }

        if (page > 1) {
            int slot = ((getRows() - 1) * 9);
            addItem(new BackPage(slot) {
                @Override
                public void click(Player player, ClickType clickType) {
                    super.click(player, clickType);
                    new GadgetInventory(cosmeticManager, profile, page - 1).openInventory(player);
                }
            });
        } else {
            addItem(new ClickableItem(45, back.build(), true) {
                @Override public void click(Player player, ClickType clickType) {
                    new CosmeticInventory(cosmeticManager, profile).openInventory(player);
                }
            });
        }

        addItem(new DummyItem(49, profileHead.build(), true));
    }

    public class ClickableGadgetItem extends ClickableItem {

        protected final LobbyProfile profile;
        protected final Gadget gadget;
        protected final boolean unlocked, usable;

        public ClickableGadgetItem(ItemStack itemStack, LobbyProfile profile, Gadget gadget, boolean unlocked, boolean usable) {
            super(0, itemStack, true);
            this.profile = profile;
            this.gadget = gadget;
            this.unlocked = unlocked;
            this.usable = usable;
        }

        @Override
        public void click(Player player, ClickType clickType) {
            if(!unlocked) {
                I18n.message(profile, I18nMessage.GADGETS_NOT_UNLOCKED, gadget.getName());
                return;
            }

            if(!usable) {
                Rank rank = ((CosmeticRequireRank)gadget).getRequiredRank();
                I18n.message(profile, I18nMessage.GADGETS_RANK_REQUIRED, rank.getColor() + rank.getTag());
                return;
            }

            player.getInventory().setItem(GadgetManager.SLOT_INDEX, getItemStack());
            player.updateInventory();
        }

        public boolean isUsable() {
            return usable;
        }

        public Gadget getGadget() {
            return gadget;
        }
    }
}
