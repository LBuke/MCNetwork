package me.lukebingham.gta.weapon.gun.inventory;

import com.google.common.collect.Lists;
import me.lukebingham.core.enchantment.MockEnchantment;
import me.lukebingham.core.enchantment.EnchantmentManager;
import me.lukebingham.core.inventory.MenuModule;
import me.lukebingham.core.inventory.item.ClickableItem;
import me.lukebingham.core.util.ItemUtil;
import me.lukebingham.core.util.factory.ItemFactory;
import me.lukebingham.gta.weapon.gun.Gun;
import me.lukebingham.gta.weapon.gun.GunManager;
import me.lukebingham.util.C;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Created by LukeBingham on 13/04/2017.
 */
public class GunMenu extends MenuModule {

    public GunMenu(GunManager gunManager) {
        super(6, "Guns (TEST)");

        int x = 0;
        for(Gun gun : gunManager.getGuns()) {

            Deque<String> lore = Lists.newLinkedList();
            lore.addAll(Arrays.asList(
                    "",
                    GunManager.getProgress(gunManager.getDamage(gun).value()) + C.WHITE + " Damage",
                    GunManager.getProgress(gunManager.getAccuracy(gun).value()) + C.WHITE + " Accuracy",
                    GunManager.getProgress(gunManager.getFireRate(gun).value()) + C.WHITE + " Fire Rate",
                    GunManager.getProgress(gunManager.getRange(gun).value()) + C.WHITE + " Range",
                    "",
                    C.GRAY + gun.getGunType().name()
            ));

            ItemFactory item = new ItemFactory(gun.getGunItem()).setDurability(gun.getGunUniqueIdentifier())
                    .setName(gun.getName()).setLore(lore).setEnchantment(Enchantment.DURABILITY, 3)
                    .setUnbreakable(true);

            if(gun.getGunItem() == Material.DIAMOND_PICKAXE) {
                Optional<MockEnchantment> optional1 = EnchantmentManager.getEnchantmentById(101);
                optional1.ifPresent(mockEnchantment -> item.setEnchantment(mockEnchantment, 2));

                Optional<MockEnchantment> optional2 = EnchantmentManager.getEnchantmentById(102);
                optional2.ifPresent(mockEnchantment -> item.setEnchantment(mockEnchantment, 2));

                Optional<MockEnchantment> optional3 = EnchantmentManager.getEnchantmentById(103);
                optional3.ifPresent(mockEnchantment -> item.setEnchantment(mockEnchantment, 4));
            }

            addItem(new ClickableItem(++x, item.build(), true) {
                @Override public void onClick(Player player, ClickType clickType) {
                    ItemStack itemStack = item.build();
                    Optional<MockEnchantment> optional = EnchantmentManager.getEnchantmentById(103);
                    player.getInventory().addItem(optional.isPresent() ? ItemUtil.removeEnchant(itemStack, optional.get()) : itemStack);

                    player.updateInventory();
                }
            });
        }
    }
}
