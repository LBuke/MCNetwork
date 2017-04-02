package me.lukebingham.core.util;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;

/**
 * Created by LukeBingham on 30/03/2017.
 */
public final class EntityUtil {

    /**
     * Set the ArmorStand's marker.
     *
     * @param armorStand The {@link org.bukkit.entity.ArmorStand}
     * @param marker If true the ArmorStand's bounding box will be null
     */
    public static void setArmorStandMarker(ArmorStand armorStand, boolean marker) {
        EntityArmorStand nmsArmorStand = ((CraftArmorStand) armorStand).getHandle();
        NBTTagCompound compoundTag = new NBTTagCompound();
        nmsArmorStand.c(compoundTag);
        compoundTag.setBoolean("Marker", marker);
        nmsArmorStand.f(compoundTag);
    }

    /**
     * Set the ArmorStand's marker.
     *
     * @param armorStand The {@link net.minecraft.server.v1_8_R3.EntityArmorStand}
     * @param marker If true the ArmorStand's bounding box will be null
     */
    public static void setArmorStandMarker(EntityArmorStand armorStand, boolean marker) {
        NBTTagCompound compoundTag = armorStand.getNBTTag();
        if(compoundTag == null) compoundTag = new NBTTagCompound();
        armorStand.c(compoundTag);
        compoundTag.setBoolean("Marker", marker);
        armorStand.f(compoundTag);
    }
}
