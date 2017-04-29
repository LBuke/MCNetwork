package me.lukebingham.gta;

import me.lukebingham.core.CorePlugin;
import me.lukebingham.core.enchantment.EnchantmentManager;
import me.lukebingham.core.enchantment.GlowEnchantment;
import me.lukebingham.core.module.Module;
import me.lukebingham.core.module.PluginState;
import me.lukebingham.core.util.ServerUtil;
import me.lukebingham.gta.chat.ChatComponent;
import me.lukebingham.gta.vehicles.VehicleManager;
import me.lukebingham.gta.vehicles.upgrade.VehicleArmor;
import me.lukebingham.gta.vehicles.upgrade.VehicleBrakes;
import me.lukebingham.gta.vehicles.upgrade.VehicleEngine;
import me.lukebingham.gta.weapon.gun.GunComponent;
import me.lukebingham.gta.weapon.gun.GunManager;
import me.lukebingham.gta.weapon.gun.command.GunCommand;
import me.lukebingham.gta.player.PlayerComponent;
import me.lukebingham.gta.profile.GTAProfile;
import me.lukebingham.util.ServerType;

/**
 * Created by LukeBingham on 13/04/2017.
 */
@Module(version = "1.0.0-SNAPSHOT", state = PluginState.PRE_ALPHA, type = ServerType.GTA)
public final class GTA extends CorePlugin<GTAProfile> {

    /**
     * This method is fired when the plugin starts.
     */
    @Override
    protected void load() {
        EnchantmentManager enchantmentManager = new EnchantmentManager();
        enchantmentManager.addEnchantment(new GlowEnchantment());
        enchantmentManager.addEnchantment(new VehicleArmor());
        enchantmentManager.addEnchantment(new VehicleBrakes());
        enchantmentManager.addEnchantment(new VehicleEngine());
        enchantmentManager.registerAll();

        GunManager gunManager = new GunManager();
        VehicleManager vehicleManager = new VehicleManager();

        //Commands
        new GunCommand(gunManager);

        //Components
        GunComponent gunComponent = new GunComponent(this, gunManager, super.profileManager);
        ChatComponent chatComponent = new ChatComponent(super.profileManager);
        PlayerComponent playerComponent = new PlayerComponent(super.profileManager);

        ServerUtil.registerComponent(gunComponent, chatComponent, playerComponent, vehicleManager);
    }

    /**
     * This method is fired before the plugin disables
     */
    @Override
    protected void unload() {
        getDiscordManager().onDisable();
    }

    /**
     * @return Name of the plugin
     */
    @Override
    public String getPluginName() {
        return "GTA";
    }
}
