package me.lukebingham.core.util;

import me.lukebingham.core.CorePlugin;
import me.lukebingham.util.C;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created by LukeBingham on 22/02/2017.
 */
public final class ServerUtil {

    public static final String SERVER_NAME = "ServerName";

    private static JavaPlugin PLUGIN;

    static {
        PLUGIN = getJavaPlugin();
    }

    /**
     * Sends the console a direct message.
     *
     * @param log The log message
     */
    public static void log(String log) {
        Bukkit.getConsoleSender().sendMessage(log);
    }

    public static void logError(String log) {
        log("[" + C.RED + "ERROR" + C.RESET + "] " + log);
    }

    public static void logWarn(String log) {
        Bukkit.getConsoleSender().sendMessage("[" + C.YELLOW + "WARN" + C.RESET + "] " + log);
    }

    public static void logDebug(String log) {
        Bukkit.getConsoleSender().sendMessage("[" + C.LIGHT_PURPLE + "DEBUG" + C.RESET + "] " + log);
    }

    public static JavaPlugin getJavaPlugin() {
        if(PLUGIN != null) return PLUGIN;
        Optional<Plugin> optional = Arrays.stream(Bukkit.getPluginManager().getPlugins()).filter(plugin -> plugin.getDescription().getDescription() != null && isValidPlugin(plugin.getDescription().getDescription())).findFirst();
        return optional.isPresent() ? PLUGIN = (JavaPlugin) optional.get() : null;
    }

    private static boolean isValidPlugin(String description) {
        Pattern pattern = Pattern.compile("^.*" + SERVER_NAME + "-.*");
        return pattern.matcher(description).find();
    }

    public static void sendPacket(Player player, Packet<?>... packets) {
        for(Packet<?> packet : packets) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }

    /**
     * This method will register a component(s) (listener)
     *
     * @param components Array of component
     */
    public static void registerComponent(Component... components) {
        Arrays.asList(components).forEach(component -> {
            Bukkit.getPluginManager().registerEvents(component, PLUGIN);
            component.onLoad();
            component.log(true);
            ((CorePlugin) PLUGIN).getComponents().add(component);
        });
    }

    /**
     * This method will un-register a component(s) (listener)
     *
     * @param components Array of component
     */
    public static void unregisterComponent(Component... components) {
        Arrays.asList(components).forEach(component -> {
            if(component.disableAble()) {
                HandlerList.unregisterAll(component);
                component.onDisable();
                component.log(false);
            }
        });
    }

    public static void runTask(Runnable runnable) {
        Bukkit.getScheduler().runTask(getJavaPlugin(), runnable);
    }

    public static void runTaskLater(long time, Runnable runnable) {
        Bukkit.getScheduler().runTaskLater(PLUGIN, runnable, time);
    }
}
