package me.lukebingham.core.packet;

import me.lukebingham.core.util.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by LukeBingham on 19/03/2017.
 */
public class PacketComponent implements Component {

    @Override
    public void onLoad() {
        Bukkit.getOnlinePlayers().forEach(PacketHandler::hook);
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(PacketHandler::unHook);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        PacketHandler.hook(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        PacketHandler.unHook(event.getPlayer());
    }

}
