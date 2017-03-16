package me.lukebingham.core.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Created by LukeBingham on 16/03/2017.
 */
public abstract class CommandFactory<T extends CommandSender> extends BukkitCommand {

    private static CommandMap commandMap = null;

    private final String command, description;
    private final String[] aliases;
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

    @Override
    public final boolean execute(CommandSender commandSender, String s, String[] strings) {
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
