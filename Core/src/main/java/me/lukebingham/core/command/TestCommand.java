package me.lukebingham.core.command;

import me.lukebingham.core.i18n.I18n;
import me.lukebingham.core.i18n.I18nMessage;
import org.bukkit.entity.Player;

/**
 * Created by LukeBingham on 19/03/2017.
 */
public class TestCommand extends CommandFactory<Player> {

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
    public void execute(Player sender, String[] args) {
        //{0} this is a {0} {1} TEST
        I18n.message(sender, I18nMessage.TEST, "ABC", sender.getPlayer().getName());
    }
}
