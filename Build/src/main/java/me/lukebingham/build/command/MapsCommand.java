package me.lukebingham.build.command;

import me.lukebingham.database.Database;
import me.lukebingham.util.C;
import me.lukebingham.util.ServerType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * Created by LukeBingham on 07/04/2017.
 */
public class MapsCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public MapsCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length < 1) {
            commandSender.sendMessage(C.RED + "Incorrect arguments, /maps <ServerType>");
            return true;
        }

        ServerType serverType;
        try {
            serverType = ServerType.valueOf(strings[0].toUpperCase());
        } catch (Exception e) {
            commandSender.sendMessage(C.RED + "Server type '" + strings[0].toUpperCase() + "' cannot be recognised.");
            commandSender.sendMessage(C.YELLOW + "Listing all available server types:");
            for(ServerType type : ServerType.values()) {
                commandSender.sendMessage(C.GRAY + type.name());
            }
            return true;
        }

        try {
            File parent = new File(plugin.getServer().getWorldContainer().getCanonicalPath() + "/../../Template/BUILD/" + serverType.name());
            StringBuilder builder = new StringBuilder();
            for(String file : parent.list()) {
                builder.append(C.GRAY + file + C.WHITE + ", ");
            }

            commandSender.sendMessage(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
