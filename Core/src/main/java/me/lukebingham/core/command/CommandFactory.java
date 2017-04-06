package me.lukebingham.core.command;

import me.lukebingham.core.command.attributes.*;
import me.lukebingham.core.profile.CoreProfile;
import me.lukebingham.core.profile.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Created by LukeBingham on 16/03/2017.
 */
public abstract class CommandFactory<T extends CommandSender> extends BukkitCommand {

    private final CommandFactory<T> cmdClass = this;
    private static CommandMap commandMap = null;

    private String command, description;
    private String[] aliases;
    private String suggest, tooltip;

    /**
     * Construct a new command.
     *
     * @param command     The command label
     * @param description The command description
     * @param aliases     Aliases of the command
     */
    public CommandFactory(String command, String description, String[] aliases) {
        super(command);
        this.command = command;
        this.description = description;
        this.aliases = aliases;

        register();
    }

    /**
     * Construct a new command.
     *
     * @param command     The command label
     * @param description The command description
     */
    public CommandFactory(String command, String description) {
        this(command, description, new String[]{});
    }

    /**
     * Construct a new command.
     *
     * @param cmd The command
     */
    public CommandFactory(Class<? extends CommandFactory> cmd) {
        this(cmd.getAnnotation(Name.class).value(), cmd.getAnnotation(Description.class).value());

        if(cmd.isAnnotationPresent(Alias.class))
            this.aliases = cmd.getAnnotation(Alias.class).value();

        if(cmd.isAnnotationPresent(Tooltip.class))
            this.tooltip = cmd.getAnnotation(Tooltip.class).value();

        if(cmd.isAnnotationPresent(Suggest.class))
            this.suggest = cmd.getAnnotation(Suggest.class).value();
    }

    @Override
    public final boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(commandSender instanceof Player) {
            CoreProfile profile = null;
            if(this instanceof RankedCommand) {
                profile = ProfileManager.getInstance().getCache(((Player) commandSender).getUniqueId());
                if(profile.getRank().hasRank(((RankedCommand) this).getRequiredRank())) {
                    execute((T) commandSender, strings);
                } else {
                    commandSender.sendMessage("NO PERMISSION.");
                }
                return true;
            }

            if(this instanceof StaffCommand) {
                profile = ProfileManager.getInstance().getCache(((Player) commandSender).getUniqueId());
                if(profile.getRole().hasRole(((StaffCommand) this).getRequiredRole())) {
                    execute((T) commandSender, strings);
                } else {
                    commandSender.sendMessage("NO PERMISSION.");
                }
                return true;
            }

            execute((T) commandSender, strings);
            return true;
        }
        execute((T) commandSender, strings);
        return true;
    }

    /**
     * This is fired when the command is executed.
     *
     * @param sender Sender of the command
     * @param args   Command arguments
     */
    public abstract void execute(T sender, String[] args);

    /**
     * This method will register a command without the need of plugin.yml
     */
    private void register() {
        if (commandMap != null) {
            setAliases(Arrays.asList(aliases));
            setDescription(description);
            commandMap.register(command, this);
            return;
        }

        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            commandMap = (CommandMap) field.get(Bukkit.getServer());

            setAliases(Arrays.asList(aliases));
            setDescription(description);

            commandMap.register(command, this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCommand() {
        return command;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String[] getArrayAliases() {
        return aliases;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
