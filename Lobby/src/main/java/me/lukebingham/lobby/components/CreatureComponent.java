package me.lukebingham.lobby.components;

import me.lukebingham.core.util.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by LukeBingham on 23/02/2017.
 */
public class CreatureComponent implements Component {

    private boolean allowCreatureSpawn, allowDamage;

    @Override
    public void onLoad() {
        Bukkit.getWorld("world").getEntities().stream().filter(e -> !(e instanceof HumanEntity)).forEach(Entity::remove);
    }

    @Override
    public boolean disableAble() {
        return false;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if(event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM) return;
        event.setCancelled(!allowCreatureSpawn);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player))
            event.setCancelled(!allowDamage);
    }

    public void setAllowCreatureSpawn(boolean allowCreatureSpawn) {
        this.allowCreatureSpawn = allowCreatureSpawn;
    }

    public void setAllowDamage(boolean allowDamage) {
        this.allowDamage = allowDamage;
    }
}
