package me.lukebingham.build.util;

import org.bukkit.Material;

/**
 * Created by LukeBingham on 06/04/2017.
 */
public final class BlockData implements Comparable<BlockData> {
    private final Material material;
    private final byte data;

    public BlockData(Material material, byte data) {
        this.material = material;
        this.data = data;
    }

    public BlockData(Material material) {
        this(material, (byte) 0);
    }

    public Material getMaterial() {
        return material;
    }

    public byte getData() {
        return data;
    }

    @Override public int compareTo(BlockData o) {
        return material == o.material && data == o.getData() ? 1 : 0;
    }
}
