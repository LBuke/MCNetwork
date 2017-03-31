package me.lukebingham.game.combat.indicator;

import me.lukebingham.core.util.C;
import me.lukebingham.core.util.Component;
import me.lukebingham.core.util.EntityUtil;
import me.lukebingham.core.util.ServerUtil;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntity;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by LukeBingham on 30/03/2017.
 */
public abstract class DamageIndicator implements Component {

    protected final void spawn(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof LivingEntity)) return;
        Player damager = (Player) event.getDamager();
        LivingEntity victim = (LivingEntity) event.getEntity();
        DamageIndicatorEntity entity = new DamageIndicatorEntity(victim, C.RED + C.BOLD + "- " + C.RED + event.getDamage());
        PacketPlayOutSpawnEntity spawnPacket = new PacketPlayOutSpawnEntity(entity, 30);
        PacketPlayOutEntityTeleport teleportPacket = new PacketPlayOutEntityTeleport(entity);
        ((CraftWorld) victim.getLocation().getWorld()).getHandle().addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        EntityUtil.setArmorStandMarker(entity, true);
        entity.setLocation(victim.getLocation().getX(), victim.getLocation().getY() + 2, victim.getLocation().getZ(), 0f, 0f);

        ServerUtil.sendPacket(damager, spawnPacket, teleportPacket);
//        entity.sendDirection();

        ServerUtil.runTaskLater(20, () -> {
            PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(entity.getId());
            ServerUtil.sendPacket(damager, destroy);
            entity.dead = true;
        });
    }
}
