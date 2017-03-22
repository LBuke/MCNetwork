package me.lukebingham.core.inventory.example;

import me.lukebingham.core.inventory.MenuModule;
import me.lukebingham.core.inventory.item.DummyItem;
import me.lukebingham.core.inventory.item.button.BackPage;
import me.lukebingham.core.inventory.item.button.NextPage;
import me.lukebingham.core.util.InventoryUtil;
import me.lukebingham.core.util.factory.ItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LukeBingham on 24/02/2017.
 */
public class ShopInventory extends MenuModule {

    public ShopInventory() {
        this(1);
    }

    public ShopInventory(int page) {
        super(6, "Dev Shop (Page " + page + ")", false, true, false);

        int[] slots = InventoryUtil.getSlots(3);

        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < 137; i++) {
            items.add(new ItemStack(Material.STONE));
        }

        int perPage = slots.length, pages = 1, x = items.size(), z = 0;
        if (x / perPage > 0) pages = x / perPage;
        if (x % perPage > 0) pages += 1;
        if (page > pages) return;
        System.out.println((page * perPage) - perPage + " - " + page * perPage);
        for (int i = ((page * perPage) - perPage); i < (page * perPage); i++) {
            if (i >= x) {
                for(int j = z; j < perPage; j++) {//This is to complete the page null spaces.
                    addItem(new DummyItem(slots[z++], new ItemFactory(Material.DIRT).build(), true));
                }
                break;
            }
            addItem(new DummyItem(slots[z++], items.get(i), true));
        }

        if (page < pages) {
            int slot = ((getRows() - 1) * 9) + 9 - 1;
            addItem(new NextPage(slot) {
                @Override
                public void click(Player player, ClickType clickType) {
                    super.click(player, clickType);
                    new ShopInventory(page + 1).openInventory(player);
                }
            });
        }

        if (page > 1) {
            int slot = ((getRows() - 1) * 9);
            addItem(new BackPage(slot) {
                @Override
                public void click(Player player, ClickType clickType) {
                    super.click(player, clickType);
                    new ShopInventory(page - 1).openInventory(player);
                }
            });
        }
    }
}
