package me.lukebingham.lobby.profile;

import me.lukebingham.core.cosmetic.CosmeticManager;
import me.lukebingham.core.graphics.GraphicsManager;
import me.lukebingham.core.inventory.MenuModule;
import me.lukebingham.core.inventory.item.ClickableItem;
import me.lukebingham.core.inventory.item.DummyItem;
import me.lukebingham.core.util.factory.ItemFactory;
import me.lukebingham.lobby.cosmetic.CosmeticInventory;
import me.lukebingham.lobby.settings.SettingsInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

/**
 * Created by LukeBingham on 25/02/2017.
 */
public class ProfileInventory extends MenuModule {

    public ProfileInventory(CosmeticManager cosmeticManager, GraphicsManager graphicsManager, LobbyProfile profile) {
        super(6, (profile.getDisplayName().equals("null") ? profile.getName() : profile.getDisplayName()) + " Profile");

        ItemFactory achievements = new ItemFactory(Material.DIAMOND).setName("Achievements");
        ItemFactory quests = new ItemFactory(Material.PAPER).setName("Quests");
        ItemFactory cosmetics = new ItemFactory(Material.ENDER_CHEST).setName("Cosmetics");
        ItemFactory settings = new ItemFactory(Material.REDSTONE).setName("Settings");
        ItemFactory punishments = new ItemFactory(Material.BOOK).setName("Punishments");

        ItemFactory close = new ItemFactory(Material.BARRIER).setName("Close");
        ItemFactory profileHead = new ItemFactory(Material.SKULL_ITEM, (byte) 3).setName("Profile").setOwner(profile.getName());

        addItem(new ClickableItem(12, achievements.build(), true) {
            @Override public void onClick(Player player, ClickType clickType) {
            }
        });

        addItem(new ClickableItem(14, quests.build(), true) {
            @Override public void onClick(Player player, ClickType clickType) {
            }
        });

        addItem(new ClickableItem(19, cosmetics.build(), true) {
            @Override public void onClick(Player player, ClickType clickType) {
                new CosmeticInventory(cosmeticManager, graphicsManager, profile).openInventory(player);
            }
        });

        addItem(new ClickableItem(25, settings.build(), true) {
            @Override public void onClick(Player player, ClickType clickType) {
                new SettingsInventory(cosmeticManager, graphicsManager, profile).openInventory(player);
            }
        });

        addItem(new ClickableItem(31, punishments.build(), true) {
            @Override public void onClick(Player player, ClickType clickType) {
            }
        });

        addItem(new ClickableItem(45, close.build(), true) {
            @Override public void onClick(Player player, ClickType clickType) {
                player.closeInventory();
            }
        });

        addItem(new DummyItem(49, profileHead.build(), true));
    }
}
