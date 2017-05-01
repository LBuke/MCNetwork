package me.lukebingham.gta.vehicles;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import me.lukebingham.core.packet.PacketHandler;
import me.lukebingham.core.packet.PacketModule;
import me.lukebingham.core.packet.PacketType;
import me.lukebingham.core.util.Component;
import me.lukebingham.core.util.PlayerUtil;
import me.lukebingham.core.util.ServerUtil;
import me.lukebingham.gta.vehicles.command.VehicleCommand;
import me.lukebingham.gta.vehicles.type.car.sport.AudiR8;
import me.lukebingham.util.C;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInSteerVehicle;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import org.spigotmc.event.entity.EntityDismountEvent;

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

        new VehicleCommand(this);

        PacketModule.addPacketListener(new PacketHandler(PacketType.IN_STEER_VEHICLE) {
            @Override public void handle(Player player, Packet<?> packet) {
                PacketPlayInSteerVehicle steerVehicle = (PacketPlayInSteerVehicle) packet;
                if(player.getVehicle() == null) return;
                if(player.getVehicle().getType() != EntityType.ARMOR_STAND) return;
                ArmorStand vehicle = (ArmorStand) player.getVehicle();
                Vector vector = null;

                travelDataMap.computeIfAbsent(player.getUniqueId(), key -> new TravelData(System.currentTimeMillis(), vehicle.getLocation()));
                TravelData data = travelDataMap.get(player.getUniqueId());

                Location dummy = data.getDummyLocation();
                if(dummy == null) {
                    dummy = data.setDummyLocation(vehicle.getLocation().clone());
                    dummy.setYaw(vehicle.getLocation().getYaw());
                }

                long now = System.currentTimeMillis();
                if(now >= data.getEndTime()) {
                    double distance = data.getStartLocation().distance(vehicle.getLocation()), mph = distance / 500;
                    PlayerUtil.sendActionBar(player, C.YELLOW + "MPH - " + mph);
                    data.setStartTime(now);
                    data.setEndTime(data.getStartTime() + 500);
                    data.setStartLocation(vehicle.getLocation());
                    data.setMph(mph);
                    travelDataMap.computeIfPresent(player.getUniqueId(), (uuid1, travelData) -> data);
                }

                // Left and Right
                int rotation = 5;
                if (steerVehicle.a() > 0) {//Pressing 'A'
                    if(steerVehicle.b() > 0) {
                        vehicle.setHeadPose(vehicle.getHeadPose().subtract(0, Math.toRadians(rotation), 0));
                        dummy.setYaw(dummy.getYaw() - rotation);
                    }
                    else if(steerVehicle.b() < 0) {
                        vehicle.setHeadPose(vehicle.getHeadPose().add(0, Math.toRadians(rotation), 0));
                        dummy.setYaw(dummy.getYaw() + rotation);
                    }
                }
                else if (steerVehicle.a() < 0) {//Pressing 'D'
                    if(steerVehicle.b() > 0) {
                        vehicle.setHeadPose(vehicle.getHeadPose().add(0, Math.toRadians(rotation), 0));
                        dummy.setYaw(dummy.getYaw() + rotation);
                    }
                    else if(steerVehicle.b() < 0) {
                        vehicle.setHeadPose(vehicle.getHeadPose().subtract(0, Math.toRadians(rotation), 0));
                        dummy.setYaw(dummy.getYaw() - rotation);
                    }
                }

                double speed = 0.86;//TODO: temp
                // Forward and Backwards.
                if(steerVehicle.b() > 0) {//Pressing 'W'
                    vector = new Vector();
                    double rotX = (double) dummy.getYaw();
                    double rotY = (double) dummy.getPitch();
                    vector.setY(-Math.sin(Math.toRadians(rotY)));
                    double xz = Math.cos(Math.toRadians(rotY));
                    vector.setX((-xz * Math.sin(Math.toRadians(rotX))) * (speed * 1.8));
                    vector.setZ((xz * Math.cos(Math.toRadians(rotX))) * (speed * 1.8));
                }
                else if(steerVehicle.b() < 0) {//Pressing 'S'
                    vector = new Vector();
                    double rotX = (double) dummy.getYaw();
                    double rotY = (double) dummy.getPitch();
                    vector.setY(-Math.sin(Math.toRadians(rotY)));
                    double xz = Math.cos(Math.toRadians(rotY));
                    vector.setX((-xz * Math.sin(Math.toRadians(rotX))) * -(speed * 1.3));
                    vector.setZ((xz * Math.cos(Math.toRadians(rotX))) * -(speed * 1.3));
                }

                // Up and Down
                if(steerVehicle.c()) {//Pressing 'SPACE'
                    ServerUtil.logDebug("SPACE");
                }

                if(steerVehicle.d()) {
                    player.teleport(vehicle);
                }

                ServerUtil.logDebug(vehicle.getVelocity().toString());
                if(vector != null) vehicle.setVelocity(vector.normalize());
            }
        });

        PacketModule.addPacketListener(new PacketHandler(PacketType.) {
            @Override public void handle(Player player, Packet<?> packet) {

            }
        });
    }

    @EventHandler
    protected final void onPlayerDismount(EntityDismountEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        if(!(event.getDismounted() instanceof ArmorStand)) return;
        Player player = (Player) event.getEntity();
        if(travelDataMap.containsKey(player.getUniqueId())) {

        }
    }

    class TravelData {
        private long startTime, endTime;
        private Location startLocation;
        private Location dummyLocation;
        private double mph = 0;
        private boolean exit = false;

        public TravelData(long startTime, Location startLocation) {
            this.startTime = startTime;
            this.endTime = startTime + 500;
            this.startLocation = startLocation;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public Location getStartLocation() {
            return startLocation;
        }

        public void setStartLocation(Location startLocation) {
            this.startLocation = startLocation;
        }

        public Location getDummyLocation() {
            return dummyLocation;
        }

        public Location setDummyLocation(Location dummyLocation) {
            this.dummyLocation = dummyLocation;
            return this.dummyLocation;
        }

        public double getMph() {
            return mph;
        }

        public void setMph(double mph) {
            this.mph = mph;
        }

        public void setExit(boolean exit) {
            this.exit = exit;
        }

        public boolean canExit() {
            return exit;
        }
    }
}
