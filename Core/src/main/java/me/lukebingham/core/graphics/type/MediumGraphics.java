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
public final class MediumGraphics extends ClientGraphics {

    private static final HashMap<BlockData, BlockData> blocks;

    static {
        blocks = Maps.newHashMap();
    }

    public MediumGraphics() {
        super(GraphicsType.MEDIUM);
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
        // Nature
        addBlock(new BlockData(Material.SAPLING), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.SAPLING, 1), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.SAPLING, 2), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.SAPLING, 3), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.SAPLING, 4), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.SAPLING, 5), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.WEB), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.LONG_GRASS, 1), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.LONG_GRASS, 2), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.DEAD_BUSH), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.YELLOW_FLOWER), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.RED_ROSE), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.RED_ROSE, 1), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.RED_ROSE, 2), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.RED_ROSE, 3), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.RED_ROSE, 4), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.RED_ROSE, 5), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.RED_ROSE, 6), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.RED_ROSE, 7), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.RED_ROSE, 8), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.BROWN_MUSHROOM), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.RED_MUSHROOM), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.VINE), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.FLOWER_POT), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.DOUBLE_PLANT), new BlockData(Material.AIR));

        // Tile Entities
        addBlock(new BlockData(Material.STANDING_BANNER), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.WALL_BANNER), new BlockData(Material.AIR));
        addBlock(new BlockData(Material.PAINTING), new BlockData(Material.AIR));
    }
}
