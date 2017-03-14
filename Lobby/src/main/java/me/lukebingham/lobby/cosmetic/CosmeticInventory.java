package me.lukebingham.lobby.cosmetic;

import me.lukebingham.core.cosmetic.CosmeticManager;
import me.lukebingham.core.inventory.MenuModule;
import me.lukebingham.core.inventory.item.ClickableItem;
import me.lukebingham.core.inventory.item.DummyItem;
import me.lukebingham.core.util.factory.ItemFactory;
import me.lukebingham.lobby.cosmetic.gadget.GadgetInventory;
import me.lukebingham.lobby.profile.LobbyProfile;
import me.lukebingham.lobby.profile.ProfileInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

/**
 * Created by LukeBingham on 25/02/2017.
 */
public class CosmeticInventory extends MenuModule {

    public CosmeticInventory(CosmeticManager cosmeticManager, LobbyProfile profile) {
        super(4, "Cosmetics");

        int gadgetsOwned = profile.getGadgetDataList().size();
        ItemFactory gadgets = new ItemFactory(Material.CHEST).setName("Gadgets");
        ItemFactory hats = new ItemFactory(Material.SKULL_ITEM, (byte) 4).setName("Hats");
        ItemFactory trails = new ItemFactory(Material.BLAZE_POWDER).setName("Trails");
        ItemFactory pets = new ItemFactory(Material.MONSTER_EGG, (byte) 383).setName("Pets");

        ItemFactory close = new ItemFactory(Material.ARROW).setName("Back");
        ItemFactory profileHead = new ItemFactory(Material.SKULL_ITEM, (byte) 3).setName("Profile").setOwner(profile.getName());

        addItem(new ClickableItem(10, gadgets.build(), true) {
            @Override public void click(Player player, ClickType clickType) {
                new GadgetInventory(cosmeticManager, profile).openInventory(player);
            }
        });

        addItem(new ClickableItem(12, hats.build(), true) {
            @Override public void click(Player player, ClickType clickType) {
            }
        });

        addItem(new ClickableItem(14, trails.build(), true) {
            @Override public void click(Player player, ClickType clickType) {
            }
        });

        addItem(new ClickableItem(16, pets.build(), true) {
            @Override public void click(Player player, ClickType clickType) {
            }
        });

        addItem(new ClickableItem(27, close.build(), true) {
            @Override public void click(Player player, ClickType clickType) {
                new ProfileInventory(cosmeticManager, profile).openInventory(player);
            }
        });

        addItem(new DummyItem(31, profileHead.build(), true));
    }
}
