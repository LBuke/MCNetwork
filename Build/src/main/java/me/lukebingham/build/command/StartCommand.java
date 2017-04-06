package me.lukebingham.build.command;

import me.lukebingham.build.util.WorldUtil;
import me.lukebingham.util.C;
import me.lukebingham.util.DoubleCallback;
import me.lukebingham.util.ServerType;
import org.apache.commons.io.FileUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

/**
 * Created by LukeBingham on 05/04/2017.
 */
public final class StartCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public StartCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public final boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return true;
        if (strings.length < 2) return true;

        Player sender = (Player) commandSender;
        String name = strings[0], type = "NORMAL";
        if(name.equalsIgnoreCase("world")) return true;
        if(strings.length >= 3) type = strings[2].toUpperCase();

        ServerType serverType;
        try {
            serverType = ServerType.valueOf(strings[1].toUpperCase());
        } catch (Exception e) {
            sender.sendMessage("Server type '" + strings[1].toUpperCase() + "' couldn't be recognised.");
            return true;
        }

        sender.sendMessage(C.GOLD + "MAP " + C.YELLOW + "Loading template map file.");
        createWorld(sender, name, serverType, type, (done, world) -> {
            if(done) {
                sender.sendMessage(C.GOLD + "MAP " + C.YELLOW + name + " map has created/loaded.");
                sender.teleport(world.getSpawnLocation());
            }
            else {
                sender.sendMessage(C.DARK_RED + "MAP " + C.RED + name + " could not be created, try again.");
            }
        });

        return true;
    }

    private void createWorld(Player player, String name, ServerType server, String type, DoubleCallback<Boolean, World> callback) {
        try {
            File template = new File(plugin.getServer().getWorldContainer().getCanonicalPath() + "/../../Template/BUILD/template");
            File existing = new File(plugin.getServer().getWorldContainer().getCanonicalPath() + "/../../Template/BUILD/" + server.name() + "/" + name);
            if(existing.exists()) {
                File worldContainer = new File(plugin.getServer().getWorldContainer(), name);
                if (!worldContainer.exists()) worldContainer.mkdir();
                FileUtils.copyDirectory(existing, worldContainer);

                new BukkitRunnable() {
                    public void run() {

                        World.Environment environment = World.Environment.NORMAL;
                        if(type.contains("END")) environment = World.Environment.THE_END;
                        else if(type.contains("NETHER")) environment = World.Environment.NETHER;

                        World world = WorldUtil.generateWorld(name, environment, false);
                        callback.call(true, world);
                    }
                }.runTaskLater(plugin, 20L);
                return;
            }

            File worldContainer = new File(plugin.getServer().getWorldContainer(), name);
            if (!worldContainer.exists()) worldContainer.mkdir();
            FileUtils.copyDirectory(template, worldContainer);

            File data = new File(worldContainer, "data.yml");
            if(data.exists()) {
                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(data);
                configuration.set("authors", Collections.singletonList(player.getName()));
                configuration.set("name", name);
                configuration.set("server", server.name());
                configuration.set("environment", type);
                configuration.save(data);
            }


            new BukkitRunnable() {
                public void run() {

                    World.Environment environment = World.Environment.NORMAL;
                    if(type.contains("END")) environment = World.Environment.THE_END;
                    else if(type.contains("NETHER")) environment = World.Environment.NETHER;

                    World world = WorldUtil.generateWorld(name, environment, false);
                    callback.call(true, world);
                }
            }.runTaskLater(plugin, 20L);
        }
        catch (IOException e) {
            e.printStackTrace();
            callback.call(false, null);
        }
    }
}
