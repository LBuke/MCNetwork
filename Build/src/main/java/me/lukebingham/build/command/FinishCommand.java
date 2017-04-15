package me.lukebingham.build.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.lukebingham.build.BuildManager;
import me.lukebingham.build.util.BlockData;
import me.lukebingham.util.*;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipOutputStream;

/**
 * Created by LukeBingham on 06/04/2017.
 */
public final class FinishCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final BuildManager buildManager;

    public FinishCommand(JavaPlugin plugin, BuildManager buildManager) {
        this.plugin = plugin;
        this.buildManager = buildManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return true;
        Player sender = (Player) commandSender;
        if(strings.length < 1) {
            sender.sendMessage(C.RED + "Incorrect arguments, /finish <radius> [data]");
            return true;
        }

        int[] radius = {-1};
        try {
            radius[0] = Integer.parseInt(strings[0]);
        } catch (Exception e) {
            sender.sendMessage(C.RED + "Radius needs to be a numeral.");
            return true;
        }

        List<String> data = Lists.newArrayList();
        data.addAll(Arrays.asList(strings).subList(1, strings.length));

        File map = new File(plugin.getServer().getWorldContainer(), sender.getWorld().getName());
        if(!map.exists()) return true;
        File mapData = new File(map, "data.yml");
        if(!mapData.exists()) return true;
        YamlConfiguration config = YamlConfiguration.loadConfiguration(mapData);

        sender.sendMessage(C.RED + "Parsing & compression queued, Do not leave this world.");

        int[] y = {-1};
        DoubleCallback<Boolean, Integer> callback = (done, count) -> {
            if(count % 16 == 0) {
                String c = C.GOLD + count + C.YELLOW + "/" + C.GOLD + 256;
                sender.sendMessage(C.YELLOW + "Map parsing progress: " + c + C.YELLOW + " (" + radius[0]*radius[0] + ")");
            }

            if(done) {
                sender.sendMessage(C.YELLOW + "Map parsing complete!");
            }
        };

        if(config.contains("data")) config.set("data", null);
        new BukkitRunnable() {
            @Override public void run() {
                y[0] += 1;
                buildManager.parse(y[0], ServerType.valueOf(config.getString("server")), sender.getWorld(), radius[0], data, object -> {
                    HashMap<BlockData, List<String>> map = Maps.newHashMap();
                    for(Location location : object) {
                        if(location.getBlock().getType() == Material.WOOL &&
                                location.getBlock().getRelative(BlockFace.UP).getType() != Material.GOLD_PLATE) continue;

                        BlockData blockData = new BlockData(location.getBlock().getType(), location.getBlock().getData());
                        Optional<BlockData> optional = map.keySet().stream().filter(data -> data.compareTo(blockData) == 1).findAny();
                        List<String> current = optional.isPresent() ? map.get(optional.get()) : Lists.newArrayList();
                        current.add(location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ());
                        map.put(optional.isPresent() ? optional.get() : blockData, current);
                    }

                    for(BlockData blockData : map.keySet()) {
                        List<String> locs = map.get(blockData);
                        if(config.contains("data." + blockData.getMaterial().name() + ";" + blockData.getData())) {
                            locs.addAll(config.getStringList("data." + blockData.getMaterial().name() + ";" + blockData.getData()));
                        }

                        config.set("data." + blockData.getMaterial().name() + ";" + blockData.getData(), locs);
                    }

                    try {
                        config.save(mapData);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                if(y[0] >= 256) {
                    callback.call(true, y[0]);
                    cancel();
                }
                else callback.call(false, y[0]);
            }
        }.runTaskTimer(plugin, 0, 3);
        return false;
    }
}
