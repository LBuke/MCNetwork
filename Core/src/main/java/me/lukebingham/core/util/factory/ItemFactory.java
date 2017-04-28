package me.lukebingham.core.util.factory;

import me.lukebingham.core.enchantment.EnchantmentManager;
import me.lukebingham.core.enchantment.EnchantmentTier;
import me.lukebingham.core.enchantment.MockEnchantment;
import me.lukebingham.core.enchantment.PercentageEnchantment;
import me.lukebingham.core.util.EnchantmentUtil;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

/**
 * Created by LukeBingham on 24/02/2017.
 */
public final class ItemFactory extends Factory<ItemStack> implements CloneableFactory<ItemFactory> {

    private ItemMeta itemMeta;

    public ItemFactory(Material material, byte data) {
        this.object = new ItemStack(material, 1, data);
        this.itemMeta = object.getItemMeta();
    }

    public ItemFactory(Material material) {
        this(material, (byte) 0);
    }

    public final ItemFactory setAmount(int amount) {
        if (amount > 64) amount = 64;
        object.setAmount(amount);
        return this;
    }

    public final ItemFactory setName(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    public final ItemFactory setLore(List<String> lore) {
        itemMeta.setLore(lore);
        return this;
    }

    public final ItemFactory setLore(Queue<String> lore) {
        itemMeta.setLore((LinkedList<String>) lore);
        return this;
    }

    public final ItemFactory setLore(String... lore) {
        itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public final ItemFactory setOwner(String name) {
        if (object.getType().equals(Material.SKULL_ITEM)) {
            SkullMeta meta = (SkullMeta) itemMeta;
            meta.setOwner(name);
        }
        return this;
    }

    public final ItemFactory setDurability(short durability) {
        object.setDurability(durability);
        return this;
    }

    public final ItemStack build() {
        object.setItemMeta(itemMeta);
        return object;
    }

    public final ItemFactory setData(byte data) {
        object.setDurability(data);
        return this;
    }

    public final ItemFactory setUnsafeEnchantment(Enchantment enchantment, int level) {
        object.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public final ItemFactory setEnchantment(Enchantment enchantment, int level) {
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public final ItemFactory setEnchantment(MockEnchantment enchantment, int level) {
        boolean hasLore = itemMeta.hasLore();
        String[] loreArray = new String[hasLore ? itemMeta.getLore().size() + 1 : 1];
        if(hasLore) {
            for(int i = 1; i < loreArray.length; i++) {
                loreArray[i] = itemMeta.getLore().get(i - 1);
            }
        }

        if(enchantment instanceof PercentageEnchantment)
            loreArray[0] = enchantment.getName((((PercentageEnchantment) enchantment).getStartingPercentage() * level) + "%");
        else if(enchantment instanceof EnchantmentTier)
            loreArray[0] = enchantment.getName("(" + ((EnchantmentTier) enchantment).getTiers()[level-1] + ")");
        else loreArray[0] = enchantment.getName(EnchantmentUtil.toRoman(level));

        return setLore(loreArray);
    }

    public final ItemFactory addFlags(ItemFlag... flags) {
        itemMeta.addItemFlags(flags);
        return this;
    }

    public ItemFactory setGlow() {
        Optional<MockEnchantment> optional = EnchantmentManager.getEnchantmentById(100);
        optional.ifPresent(mockEnchantment -> object.addEnchantment(mockEnchantment, 1));

        return this;
    }

//    public <T> ItemFactory setNBT(String key, T value) {
//        net.minecraft.server.v1_8_R3.ItemStack nmsCopy = CraftItemStack.asNMSCopy(object);
//        NBTTagCompound compoundTag = new NBTTagCompound();
//        nmsCopy.c(compoundTag);
//        compoundTag.set(key, );
//        nmsCopy.f(compoundTag);
//    }

    public ItemFactory setUnbreakable(boolean unbreakable) {
        net.minecraft.server.v1_8_R3.ItemStack nmsCopy = CraftItemStack.asNMSCopy(object);
        NBTTagCompound compoundTag = new NBTTagCompound();
        nmsCopy.c(compoundTag);
        compoundTag.setBoolean("Unbreakable", true);
        return this;
    }

    @Override
    public final ItemFactory clone() {
        ItemFactory clone = new ItemFactory(object.getType(), object.getData().getData());
        clone.itemMeta = this.itemMeta;
        return clone;
    }
}
