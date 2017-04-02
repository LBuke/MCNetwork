package me.lukebingham.core.util.particle;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by LukeBingham on 22/03/2017.
 */
public final class ColoredParticle {

    public static void send(ParticleEffect effect, Location location, List<Player> players, int r, int g, int b) {
        effect.display(r / 255, g / 255, b / 255, 1, 0, location, players);
    }

    public static void send(ParticleEffect effect, Location location, int Distance, int r, int g, int b) {
        effect.display(r / 255, g / 255, b / 255, 1, 0, location, Distance);
    }
}
