package me.lukebingham.core.util.factory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

/**
 * Created by LukeBingham on 24/02/2017.
 */
public class ItemFactory extends Factory<ItemStack> {

    private ItemMeta itemMeta;

    public ItemFactory(Material material, byte data) {
        this.object = new ItemStack(material, 1, data);
        this.itemMeta = object.getItemMeta();
    }

    public ItemFactory(Material material) {
        this(material, (byte) 0);
    }

    public ItemFactory setAmount(int amount) {
        if (amount > 64) amount = 64;
        object.setAmount(amount);
        return this;
    }

    public ItemFactory setName(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    public ItemFactory setLore(List<String> lore) {
        itemMeta.setLore(lore);
        return this;
    }

    public ItemFactory setLore(String... lore) {
        itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemFactory setOwner(String name) {
        if (object.getType().equals(Material.SKULL_ITEM)) {
            SkullMeta meta = (SkullMeta) itemMeta;
            meta.setOwner(name);
        }
        return this;
    }

    public ItemStack build() {
        object.setItemMeta(itemMeta);
        return object;
    }

    public ItemFactory setData(byte data) {
        object.setDurability(data);
        return this;
    }
}
