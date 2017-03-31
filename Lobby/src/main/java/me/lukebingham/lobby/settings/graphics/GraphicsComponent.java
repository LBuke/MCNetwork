package me.lukebingham.lobby.settings.graphics;

import com.google.common.collect.Sets;
import me.lukebingham.core.graphics.event.GraphicsChangeEvent;
import me.lukebingham.core.i18n.I18nTODO;
import me.lukebingham.core.packet.PacketEvent;
import me.lukebingham.core.packet.PacketHandler;
import me.lukebingham.core.util.BlockData;
import me.lukebingham.core.util.Component;
import me.lukebingham.core.util.WorldUtil;
import me.lukebingham.lobby.Lobby;
import me.lukebingham.lobby.region.SpawnRegion;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;
import java.util.UUID;

/**
 * Created by LukeBingham on 26/03/2017.
 */
@I18nTODO
public final class GraphicsComponent implements Component {

    private final Set<UUID> uuidSet;

    private final Lobby plugin;
    private final SpawnRegion spawnRegion;

    public GraphicsComponent(Lobby plugin, SpawnRegion spawnRegion) {
        this.plugin = plugin;
        this.spawnRegion = spawnRegion;
        uuidSet = Sets.newHashSet();

        PacketHandler.addPacketListener(PacketPlayOutBlockChange.class);
    }

    @EventHandler
    protected final void onGraphicsChange(GraphicsChangeEvent event) {
        if (event.getPlayer() == null) return;
        if (event.getGraphics() == null) return;

        if(uuidSet.contains(event.getPlayer().getUniqueId())) {
            event.getPlayer().sendMessage("Please wait.");
            return;
        }

        uuidSet.add(event.getPlayer().getUniqueId());
        if (event.getGraphics().hasBlockChanges()) {
            final int[] index = { 0 };
            new BukkitRunnable() {
                @Override public void run() {
                    spawnRegion.getList(index[0]++, false, (locations, done) -> {
                        locations.forEach(loc -> {
                            BlockData blockData = event.getGraphics().getBlockData(loc.getBlock().getType(), (byte)-1);
                            WorldUtil.sendBlockChange(event.getPlayer(), loc, blockData != null ? event.getGraphics().getBlockData().get(blockData) : new BlockData(loc.getBlock().getType(), loc.getBlock().getData()));
                        });

                        if (done) {
                            uuidSet.remove(event.getPlayer().getUniqueId());
                            cancel();
                        }
                        locations.clear();
                    });
                }
            }.runTaskTimer(plugin, 0, 3);
        }
    }

    @EventHandler
    protected final void onBlockChange(PacketEvent event) {
        if(event.getPlayer() == null) return;
        if(event.getPacketType() != PacketEvent.PacketType.OUTWARDS) return;
        if(!(event.getPacket() instanceof PacketPlayOutBlockChange)) return;
        if(uuidSet.contains(event.getPlayer().getUniqueId())) return;

        PacketPlayOutBlockChange packet = (PacketPlayOutBlockChange) event.getPacket();
        if(packet.block.getBlock().getName().equals("tile.air.name")) return;
        event.setCancelled(true);
    }
}
