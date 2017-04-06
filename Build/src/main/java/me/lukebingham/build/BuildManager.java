package me.lukebingham.build;

import com.google.common.collect.Lists;
import me.lukebingham.build.util.WorldUtil;
import me.lukebingham.util.Callback;
import me.lukebingham.util.DoubleCallback;
import me.lukebingham.util.ServerType;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.List;

/**
 * Created by LukeBingham on 05/04/2017.
 */
public final class BuildManager implements Listener {

    private final JavaPlugin plugin;

    public BuildManager(JavaPlugin plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    protected void onPlayerQuit(PlayerQuitEvent event) {
        World from = event.getPlayer().getWorld();
        if (from.getName().equalsIgnoreCase("world")) return;
        unloadAndDelete(from, true, (done) -> {});
    }

    @EventHandler
    protected void onLeaveWorld(PlayerChangedWorldEvent event) {
        if (event.getFrom().getName().equalsIgnoreCase("world")) return;
        unloadAndDelete(event.getFrom(), true, (done) -> {});
    }

    protected void unloadAndDelete(World from, boolean async, Callback<Boolean> callback) {
        from.save();
        if(!async) {
            try {
                String worldName = from.getName();
                File file = new File(Bukkit.getWorldContainer(), worldName);

                File data = new File(file, "data.yml");
                if (!data.exists()) return;

                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(data);
                ServerType serverType = ServerType.valueOf(configuration.getString("server"));
                File to = new File(Bukkit.getWorldContainer().getAbsolutePath() + "/../../Template/BUILD/" + serverType.name() + "/" + worldName);
                FileUtils.deleteDirectory(to);
                if (!to.exists()) to.mkdirs();

                WorldUtil.unloadWorld(from, true);
                FileUtils.copyDirectory(file, to);

                FileUtils.deleteDirectory(file);

                callback.call(true);
            } catch (Exception e) {
                e.printStackTrace();
                callback.call(false);
            }

            return;
        }

        new BukkitRunnable() {
            @Override public void run() {
                try {
                    String worldName = from.getName();
                    File file = new File(Bukkit.getWorldContainer(), worldName);

                    File data = new File(file, "data.yml");
                    if (!data.exists()) return;

                    YamlConfiguration configuration = YamlConfiguration.loadConfiguration(data);
                    ServerType serverType = ServerType.valueOf(configuration.getString("server"));
                    File to = new File(Bukkit.getWorldContainer().getAbsolutePath() + "/../../Template/BUILD/" + serverType.name() + "/" + worldName);
                    FileUtils.deleteDirectory(to);
                    if (!to.exists()) to.mkdirs();

                    WorldUtil.unloadWorld(from, true);
                    FileUtils.copyDirectory(file, to);

                    FileUtils.deleteDirectory(file);

                    callback.call(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.call(false);
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void parse(int y, ServerType serverType, World world, int radius, List<String> data, Callback<List<Location>> callback) {
        List<Location> list = Lists.newArrayList();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            for(int x = -radius; x < radius; x++) {
                for(int z = -radius; z < radius; z++) {
                    Location location = new Location(world, x, y, z);
                    if(location.getBlock().getType() == Material.AIR) continue;
                    boolean d = false;
                    for(String s : data) {
                        if(location.getBlock().getType().name().equalsIgnoreCase(s)) {
                            d = true;
                            break;
                        }
                    }

                    if(d) list.add(location);
                }
            }

            callback.call(list);
        });
    }
}
