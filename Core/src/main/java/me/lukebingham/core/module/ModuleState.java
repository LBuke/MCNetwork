package me.lukebingham.core.module;

import java.lang.annotation.*;

/**
 * Created by LukeBingham on 23/02/2017.
 */
@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleState {

    /**
     * This value will determine which state the current module is in.
     *
     * @return Plugin State
     */
    PluginState state();
}
