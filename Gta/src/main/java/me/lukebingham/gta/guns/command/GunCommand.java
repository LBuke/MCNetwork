package me.lukebingham.gta.guns.command;

import me.lukebingham.core.command.CommandFactory;
import me.lukebingham.core.command.attributes.Description;
import me.lukebingham.core.command.attributes.Name;
import me.lukebingham.gta.guns.GunManager;
import me.lukebingham.gta.guns.inventory.GunMenu;
import org.bukkit.entity.Player;

/**
 * Created by LukeBingham on 14/04/2017.
 */
@Name("gun")
@Description("Just a test command.")
public class GunCommand extends CommandFactory<Player> {

    private GunManager gunManager;

    /**
     * Construct a new command.
     */
    public GunCommand(GunManager gunManager) {
        super(GunCommand.class);
        this.gunManager = gunManager;
    }

    /**
     * This is fired when the command is executed.
     *
     * @param sender Sender of the command
     * @param args   Command arguments
     */
    @Override
    public void execute(Player sender, String[] args) {
        new GunMenu(gunManager).openInventory(sender);
    }
}
