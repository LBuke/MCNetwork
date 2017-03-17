package me.lukebingham.core.command;

import me.lukebingham.core.redis.JedisModule;
import me.lukebingham.core.redis.MessageListener;
import me.lukebingham.core.redis.message.CommandMessage;
import me.lukebingham.core.util.ServerUtil;
import me.lukebingham.core.util.rank.Role;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Created by LukeBingham on 16/03/2017.
 */
public class TestCommand extends CommandFactory<Player> implements StaffCommand, MessageListener<CommandMessage> {

    private JedisModule jedisModule;

    /**
     * Construct a new command.
     */
    public TestCommand(JedisModule jedisModule) {
        super("test", "This is a test command");

        this.jedisModule = jedisModule;
    }

    /**
     * This is fired when the command is executed.
     *
     * @param sender Sender of the command
     * @param args   Command arguments
     */
    @Override
    public void execute(Player sender, String[] args) {
        CommandMessage message = new CommandMessage("lol");
        this.jedisModule.sendMessage(message, "all");
    }

    /**
     * Only staff with the specified role or higher can use the command.
     *
     * @return Role value
     */
    @Override
    public Role getRequiredRole() {
        return Role.ADMINISTRATOR;
    }

    /**
     * This is fired when a 'CommandMessage' is received via Redis.
     *
     * @param sender Server which sent the message
     * @param msg    The JMessage
     */
    @Override
    public void onReceive(String sender, CommandMessage msg) {
        ServerUtil.logDebug("onReceive: " + msg.getCommand());
    }
}
