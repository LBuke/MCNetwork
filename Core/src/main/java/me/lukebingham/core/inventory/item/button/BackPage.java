package me.lukebingham.core.inventory.item.button;

import me.lukebingham.core.inventory.item.ClickableItem;
import me.lukebingham.core.util.factory.ItemFactory;
import org.bukkit.Material;

/**
 * Created by LukeBingham on 24/02/2017.
 */
public class BackPage extends ClickableItem {

    public BackPage(int index) {
        super(index, new ItemFactory(Material.ARROW).setName("Back Page").build(), true);
    }
}
