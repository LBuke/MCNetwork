package me.lukebingham.core.inventory;

import me.lukebingham.core.inventory.item.MenuItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LukeBingham on 24/02/2017.
 */
public abstract class MenuModule implements InventoryHolder {

    private List<MenuItem> items;

    private Inventory inventory;

    private int rows = 0;
    private String title;
    private final boolean resetCursor, exitNullClick, openParentOnExit;

    public MenuModule(int rows, String title, boolean resetCursor, boolean exitNullClick, boolean openParentOnExit) {
        if (rows > 6 || rows < 1) {
            if (rows > 6) this.rows = 6;
            if (rows < 1) this.rows = 1;
        }

        if (this.rows == 0) this.rows = rows;
        this.title = title;
        this.resetCursor = resetCursor;
        this.exitNullClick = exitNullClick;
        this.openParentOnExit = openParentOnExit;
        this.inventory = Bukkit.createInventory(this, rows * 9, title);

        items = new ArrayList<>();
    }

    public MenuModule(int rows, String title) {
        this(rows, title, false, false, false);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void addItem(MenuItem item) {
        inventory.setItem(item.getIndex(), item.getItemStack());
        items.add(item);
    }

    public boolean containsItem(ItemStack itemStack) {
        boolean result = false;
        for (MenuItem item : items) {
            if (!item.getItemStack().equals(itemStack))
                continue;

            result = true;
            break;
        }

        return result;
    }

    public MenuItem getInventoryItem(ItemStack itemStack) {
        for (MenuItem item : items) {
            if (!item.getItemStack().equals(itemStack))
                continue;

            return item;
        }

        return null;
    }

    public MenuItem getInventoryItem(int index) {
        for (MenuItem item : items) {
            if (item.getIndex() != index)
                continue;

            return item;
        }

        return null;
    }

    public void openInventory(Player player) {
        if (resetCursor) {
            player.closeInventory();
        }

        player.openInventory(inventory);
    }

    public boolean isExitNullClick() {
        return exitNullClick;
    }

    public int getRows() {
        return rows;
    }

    public String getTitle() {
        return title;
    }
}
