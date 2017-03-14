package me.lukebingham.core;

import me.lukebingham.core.util.Component;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

/**
 * Created by LukeBingham on 22/02/2017.
 */
public interface Core {
    String getPluginName();
    HashSet<Component> getComponents();
}
