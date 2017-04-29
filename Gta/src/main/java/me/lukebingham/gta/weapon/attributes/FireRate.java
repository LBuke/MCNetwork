package me.lukebingham.gta.weapon.attributes;

import java.lang.annotation.*;

/**
 * Created by LukeBingham on 13/04/2017.
 */
@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FireRate {
    /**
     * @return Fire Rate floating point
     */
    float value();
}
