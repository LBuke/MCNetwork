package me.lukebingham.core.command;

import me.lukebingham.core.util.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

/**
 * Created by LukeBingham on 16/03/2017.
 */
public class CommandRemover {

    private static CommandMap commandMap = null;

    /**
     * Remove/un-register a command.
     */
    public CommandRemover(String command) {
        if (commandMap != null) {
            commandMap.getCommand(command).unregister(commandMap);
            ServerUtil.logDebug("Command(" + command + ") has been removed.");
            return;
        }

        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            commandMap = (CommandMap) field.get(Bukkit.getServer());
            commandMap.getCommand(command).unregister(commandMap);
            ServerUtil.logDebug("Command(" + command + ") has been removed.");
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
