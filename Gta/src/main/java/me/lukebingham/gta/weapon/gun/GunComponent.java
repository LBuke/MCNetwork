package me.lukebingham.gta.weapon.gun;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.lukebingham.core.profile.ProfileManager;
import me.lukebingham.core.util.*;
import me.lukebingham.gta.weapon.gun.attributes.BurstGun;
import me.lukebingham.gta.profile.GTAProfile;
import me.lukebingham.util.C;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Created by LukeBingham on 16/04/2017.
 */
public final class GunComponent implements Component {

    private final HashMap<UUID, List<GunCooldown>> cooldownMap;

    private final JavaPlugin plugin;
    private final GunManager gunManager;
    private final ProfileManager<GTAProfile> profileManager;

    public GunComponent(JavaPlugin plugin, GunManager gunManager, ProfileManager<GTAProfile> profileManager) {
        this.plugin = plugin;
        this.gunManager = gunManager;
        this.profileManager = profileManager;

        this.cooldownMap = Maps.newHashMap();
    }

    @EventHandler
    protected final void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getPlayer() == null) return;
        if (event.getItem() == null) return;
        Material material = event.getItem().getType();
        if (material == Material.AIR) return;
        if (material.isBlock() || material.isEdible()) return;

        //Get held gun
        Optional<Gun> optional = gunManager.getGun(event.getItem().getType(), event.getItem().getDurability());
        if (!optional.isPresent()) return;
        Gun gun = optional.get();
        if (!canShoot(event.getPlayer(), gun)) return;

        //Get profile
        GTAProfile profile = profileManager.getCache(event.getPlayer().getUniqueId());
        if (profile == null) return;

        if (profile.getClip(gun) <= 0) {
            if (!profile.canReload(gun)) {
                PlayerUtil.sendActionBar(event.getPlayer(), C.DARK_RED + C.BOLD + "(!)  NO AMMO  (!)");
                return;
            }

            profile.refillClip(gun);
            PlayerUtil.sendActionBar(event.getPlayer(), C.RED + C.BOLD + "(!)  RELOADING  (!)");
            return;
        }

        boolean burst = gun instanceof BurstGun;
        profile.takeClip(gun, burst ? ((BurstGun) gun).getBurst() : 1);
        PlayerUtil.sendActionBar(event.getPlayer(), C.BLUE + C.BOLD + profile.getClip(gun) + C.WHITE + C.BOLD +  " / " + C.BLUE + C.BOLD + profile.getAmmo(gun.getGunType()));

        Queue<Class<? extends Projectile>> projectiles = Lists.newLinkedList();
        if (burst) {
            BurstGun burstGun = (BurstGun) gun;
            for (int i = 0; i < burstGun.getBurst(); i++) {
                projectiles.add(gun.getBulletType().getProjectile());
            }
        } else {
            projectiles.add(gun.getBulletType().getProjectile());
        }

        if (projectiles.size() <= 1) {
            Projectile bullet = event.getPlayer().launchProjectile(projectiles.poll());
            bullet.setVelocity(bullet.getVelocity().multiply(1.2));
            bullet.setCustomNameVisible(false);
            bullet.setCustomName(gun.getName() + ";" + gunManager.getDamage(gun).value());
            return;
        }

        int[] x = {0};
        new BukkitRunnable() {
            @Override public void run() {
                Projectile bullet = event.getPlayer().launchProjectile(projectiles.poll());
                bullet.setVelocity(bullet.getVelocity().multiply(1.4));
                bullet.setCustomNameVisible(false);
                bullet.setCustomName(gun.getName() + ";" + gunManager.getDamage(gun).value());

                if (projectiles.isEmpty()) this.cancel();
            }
        }.runTaskTimer(plugin, 0, ((BurstGun) gun).getBurstInterval());
    }

    /**
     * Check if a player can shoot there current weapon.
     *
     * @param player The Player
     * @param gun The current Gun
     * @return can shoot status
     */
    private boolean canShoot(Player player, Gun gun) {
        if (player == null || gun == null) return false;

        UUID uuid = player.getUniqueId();
        List<GunCooldown> guns = cooldownMap.computeIfAbsent(uuid, key -> new ArrayList<GunCooldown>(Collections.singletonList(new GunCooldown(gun.getGunType(), gun))));
        Optional<GunCooldown> optional = guns.stream().filter(c -> c.getGunType() == gun.getGunType() && c.getGun().getGunUniqueIdentifier() == gun.getGunUniqueIdentifier()).findFirst();

        GunCooldown cooldown = optional.isPresent() ? optional.get() : ListUtil.addAndReturn(new GunCooldown(gun.getGunType(), gun), guns);
        boolean elapsed = cooldown.hasElapsed();
        if (elapsed) cooldown.start();
        return elapsed;
    }

    @EventHandler
    protected final void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!(event.getDamager() instanceof Snowball)) return;//BULLET

        Player player = (Player) event.getEntity();
        Snowball bullet = (Snowball) event.getDamager();

        if (bullet.getShooter() == null) return;
        if (!(bullet.getShooter() instanceof Player)) return;

        Player shooter = (Player) bullet.getShooter();
        String gun = bullet.getCustomName().split(";")[0];
        double damage = Double.parseDouble(bullet.getCustomName().split(";")[1]);

        player.sendMessage(C.RED + "You was shot by " + shooter.getName() + " by " + gun);
        shooter.sendMessage(C.YELLOW + "You shot " + player.getName() + " and dealt " + damage + " damage. (" + gun + ")");
        PlayerUtil.sendTitle(player, new Title(C.DARK_RED + "-" + damage, C.RED + "You was shot by " + shooter.getName() + " by " + gun));
        event.setDamage(damage);
    }

    @EventHandler
    protected final void onItemDamage(PlayerItemDamageEvent event) {
        Optional<Gun> optional = gunManager.getGun(event.getItem().getType(), event.getItem().getDurability());
        if(optional.isPresent() && !event.isCancelled())
            event.setCancelled(true);
    }
}
