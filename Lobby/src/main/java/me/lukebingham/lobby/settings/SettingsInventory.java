package me.lukebingham.lobby.settings;

import me.lukebingham.core.cosmetic.CosmeticManager;
import me.lukebingham.core.graphics.GraphicsManager;
import me.lukebingham.core.i18n.I18n;
import me.lukebingham.core.i18n.I18nMessage;
import me.lukebingham.core.i18n.I18nTODO;
import me.lukebingham.core.inventory.MenuModule;
import me.lukebingham.core.inventory.item.ClickableItem;
import me.lukebingham.core.util.factory.ItemFactory;
import me.lukebingham.lobby.profile.LobbyProfile;
import me.lukebingham.lobby.profile.ProfileInventory;
import me.lukebingham.lobby.settings.graphics.GraphicsInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

/**
 * Created by LukeBingham on 27/03/2017.
 */
@I18nTODO
public final class SettingsInventory extends MenuModule {

    public SettingsInventory(CosmeticManager cosmeticManager, GraphicsManager graphicsManager, LobbyProfile profile) {
        super(6, "Settings");

        ItemFactory graphics = new ItemFactory(Material.DIAMOND).setName("Graphics");
        ItemFactory close = new ItemFactory(Material.ARROW).setName(I18n.get(profile, I18nMessage.BACK));

        addItem(new ClickableItem(13, graphics.build(), true) {
            @Override public void onClick(Player player, ClickType clickType) {
                new GraphicsInventory(cosmeticManager, graphicsManager, profile).openInventory(player);
            }
        });

        addItem(new ClickableItem(45, close.build(), true) {
            @Override public void onClick(Player player, ClickType clickType) {
                new ProfileInventory(cosmeticManager, graphicsManager, profile).openInventory(player);
            }
        });
    }
}
