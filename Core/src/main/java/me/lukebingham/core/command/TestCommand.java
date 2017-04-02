package me.lukebingham.core.command;

import me.lukebingham.core.util.particle.ColoredParticle;
import me.lukebingham.core.util.particle.ParticleEffect;
import org.bukkit.entity.Player;

/**
 * Created by LukeBingham on 19/03/2017.
 */
public final class TestCommand extends CommandFactory<Player> {

    /**
     * Construct a new command.
     */
    public TestCommand() {
        super("test", "description.");
    }

    /**
     * This is fired when the command is executed.
     *
     * @param sender Sender of the command
     * @param args   Command arguments
     */
    @Override
    public final void execute(Player sender, String[] args) {
        ColoredParticle.send(ParticleEffect.REDSTONE, sender.getLocation(), 16, 0, 255, 0);
    }
}
