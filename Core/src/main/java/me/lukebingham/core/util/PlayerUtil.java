package me.lukebingham.core.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Created by LukeBingham on 16/04/2017.
 */
public class PlayerUtil {

    private static final HashMap<UUID, Queue<Title>> titleQueue;

    static {
        titleQueue = Maps.newHashMap();
    }

    public static void sendActionBar(Player player, String msg) {
        IChatBaseComponent baseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
        PacketPlayOutChat packet = new PacketPlayOutChat(baseComponent, (byte) 2);
        sendPacket(player, packet);
    }

    public static void sendTitle(Player player, Title title) {
        boolean contains = titleQueue.containsKey(player.getUniqueId());
        Queue<Title> queue = contains ? titleQueue.get(player.getUniqueId()) : Lists.newLinkedList();
        if(contains) {
            queue.add(title);
            titleQueue.put(player.getUniqueId(), queue);
            System.out.println("QUEUE IS NOT EMPTY, ADDING.");
            return;
        }
        queue.add(title);
        titleQueue.put(player.getUniqueId(), queue);
        System.out.println("QUEUE WAS EMPTY, SENDING TITLE.");

        new BukkitRunnable() {
            @Override public void run() {
                if(!titleQueue.containsKey(player.getUniqueId())) {
                    this.cancel();
                    System.out.println("PLAYER DOESN'T EXIST.");
                    return;
                }

                Queue<Title> newQueue = titleQueue.get(player.getUniqueId());
                if(newQueue == null || newQueue.isEmpty()) {
                    titleQueue.remove(player.getUniqueId());
                    this.cancel();
                    System.out.println("QUEUE IS EMPTY.");
                    return;
                }

                Title t = newQueue.poll();
                PacketPlayOutTitle head = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + t.getTitle() + "\"}"), 10, 20, 10);
                PacketPlayOutTitle foot = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + t.getSubtitle() + "\"}"), 10, 20, 10);

                sendPacket(player, head, foot);
            }
        }.runTaskTimer(ServerUtil.getJavaPlugin(), 0L, 30L);
    }

    public static void updateSkin(Player player) {
        final EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        final UUID uuid = player.getUniqueId();
        PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(entityPlayer.getId());
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (!p.getUniqueId().equals(uuid)) {
                sendPacket(p, destroyPacket);
            }
        }

        new BukkitRunnable() {
            public void run() {
                PacketPlayOutPlayerInfo playerInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer);
                PacketPlayOutNamedEntitySpawn spawnPacket = new PacketPlayOutNamedEntitySpawn(entityPlayer);
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    sendPacket(player, playerInfo);
                    if (!player.getUniqueId().equals(uuid)) {
                        sendPacket(player, spawnPacket);
                    }
                    else {
                        boolean isFlying = player.isFlying();
                        sendPacket(player, new PacketPlayOutRespawn(player.getWorld().getEnvironment().getId(), entityPlayer.getWorld().getDifficulty(), entityPlayer.getWorld().worldData.getType(), entityPlayer.playerInteractManager.getGameMode()));

                        player.teleport(player.getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                        player.setFlying(isFlying);
                        player.updateInventory();
                    }
                }
            }
        }.runTaskLater(ServerUtil.getJavaPlugin(), 5L);
    }

    public static void sendPacket(Player player, Packet<?>... packets) {
        for(Packet<?> packet : packets) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }
}
