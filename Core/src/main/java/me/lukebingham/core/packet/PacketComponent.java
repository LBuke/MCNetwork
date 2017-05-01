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
public final class PacketComponent implements Component {

    @Override
    public final void onLoad() {
        Bukkit.getOnlinePlayers().forEach(PacketModule::hook);
    }

    @Override
    public final void onDisable() {
        Bukkit.getOnlinePlayers().forEach(PacketModule::unHook);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    protected final void onJoin(PlayerJoinEvent event) {
        PacketModule.hook(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    protected final void onQuit(PlayerQuitEvent event) {
        PacketModule.unHook(event.getPlayer());
    }

}
