package me.lukebingham.game.combat.indicator;

import me.lukebingham.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by LukeBingham on 30/03/2017.
 */
public final class PlayerDamageIndicator extends DamageIndicator {

    private Game<?> game;

    public PlayerDamageIndicator(Game<?> game) {
        this.game = game;
    }

    @EventHandler
    protected void onEntityDamage(EntityDamageByEntityEvent event) {
//        if(this.game.getGameState() != GameState.LIVE) return;
        if(!(event.getDamager() instanceof Player)) return;
        spawn(event);
    }
}
