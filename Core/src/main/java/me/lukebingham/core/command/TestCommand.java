package me.lukebingham.core.command;

import me.lukebingham.core.redis.JedisModule;
import me.lukebingham.core.redis.MessageListener;
import me.lukebingham.core.redis.message.CommandMessage;
import me.lukebingham.core.util.ServerUtil;
import org.bukkit.command.ConsoleCommandSender;

/**
 * Created by LukeBingham on 16/03/2017.
 */
public class TestCommand extends CommandFactory<ConsoleCommandSender> {

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
    public void execute(ConsoleCommandSender sender, String[] args) {
        CommandMessage message = new CommandMessage("lol");
        this.jedisModule.sendMessage(message, "all");
    }
}
