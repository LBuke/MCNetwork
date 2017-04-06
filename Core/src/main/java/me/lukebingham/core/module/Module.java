package me.lukebingham.core.module;

import me.lukebingham.util.ServerType;

import java.lang.annotation.*;

/**
 * Created by LukeBingham on 23/02/2017.
 */
@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Module {

    String version();

    /**
     * This value will determine which state the current module is in.
     *
     * @return Plugin State
     */
    PluginState state();
    ServerType type();
}
