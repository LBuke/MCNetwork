package me.lukebingham.gta.attributes;

import java.lang.annotation.*;

/**
 * Created by LukeBingham on 13/04/2017.
 */
@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Damage {
    /**
     * @return Damage/Power floating point
     */
    float value();
}
