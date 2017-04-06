package me.lukebingham.build.util;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;

/**
 * Created by LukeBingham on 05/04/2017.
 */
public class WorldUtil {

    public static World generateWorld(String worldName, World.Environment environment, boolean autoSave) {
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            WorldCreator creator = new WorldCreator(worldName);
            creator.environment(environment);
            creator.generateStructures(false);
            world = creator.createWorld();
            world.setAutoSave(autoSave);
            world.setTime(0);
            world.setGameRuleValue("doDaylightCycle", "false");
            return world;
        }
        return null;
    }

    public static void unloadWorld(World world, boolean save) {
        if(world == null) return;
        Bukkit.unloadWorld(world, save);
    }

    public static boolean isWorldFile(File file) {
        if(!file.isDirectory()) return false;
        for(File f : file.listFiles()) {
            if(f.getName().equals("data.yml"))
                return true;
        }

        return false;
    }
}
