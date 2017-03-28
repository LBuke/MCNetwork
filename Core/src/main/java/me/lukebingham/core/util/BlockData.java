package me.lukebingham.core.util;

import org.bukkit.Material;

/**
 * Created by LukeBingham on 26/03/2017.
 */
public class BlockData {

    private Material material;
    private byte data;

    public BlockData(Material material, byte data) {
        this.material = material;
        this.data = data;
    }

    public BlockData(Material material, int data) {
        this.material = material;
        this.data = (byte) data;
    }

    public BlockData(Material material) {
        this(material, (short) 0);
    }

    public Material getMaterial() {
        return material;
    }

    public byte getData() {
        return data;
    }
}
