package me.lukebingham.core.graphics.type;

import com.google.common.collect.Maps;
import me.lukebingham.core.graphics.ClientGraphics;
import me.lukebingham.core.graphics.GraphicsType;
import me.lukebingham.core.util.BlockData;
import org.bukkit.Material;

import java.util.HashMap;

/**
 * Created by LukeBingham on 26/03/2017.
 */
public final class HighGraphics extends ClientGraphics {

    private static final HashMap<BlockData, BlockData> blocks;

    static {
        blocks = Maps.newHashMap();
    }

    public HighGraphics() {
        super(GraphicsType.HIGH);
    }

    /**
     * This will return true if the graphics option changes blocks client side.
     *
     * @return block change value
     */
    @Override
    public final boolean hasBlockChanges() {
        return true;
    }

    /**
     * This method will return block change data, Key being the original block and value being the source.
     * @return block change data
     */
    @Override
    public final HashMap<BlockData, BlockData> getBlockData() {
        return blocks;
    }

    @Override
    public final BlockData getBlockData(Material material, byte data) {
        for(BlockData blockData : blocks.keySet()) {
            if(data == -1 && blockData.getMaterial() == material) return blockData;
            if(blockData.getMaterial() == material && blockData.getData() == data) return blockData;
        }

        return null;
    }

    public final void addBlock(BlockData origin, BlockData source) {
        blocks.put(origin, source);
    }

    /**
     * This will be fired once at start-up.
     */
    @Override
    protected final void init() {

    }
}
