package me.lukebingham.core.util;

/**
 * Created by LukeBingham on 25/02/2017.
 */
public final class InventoryUtil {

    public static int[] getSlots(int rows) {
        if(rows > 4 || rows < 1) rows = 1;
        if(rows == 1) return new int[]{10,11,12,13,14,15,16};
        if(rows == 2) return new int[]{10,11,12,13,14,15,16 , 19,20,21,22,23,24,25};
        if(rows == 3) return new int[]{10,11,12,13,14,15,16 , 19,20,21,22,23,24,25 , 28,29,30,31,32,33,34};
        if(rows == 4) return new int[]{10,11,12,13,14,15,16 , 19,20,21,22,23,24,25 , 28,29,30,31,32,33,34 , 37,38,39,40,41,42,43};
        return new int[]{10,11,12,13,14,15,16};
    }

    public enum SlotType {
        MIDDLE
    }
}
