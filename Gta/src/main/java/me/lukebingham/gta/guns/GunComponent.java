package me.lukebingham.gta.guns;

import me.lukebingham.core.profile.ProfileManager;
import me.lukebingham.core.util.Component;
import me.lukebingham.core.util.PlayerUtil;
import me.lukebingham.gta.guns.attributes.BurstGun;
import me.lukebingham.gta.profile.GTAProfile;
import me.lukebingham.util.C;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Optional;

/**
 * Created by LukeBingham on 16/04/2017.
 */
public final class GunComponent implements Component {

    private final GunManager gunManager;
    private final ProfileManager<GTAProfile> profileManager;

    public GunComponent(GunManager gunManager, ProfileManager<GTAProfile> profileManager) {
        this.gunManager = gunManager;
        this.profileManager = profileManager;
    }

    @EventHandler
    protected final void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getPlayer() == null) return;
        if (event.getItem() == null) return;
        Material material = event.getItem().getType();
        if (material == Material.AIR) return;
        if (material.isBlock() || material.isEdible()) return;

        GTAProfile profile = profileManager.getCache(event.getPlayer().getUniqueId());
        if (profile == null) return;

        Optional<Gun> optional = gunManager.getGun(event.getItem().getType(), event.getItem().getDurability());
        if (!optional.isPresent()) return;

        Gun gun = optional.get();
        if (profile.getClip(gun) <= 0) {
            if (!profile.canReload(gun)) {
                PlayerUtil.sendActionBar(event.getPlayer(), C.DARK_RED + C.BOLD + "(!)  NO AMMO  (!)");
                return;
            }

            profile.refillClip(gun);
            PlayerUtil.sendActionBar(event.getPlayer(), C.RED + C.BOLD + "(!)  RELOADING  (!)");
            return;
        }

        profile.takeClip(gun, gun instanceof BurstGun ? ((BurstGun) gun).getBurst() : 1);
        PlayerUtil.sendActionBar(event.getPlayer(), C.BLUE + C.BOLD + profile.getClip(gun) + C.WHITE + C.BOLD +  " / " + C.BLUE + C.BOLD + profile.getAmmo(gun.getGunType()));

        Projectile bullet = null;
        switch (gun.getBulletType()) {
            case BULLET: bullet = event.getPlayer().launchProjectile(Snowball.class); break;
            case ROCKET: bullet = event.getPlayer().launchProjectile(EnderPearl.class); break;
        }

        if(bullet == null) return;
        bullet.setVelocity(bullet.getVelocity().multiply(1.2));
    }
}
