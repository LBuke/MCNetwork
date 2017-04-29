package me.lukebingham.gta.vehicles;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import me.lukebingham.core.util.Component;
import me.lukebingham.core.util.PlayerUtil;
import me.lukebingham.core.util.StringUtil;
import me.lukebingham.gta.vehicles.type.car.sport.AudiR8;
import me.lukebingham.util.C;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by LukeBingham on 28/04/2017.
 */
public final class VehicleManager implements Component {

    private final HashSet<Vehicle> vehicles;
    private final HashMap<UUID, TravelData> travelDataMap;

    public VehicleManager() {
        travelDataMap = Maps.newHashMap();
        vehicles = Sets.newHashSet();
        vehicles.addAll(Arrays.asList(
                new AudiR8()
        ));
    }

    @EventHandler
    protected final void onPlayerMove(PlayerMoveEvent event) {
        if(event.getPlayer() == null) return;
        UUID uuid = event.getPlayer().getUniqueId();
        travelDataMap.computeIfAbsent(uuid, key -> new TravelData(System.currentTimeMillis(), event.getTo()));
        TravelData data = travelDataMap.get(uuid);
        if(System.currentTimeMillis() >= data.getEndTime()) {
            double distance = data.getStartLocation().distance(event.getTo());
            PlayerUtil.sendActionBar(event.getPlayer(), C.YELLOW + "MPH - " + (distance / 0.5));
            travelDataMap.computeIfPresent(uuid, (uuid1, travelData) -> new TravelData(System.currentTimeMillis(), event.getTo()));
        }
    }

    class TravelData {
        private final long startTime, endTime;
        private final Location startLocation;

        public TravelData(long startTime, Location startLocation) {
            this.startTime = startTime;
            this.endTime = startTime + 500;
            this.startLocation = startLocation;
        }

        public long getStartTime() {
            return startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public Location getStartLocation() {
            return startLocation;
        }
    }
}
