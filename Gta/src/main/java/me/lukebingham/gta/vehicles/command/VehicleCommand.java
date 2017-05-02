package me.lukebingham.gta.vehicles.command;

import me.lukebingham.core.command.CommandFactory;
import me.lukebingham.core.command.attributes.Description;
import me.lukebingham.core.command.attributes.Name;
import me.lukebingham.gta.vehicles.VehicleManager;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by LukeBingham on 28/04/2017.
 */
@Name("vehicle")
@Description("Just a test command.")
public final class VehicleCommand extends CommandFactory<Player> {

    private final VehicleManager vehicleManager;

    /**
     * Construct a new command.
     */
    public VehicleCommand(VehicleManager vehicleManager) {
        super(VehicleCommand.class);
        this.vehicleManager = vehicleManager;
    }

    /**
     * This is fired when the command is executed.
     *
     * @param sender Sender of the command
     * @param args   Command arguments
     */
    @Override
    public final void execute(Player sender, String[] args) {
        ArmorStand armorStand = sender.getWorld().spawn(sender.getLocation(), ArmorStand.class);
        armorStand.setCustomName("AudiR8");
        armorStand.getEquipment().setHelmet(new ItemStack(Material.WOOD_AXE));

        armorStand.setPassenger(sender);
    }
}
