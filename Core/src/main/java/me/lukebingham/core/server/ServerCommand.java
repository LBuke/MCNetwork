package me.lukebingham.core.server;

import me.lukebingham.core.command.CommandFactory;
import me.lukebingham.core.command.attributes.*;
import me.lukebingham.redis.JedisModule;
import me.lukebingham.redis.message.CreateServerMessage;
import me.lukebingham.util.ServerType;
import org.bukkit.command.ConsoleCommandSender;

/**
 * Created by LukeBingham on 17/03/2017.
 */
@Name("server")
@Description("Create a new server..")
@Tooltip("")
@Suggest("/server <ServerType>")
public final class ServerCommand extends CommandFactory<ConsoleCommandSender> {

    private final JedisModule jedisModule;

    /**
     * Construct a new command.
     */
    public ServerCommand(JedisModule jedisModule) {
        super(ServerCommand.class);
        this.jedisModule = jedisModule;
    }

    /**
     * This is fired when the command is executed.
     *
     * @param sender Sender of the command
     * @param args   Command arguments
     */
    @Override
    public final void execute(ConsoleCommandSender sender, String[] args) {
        jedisModule.sendMessage(new CreateServerMessage(ServerType.valueOf(args[0])), "OPERATOR");
    }
}
