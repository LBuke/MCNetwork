package me.lukebingham.core;

import me.lukebingham.core.util.Component;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

/**
 * Created by LukeBingham on 22/02/2017.
 */
public interface Core {

    /**
     * @return Name of the plugin
     */
    String getPluginName();

    /**
     * @return Collection of {@link me.lukebingham.core.util.Component}'s
     */
    HashSet<Component> getComponents();

    /**
     * @return Plugin
     */
    JavaPlugin getPlugin();
}
