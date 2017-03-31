package me.lukebingham.lobby.settings.graphics;

import me.lukebingham.core.cosmetic.CosmeticManager;
import me.lukebingham.core.graphics.Graphics;
import me.lukebingham.core.graphics.GraphicsManager;
import me.lukebingham.core.graphics.GraphicsType;
import me.lukebingham.core.i18n.I18n;
import me.lukebingham.core.i18n.I18nMessage;
import me.lukebingham.core.i18n.I18nTODO;
import me.lukebingham.core.inventory.MenuModule;
import me.lukebingham.core.inventory.item.ClickableItem;
import me.lukebingham.core.inventory.item.DummyItem;
import me.lukebingham.core.util.C;
import me.lukebingham.core.util.StringUtil;
import me.lukebingham.core.util.factory.ItemFactory;
import me.lukebingham.lobby.profile.LobbyProfile;
import me.lukebingham.lobby.profile.ProfileInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

/**
 * Created by LukeBingham on 27/03/2017.
 */
@I18nTODO
public final class GraphicsInventory extends MenuModule {

    public GraphicsInventory(CosmeticManager cosmeticManager, GraphicsManager graphicsManager, LobbyProfile profile) {
        super(4, I18n.get(profile, "graphics"));

        ItemFactory close = new ItemFactory(Material.ARROW).setName(I18n.get(profile, I18nMessage.BACK));

        for(GraphicsType type : graphicsManager.getGraphicsMap().keySet()) {
            if(profile.getGraphics() == type) {
                addItem(new DummyItem(type.getSlot(), new ItemFactory(Material.INK_SACK, (byte) 10)
                        .setName(I18n.get(profile, "graphics.name", type.getName(), C.GRAY + "(" + C.YELLOW + I18n.get(profile, I18nMessage.SELECTED) + C.GRAY + ")"))
                        .setLore(StringUtil.breakUp(I18n.get(profile, "graphics." + type.getId() + ".desc")))
                        .build(), true));
            }
            else {
                addItem(new ClickableItem(type.getSlot(), new ItemFactory(Material.INK_SACK, (byte) 8)
                        .setName(I18n.get(profile, "graphics.name", type.getName(), ""))
                        .setLore(StringUtil.breakUp(I18n.get(profile, "graphics." + type.getId() + ".desc")))
                        .build(), true) {
                    @Override public void onClick(Player player, ClickType clickType) {
                        graphicsManager.changeGraphics(profile, player, type);
                        new GraphicsInventory(cosmeticManager, graphicsManager, profile).openInventory(player);
                    }
                });
            }
        }

        addItem(new ClickableItem(27, close.build(), true) {
            @Override public void onClick(Player player, ClickType clickType) {
                new ProfileInventory(cosmeticManager, graphicsManager, profile).openInventory(player);
            }
        });
    }
}
