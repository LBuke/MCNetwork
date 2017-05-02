package me.lukebingham.gta.vehicles;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import me.lukebingham.core.packet.PacketHandler;
import me.lukebingham.core.packet.PacketModule;
import me.lukebingham.core.packet.PacketType;
import me.lukebingham.core.util.Component;
import me.lukebingham.core.util.PlayerUtil;
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

                travelDataMap.computeIfAbsent(player.getUniqueId(), key -> new TravelData(System.currentTimeMillis(), vehicle.getLocation()));
                TravelData data = travelDataMap.get(player.getUniqueId());

                Location dummy = data.getDummyLocation();
                if(dummy == null) {
                    dummy = data.setDummyLocation(vehicle.getLocation().clone());
                    dummy.setYaw(vehicle.getLocation().getYaw());
                }

                long now = System.currentTimeMillis();
                if(now >= data.getEndTime()) {
                    double distance = data.getStartLocation().distance(vehicle.getLocation()), mph = distance / 0.5;
                    PlayerUtil.sendActionBar(player, C.YELLOW + "MPH - " + String.format("%.1f", mph));
                    data.setStartTime(now);
                    data.setEndTime(data.getStartTime() + 500);
                    data.setStartLocation(vehicle.getLocation());
                    data.setMph(mph);
                    travelDataMap.computeIfPresent(player.getUniqueId(), (uuid1, travelData) -> data);
                }

                int rotation = 1;
                double maxSpeed = 8.6, maxRot = 5, accel = 0.02;

                if (steerVehicle.a() > 0) {//Key down = 'A'
                    if(steerVehicle.b() > 0) data.setDr(data.getDr() - rotation);
                    else if (steerVehicle.b() < 0) data.setDr(data.getDr() + rotation);

                } else if (steerVehicle.a() < 0) {//Key down = 'D'
                    if(steerVehicle.b() > 0) data.setDr(data.getDr() + rotation);
                    else if (steerVehicle.b() < 0) data.setDr(data.getDr() - rotation);
                }

                if(steerVehicle.b() < 0) data.setDf(data.getDf() - (accel * 4));//Key down = 'S'
                else if (steerVehicle.b() > 0) data.setDf(data.getDf() + (accel * 4));//Key down = 'W'

                if(Math.abs(data.getDr()) > maxRot) {
                    if(data.getDr() > 0) data.setDr(maxRot);
                    else data.setDr(-maxRot);
                }

                if(Math.abs(data.getDf()) > maxSpeed) {
                    if(data.getDf() > 0) data.setDf(maxSpeed);
                    else data.setDf(-maxSpeed);
                }

                //Rotation
                if(data.getDr() > 0) {
                    data.setDr(data.getDr() - 0.25);
                    if(data.getDr() < 0) data.setDr(0);
                }
                else if(data.getDr() < 0) {
                    data.setDr(data.getDr() + 0.25);
                    if(data.getDr() > 0) data.setDr(0);
                }

                //Forward
                if(data.getDf() > 0) {
                    data.setDf(data.getDf() - (accel * 2));
                    if(data.getDf() < 0) data.setDf(0);
                }
                else if(data.getDf() < 0) {
                    data.setDf(data.getDf() + (accel * 2));
                    if(data.getDf() > 0) data.setDf(0);
                }

                vehicle.setHeadPose(vehicle.getHeadPose().add(0, Math.toRadians(data.getDr()), 0));
                dummy.setYaw((float) (dummy.getYaw() + data.getDr()));

                Vector vector = new Vector();
                double rotX = (double) dummy.getYaw();
                double rotY = (double) dummy.getPitch();
                vector.setY(-Math.sin(Math.toRadians(rotY)));
                double xz = Math.cos(Math.toRadians(rotY));
                vector.setX((-xz * Math.sin(Math.toRadians(rotX))) * data.getDf());
                vector.setZ((xz * Math.cos(Math.toRadians(rotX))) * data.getDf());

                //Key down = 'SPACE'
                if(steerVehicle.c()) {
                    if(steerVehicle.b() > 0 && steerVehicle.a() != 0) {
                        //TODO Drift
                    }
                }

                //Key down = 'SHIFT'
                if(steerVehicle.d()) {
                    player.teleport(vehicle);
                }

                vehicle.setVelocity(vector);
            }
        });
    }

    @EventHandler
    protected final void onPlayerDismount(EntityDismountEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        if(!(event.getDismounted() instanceof ArmorStand)) return;
        travelDataMap.remove(event.getEntity().getUniqueId());
    }

    class TravelData {
        private long startTime, endTime;
        private Location startLocation;
        private Location dummyLocation;
        private double mph = 0, dr, df;
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

        public double getDf() {
            return df;
        }

        public void setDf(double df) {
            this.df = df;
        }

        public double getDr() {
            return dr;
        }

        public void setDr(double dr) {
            this.dr = dr;
        }
    }
}
