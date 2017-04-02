package me.lukebingham.core.inventory.item;

import org.bukkit.inventory.ItemStack;

/**
 * Created by LukeBingham on 24/02/2017.
 */
public class MenuItem {

    private final ItemStack itemStack;
    private int index;
    private final boolean cancelOnClick;

    public MenuItem(int index, ItemStack itemStack, boolean cancelOnClick) {
        this.index = index;
        this.itemStack = itemStack;
        this.cancelOnClick = cancelOnClick;
    }

    public MenuItem(int index, ItemStack itemStack) {
        this(index, itemStack, true);
    }

    public final ItemStack getItemStack() {
        return itemStack;
    }

    public final int getIndex() {
        return index;
    }

    public final boolean isCancelOnClick() {
        return cancelOnClick;
    }

    public final void setIndex(int index) {
        this.index = index;
    }
}
