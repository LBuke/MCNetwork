package me.lukebingham.core.graphics;

import me.lukebingham.core.util.BlockData;
import org.bukkit.Material;

import java.util.HashMap;

/**
 * Created by LukeBingham on 26/03/2017.
 */
public interface Graphics {
    /**
     * This will return true if the graphics option changes blocks client side.
     * @return block change value
     */
    boolean hasBlockChanges();

    /**
     * This method will return block change data, Key being the original block and value being the source.
     * @return block change data
     */
    HashMap<BlockData, BlockData> getBlockData();

    BlockData getBlockData(Material material, byte data);
}
