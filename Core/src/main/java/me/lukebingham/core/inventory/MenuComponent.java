package me.lukebingham.core.inventory;

import me.lukebingham.core.inventory.item.ClickableItem;
import me.lukebingham.core.inventory.item.MenuItem;
import me.lukebingham.core.util.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by LukeBingham on 24/02/2017.
 */
public class MenuComponent implements Component {

    @EventHandler (ignoreCancelled = true)
    protected void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null) return;
        if (!(event.getInventory().getHolder() instanceof MenuModule)) return;

        MenuModule goInv = (MenuModule) event.getInventory().getHolder();
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();

        //Is the clicked location outside of the Inventory bounding
        if (clicked == null) {
            if (!goInv.isExitNullClick()) {
                player.closeInventory();
            }

            //TODO: Open parent inventory if not null
            return;
        }

        //If the ItemStack cannot be recognized as 'MenuItem'
        if (!goInv.containsItem(clicked)) {
            event.setCancelled(true);
            return;
        }

        MenuItem goItem = goInv.getInventoryItem(event.getRawSlot());
        if (goItem == null) return;

        if (goItem.isCancelOnClick()) {
            event.setCancelled(true);
        }

        if (goItem instanceof ClickableItem) {
            ((ClickableItem) goItem).onClick(player, event.getClick());
        }
    }
}
