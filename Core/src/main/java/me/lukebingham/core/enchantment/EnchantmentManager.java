package me.lukebingham.core.enchantment;

import com.google.common.collect.Lists;
import org.bukkit.command.defaults.EnchantCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

/**
 * Created by LukeBingham on 20/04/2017.
 */
public final class EnchantmentManager {

    private static final List<MockEnchantment> enchantments;

    static {
        enchantments = Lists.newArrayList();
    }

    public void registerAll() {
        try{
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);

            try {
                for(Enchantment enchantment : enchantments) Enchantment.registerEnchantment(enchantment);
            } catch (IllegalArgumentException e){
                e.printStackTrace();//Enchantment ID taken.
            }

//            Throwing an exception.
//            Enchantment.stopAcceptingRegistrations();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<MockEnchantment> getEnchantments() {
        return enchantments;
    }

    public void addEnchantment(MockEnchantment enchantment) {
        enchantments.add(enchantment);
    }

    public static Optional<MockEnchantment> getEnchantmentById(int id) {
        return enchantments.stream().filter(e -> e.getId() == id).findFirst();
    }

    public static Optional<MockEnchantment> getEnchantmentByClass(Class<? extends MockEnchantment> enchant) {
        return enchantments.stream().filter(e -> e.getClass().getSimpleName().equals(enchant.getClass().getSimpleName())).findFirst();
    }
}
