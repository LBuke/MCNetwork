package me.lukebingham.core.util;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Player;

/**
 * Created by LukeBingham on 28/03/2017.
 */
public class WorldUtil {

    public static void refreshChunk(Player player, World world, int x, int z, Callback<Boolean> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(ServerUtil.getJavaPlugin(), () -> {
            PacketPlayOutMapChunk packet = new PacketPlayOutMapChunk(((CraftWorld) world).getHandle().getChunkAt(x, z), true, 1);
            ServerUtil.sendPacket(player, packet);
            callback.call(true);
        });
    }

    public static void refreshChunk(Player player, World world, Location pointA, Location pointB, Callback<Boolean> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(ServerUtil.getJavaPlugin(), () -> {
            for (int x = pointB.getBlockX(); x < pointA.getBlockX(); x += 16) {
                for (int z = pointB.getBlockZ(); z < pointA.getBlockZ(); z += 16) {
                    PacketPlayOutMapChunk packet = new PacketPlayOutMapChunk(((CraftWorld) world).getHandle().getChunkAt(x, z), true, 1);
                    ServerUtil.sendPacket(player, packet);
                    if(z > pointA.getBlockZ() && x > pointA.getBlockX()) {
                        callback.call(true);
                        return;
                    }
                }
            }

            callback.call(false);
        });
    }

    public static void sendBlockChange(Player player, Location location, BlockData blockData) {
        PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(((CraftWorld) location.getWorld()).getHandle(), new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
        packet.block = CraftMagicNumbers.getBlock(blockData.getMaterial()).fromLegacyData(blockData.getData());
        ServerUtil.sendPacket(player, packet);
    }
}
