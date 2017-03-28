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

    private int id, slot;
    private String name;
    protected Class<? extends ClientGraphics> clazz;

    GraphicsType(int id, int slot, String name, Class<? extends ClientGraphics> clazz) {
        this.id = id;
        this.slot = slot;
        this.name = name;
        this.clazz = clazz;
    }

    public int getId() {
        return id;
    }

    public int getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }

    public static GraphicsType getById(int id) {
        for(GraphicsType type : values()) {
            if(type.getId() == id) return type;
        }

        return GraphicsType.HIGH;
    }
}
