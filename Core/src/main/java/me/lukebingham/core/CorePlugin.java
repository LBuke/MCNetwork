package me.lukebingham.core;

import com.google.common.collect.Sets;
import me.lukebingham.core.command.ServerCommand;
import me.lukebingham.core.command.TestCommand;
import me.lukebingham.core.database.Database;
import me.lukebingham.core.database.DatabaseModule;
import me.lukebingham.core.graphics.GraphicsManager;
import me.lukebingham.core.i18n.I18n;
import me.lukebingham.core.i18n.I18nComponent;
import me.lukebingham.core.inventory.MenuComponent;
import me.lukebingham.core.module.Module;
import me.lukebingham.core.packet.PacketComponent;
import me.lukebingham.core.packet.PacketHandler;
import me.lukebingham.core.profile.CoreProfile;
import me.lukebingham.core.profile.ProfileManager;
import me.lukebingham.core.redis.JedisModule;
import me.lukebingham.core.util.C;
import me.lukebingham.core.util.Component;
import me.lukebingham.core.util.ServerUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

/**
 * Created by LukeBingham on 22/02/2017.
 */
public abstract class CorePlugin<Profile extends CoreProfile> extends JavaPlugin implements Core {

    private HashSet<Component> components;
    protected Database database;
    protected JedisModule jedisModule;
    protected ProfileManager<Profile> profileManager;
    protected GraphicsManager graphicsManager;

    @Override
    public final void onEnable() {
        //In case spigot ever add default 'onEnable' code.
        super.onEnable();

        this.components = Sets.newHashSet();
        this.database = new DatabaseModule("localhost", 27017, true);
        this.jedisModule = new JedisModule(getPluginName());
        this.profileManager = new ProfileManager<>();
        this.graphicsManager = new GraphicsManager();
        I18n i18n = new I18n();

        //Commands
        new ServerCommand(this.jedisModule);
        new TestCommand();

        //Components & Listeners
        MenuComponent menuComponent = new MenuComponent();
        I18nComponent i18nComponent = new I18nComponent(profileManager);
        PacketComponent packetComponent = new PacketComponent();
        ServerUtil.registerComponent(menuComponent, i18nComponent, packetComponent);

        load();
        logLoadedModule();
    }

    @Override
    public final void onLoad() {
        //In case spigot ever add default 'onLoad' code.
        super.onLoad();
    }

    @Override
    public final void onDisable() {
        //In case spigot ever add default 'onDisable' code.
        super.onDisable();

        getComponents().forEach(ServerUtil::unregisterComponent);
        unload();
        database.close();
        jedisModule.disable();
        PacketHandler.clear();
    }

    protected abstract void load();
    protected abstract void unload();

    private Module getModuleState() {
        return getClass().isAnnotationPresent(Module.class) ? getClass().getAnnotation(Module.class) : null;
    }

    private void logLoadedModule() {
        String log = C.AQUA + "Module loaded:    " + getPluginName() + C.GRAY;
        int classLength = getPluginName().length(), max = 35, difference = max - classLength;
        for(int i = 0; i < difference; i++) log += ".";
        log += getModuleState() != null ? getModuleState().state().getName(true, false) : C.RED + "NULL";
        ServerUtil.log(log);
    }

    @Override
    public final HashSet<Component> getComponents() {
        return components;
    }

    @Override
    public JedisModule getJedis() {
        return jedisModule;
    }

    @Override
    public JavaPlugin getPlugin() {
        return this;
    }
}
