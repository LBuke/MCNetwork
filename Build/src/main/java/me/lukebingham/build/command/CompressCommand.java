package me.lukebingham.build.command;

import me.lukebingham.database.Database;
import me.lukebingham.util.C;
import me.lukebingham.util.FileUtil;
import me.lukebingham.util.ServerType;
import org.apache.commons.io.FileUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by LukeBingham on 07/04/2017.
 */
public class CompressCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final Database database;

    public CompressCommand(JavaPlugin plugin, Database database) {
        this.plugin = plugin;
        this.database = database;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
//        DBCursor cursor = database.getCollection("") Check rank.
        if(!(commandSender instanceof Player)) return true;
        Player sender = (Player) commandSender;

        if(sender.getWorld().getName().equalsIgnoreCase("world")) return true;

        File map = new File(plugin.getServer().getWorldContainer(), sender.getWorld().getName());
        if(!map.exists()) return true;
        File mapData = new File(map, "data.yml");
        if(!mapData.exists()) return true;
        YamlConfiguration config = YamlConfiguration.loadConfiguration(mapData);

        try {
            String name = config.getString("name");
            ServerType serverType = ServerType.valueOf(config.getString("server"));

            File worldFile = new File(plugin.getServer().getWorldContainer(), name);
            File source = new File(plugin.getServer().getWorldContainer().getCanonicalPath() + "/../../Template/BUILD/" + serverType.name() + "/" + name);
            File output = new File(plugin.getServer().getWorldContainer().getCanonicalPath() + "/../../maps/" + serverType.name() + "/" + name + ".zip");
            FileUtils.copyDirectory(worldFile, source);
            FileUtils.forceDelete(new File(source, "uid.dat"));
            FileUtils.forceDelete(new File(source, "session.lock"));
            FileUtils.deleteDirectory(new File(source, "playerdata"));
            FileUtils.deleteDirectory(new File(source, "stats"));

            FileUtil.zip(source, output);

            sender.sendMessage(C.YELLOW + "Map compression complete!");
        }
        catch (Exception e) {
            if(!(e instanceof FileNotFoundException))
                e.printStackTrace();
        }
        return true;
    }
}
