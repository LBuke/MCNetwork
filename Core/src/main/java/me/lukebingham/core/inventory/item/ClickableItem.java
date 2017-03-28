package me.lukebingham.core.inventory.item;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * Created by LukeBingham on 24/02/2017.
 */
public class ClickableItem extends MenuItem {

    public ClickableItem(int index, ItemStack itemStack, boolean cancelOnClick) {
        super(index, itemStack, cancelOnClick);
    }

    public void onClick(Player player, ClickType clickType) {
        System.out.println("[Inventory] Debug > Clicked " + getItemStack().getType().toString());
    }
}
