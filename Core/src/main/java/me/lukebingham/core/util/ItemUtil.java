package me.lukebingham.core.util;

import me.lukebingham.core.enchantment.MockEnchantment;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by LukeBingham on 21/04/2017.
 */
public final class ItemUtil {

    public static ItemStack removeEnchant(ItemStack itemStack, Enchantment enchantment) {
        if(itemStack.containsEnchantment(enchantment)) {
            itemStack.removeEnchantment(enchantment);
        }

        return itemStack;
    }

    public static ItemStack removeEnchant(ItemStack itemStack, MockEnchantment enchantment) {
        if(!itemStack.hasItemMeta()) return itemStack;
        ItemMeta meta = itemStack.getItemMeta();
        if(!meta.hasLore()) return itemStack;

        if(itemStack.containsEnchantment(enchantment)) {
            itemStack.removeEnchantment(enchantment);
        }

        List<String> lore = meta.getLore();
        Pattern pattern = Pattern.compile("^.*(?:Engine )(?:\\D{1,5}|enchantment\\.level\\.\\d+|\\d+%)$");
        int i = 0;
        for(String entry : lore) {
            if(pattern.matcher(entry).find()) break;
            i++;
        }

        lore.remove(i);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        return itemStack;
    }
}
