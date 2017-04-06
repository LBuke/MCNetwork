package me.lukebingham.build.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by LukeBingham on 06/04/2017.
 */
public final class SpawnCommand implements CommandExecutor {

    private final Location spawnLocation;

    public SpawnCommand() {
        this.spawnLocation = new Location(Bukkit.getWorld("world"), 0, 50, 0);
    }

    @Override
    public final boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return true;
        ((Player) commandSender).teleport(spawnLocation);
        return true;
    }
}
