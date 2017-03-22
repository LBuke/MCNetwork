package me.lukebingham.core.command;

import me.lukebingham.core.util.ServerUtil;
import org.apache.commons.codec.language.bm.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LukeBingham on 16/03/2017.
 */
public class CommandRemover {

    private CommandMap map = new SimpleCommandMap(null);

    /**
     * Remove/un-register a command.
     */
    public CommandRemover(JavaPlugin plugin, String command) {
        if (plugin.getServer() != null && plugin.getServer().getPluginManager() instanceof SimplePluginManager) {
            final SimplePluginManager manager = (SimplePluginManager) plugin.getServer().getPluginManager();
            try {
                final Field field = SimplePluginManager.class.getDeclaredField("commandMap");
                field.setAccessible(true);
                map = (CommandMap) field.get(manager);
                final Field field2 = SimpleCommandMap.class.getDeclaredField("knownCommands");
                field2.setAccessible(true);
                @SuppressWarnings("unchecked")
                final Map<String, Command> knownCommands = (Map<String, org.bukkit.command.Command>) field2.get(map);
                for (final Map.Entry<String, org.bukkit.command.Command> entry : knownCommands.entrySet()) {
                    if (entry.getKey().equals(command)) {
                        entry.getValue().unregister(map);
                    }
                }
                knownCommands.remove(command);
            } catch (IllegalArgumentException | NoSuchFieldException | IllegalAccessException | SecurityException e) {
                e.printStackTrace();
            }
        }
    }
}
