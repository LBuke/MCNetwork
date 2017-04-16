package me.lukebingham.gta.guns.inventory;

import com.google.common.collect.Lists;
import me.lukebingham.core.inventory.MenuModule;
import me.lukebingham.core.inventory.item.ClickableItem;
import me.lukebingham.core.inventory.item.DummyItem;
import me.lukebingham.core.util.factory.ItemFactory;
import me.lukebingham.gta.guns.Gun;
import me.lukebingham.gta.guns.GunManager;
import me.lukebingham.util.C;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.Arrays;
import java.util.List;

/**
 * Created by LukeBingham on 13/04/2017.
 */
public class GunMenu extends MenuModule {

    public GunMenu(GunManager gunManager) {
        super(6, "Guns (TEST)");

        int x = 0;
        for(Gun gun : gunManager.getGuns()) {

            List<String> lore = Lists.newArrayList();
            lore.addAll(Arrays.asList(
                    "",
                    GunManager.getProgress(gunManager.getDamage(gun).value()) + C.WHITE + " Damage",
                    GunManager.getProgress(gunManager.getAccuracy(gun).value()) + C.WHITE + " Accuracy",
                    GunManager.getProgress(gunManager.getFireRate(gun).value()) + C.WHITE + " Fire Rate",
                    GunManager.getProgress(gunManager.getRange(gun).value()) + C.WHITE + " Range",
                    "",
                    C.GRAY + gun.getGunType().name()
            ));

            ItemFactory item = new ItemFactory(gun.getGunItem()).setDurability(gun.getGunId())
                    .setName(gun.getName()).setLore(lore);

            addItem(new ClickableItem(++x, item.build(), true) {
                @Override public void onClick(Player player, ClickType clickType) {
                    player.getInventory().addItem(item.build());
                    player.updateInventory();
                }
            });
        }
    }
}
