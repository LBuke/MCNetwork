package me.lukebingham.core.command;

import me.lukebingham.core.Core;
import me.lukebingham.core.util.ServerUtil;
import me.lukebingham.core.util.rank.Role;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by LukeBingham on 17/03/2017.
 */
public class StopCommand extends CommandFactory<CommandSender> implements StaffCommand{

    private Core core;

    /**
     * Construct a new command.
     */
    public StopCommand(Core core) {
        super("stop", "Stop the server.");
        this.core = core;

        new CommandRemover("stop");
    }

    /**
     * This is fired when the command is executed.
     *
     * @param sender Sender of the command
     * @param args   Command arguments
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
        Bukkit.broadcastMessage("IMPORTANT, This server needs a restart!");

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendMessage("Sending you to Lobby_x.");
            //Send to lobby
        });

        core.getComponents().forEach(ServerUtil::unregisterComponent);

        Bukkit.getScheduler().runTaskLater(core.getPlugin(), Bukkit::shutdown, 40);
    }

    /**
     * Only staff with the specified role or higher can use the command.
     *
     * @return Role value
     */
    @Override
    public Role getRequiredRole() {
        return Role.DEVELOPER;
    }
}
