package me.lukebingham.core.graphics;

import me.lukebingham.core.graphics.type.ExtremeGraphics;
import me.lukebingham.core.graphics.type.HighGraphics;
import me.lukebingham.core.graphics.type.LowGraphics;
import me.lukebingham.core.graphics.type.MediumGraphics;
import me.lukebingham.core.util.C;

/**
 * Created by LukeBingham on 26/03/2017.
 */
public enum GraphicsType {
    LOW(1, 10, C.GREEN + "Low", LowGraphics.class),
    MEDIUM(2, 12, C.GOLD + "Medium", MediumGraphics.class),
    HIGH(3, 14, C.RED + "High", HighGraphics.class),
    EXTREME(4, 16, C.DARK_RED + "Extreme", ExtremeGraphics.class),
    ;

    private final int id, slot;
    private final String name;
    protected final Class<? extends ClientGraphics> clazz;

    GraphicsType(int id, int slot, String name, Class<? extends ClientGraphics> clazz) {
        this.id = id;
        this.slot = slot;
        this.name = name;
        this.clazz = clazz;
    }

    public final int getId() {
        return id;
    }

    public final int getSlot() {
        return slot;
    }

    public final String getName() {
        return name;
    }

    public static GraphicsType getById(int id) {
        for(GraphicsType type : values()) {
            if(type.getId() == id) return type;
        }

        return GraphicsType.HIGH;
    }
}
