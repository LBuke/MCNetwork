package me.lukebingham.core.command;

import me.lukebingham.core.redis.JedisModule;
import me.lukebingham.core.redis.message.CreateServerMessage;
import me.lukebingham.core.util.ServerType;
import org.bukkit.command.ConsoleCommandSender;

/**
 * Created by LukeBingham on 17/03/2017.
 */
public class ServerCommand extends CommandFactory<ConsoleCommandSender> {

    private JedisModule jedisModule;

    /**
     * Construct a new command.
     */
    public ServerCommand(JedisModule jedisModule) {
        super("server", "Create a new server.");
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
        jedisModule.sendMessage(new CreateServerMessage(ServerType.valueOf(args[0])), "OPERATOR");
    }
}
