package me.lukebingham.core.util;

import net.minecraft.server.v1_8_R3.Vector3f;
import org.bukkit.util.EulerAngle;

/**
 * Created by LukeBingham on 30/04/2017.
 */
public final class MathUtil {

    public static EulerAngle fromVector3f(Vector3f old) {
        return new EulerAngle(Math.toRadians((double)old.getX()), Math.toRadians((double)old.getY()), Math.toRadians((double)old.getZ()));
    }

    public static Vector3f fromEulerAngle(EulerAngle old) {
        return new Vector3f((float)Math.toDegrees(old.getX()), (float)Math.toDegrees(old.getY()), (float)Math.toDegrees(old.getZ()));
    }
}
