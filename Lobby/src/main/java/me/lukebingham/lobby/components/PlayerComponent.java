package me.lukebingham.lobby.components;

import me.lukebingham.core.Core;
import me.lukebingham.core.cosmetic.CosmeticManager;
import me.lukebingham.core.database.Database;
import me.lukebingham.core.module.Module;
import me.lukebingham.core.module.PluginState;
import me.lukebingham.core.profile.ProfileManager;
import me.lukebingham.core.util.C;
import me.lukebingham.core.util.Component;
import me.lukebingham.core.util.ServerUtil;
import me.lukebingham.core.util.factory.ItemFactory;
import me.lukebingham.core.util.rank.Role;
import me.lukebingham.lobby.Lobby;
import me.lukebingham.lobby.dao.GadgetDAO;
import me.lukebingham.lobby.profile.LobbyProfile;
import me.lukebingham.lobby.profile.ProfileInventory;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;

/**
 * Created by LukeBingham on 23/02/2017.
 */
public final class PlayerComponent implements Component {

    private final Core core;
    private final Database database;
    private final CosmeticManager cosmeticManager;
    private final ProfileManager<LobbyProfile> profileManager;

    private boolean allowItemDrop, allowConsume, allowBlockBreak, allowBlockPlace,
            allowHungerDrain, allowDamage;

    public PlayerComponent(Core core, Database database, CosmeticManager cosmeticManager, ProfileManager<LobbyProfile> profileManager) {
        this.core = core;
        this.database = database;
        this.cosmeticManager = cosmeticManager;
        this.profileManager = profileManager;
    }

    @Override
    public boolean disableAble() {
        return false;
    }

    @Override
    public void onDisable() {
        profileManager.getPlayerCache().forEach(cache -> profileManager.saveData(cache.getUniqueId(), call -> {}));
    }

    @EventHandler
    protected void onPlayerLogin(AsyncPlayerPreLoginEvent event) {
        //TODO pull from database
        profileManager.loadData(new LobbyProfile(event.getUniqueId(), event.getName()), call -> {
            if (Lobby.class.isAnnotationPresent(Module.class)) {
                PluginState state = Lobby.class.getAnnotation(Module.class).state();
                if (state == PluginState.PRE_ALPHA || state == PluginState.ALPHA) {
                    if (!call.getRole().hasRole(Role.MODERATOR)) {
                        event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
                        event.setKickMessage(C.RED + "You don't have permission to join this server.");
                    }
                }
            }
        });

        new GadgetDAO(profileManager.getData(event.getUniqueId())).fetch(database, call -> ServerUtil.logDebug("Gadgets loaded for " + event.getName()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(event.getPlayer() == null) return;
        profileManager.saveData(event.getPlayer().getUniqueId(), call -> ServerUtil.logDebug("Data saved for " + call.getName()));
    }

    @EventHandler
    protected void onPlayerJoin(PlayerJoinEvent event) {
        if(event.getPlayer() == null) return;
        event.getPlayer().getInventory().clear();
        event.getPlayer().getInventory().setItem(4, new ItemFactory(Material.SKULL_ITEM, (byte) 3).setName("Profile").setOwner(event.getPlayer().getName()).build());
    }

    @EventHandler
    protected void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getPlayer() == null) return;
        if(event.getItem() == null) return;

        //Profile
        if(event.getItem().getType() == Material.SKULL_ITEM) {
            new ProfileInventory(cosmeticManager, profileManager.getData(event.getPlayer().getUniqueId())).openInventory(event.getPlayer());
            return;
        }

        //TODO: More lobby inventory items.
    }

    @EventHandler
    protected void onItemDrop(PlayerDropItemEvent event) {
        if(event.getPlayer() == null) return;
        event.setCancelled(!allowItemDrop);
        event.getPlayer().updateInventory();
    }

    @EventHandler
    protected void onConsume(PlayerItemConsumeEvent event) {
        if(event.getPlayer() == null) return;
        event.setCancelled(!allowConsume);
        event.getPlayer().updateInventory();
    }

    @EventHandler (ignoreCancelled = true)
    protected void onBlockBreak(BlockBreakEvent event) {
        if(event.getPlayer() == null) return;
        if(event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        event.setCancelled(!allowBlockBreak);
    }

    @EventHandler (ignoreCancelled = true)
    protected void onBlockPlace(BlockPlaceEvent event) {
        if(event.getPlayer() == null) return;
        if(event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        event.setCancelled(!allowBlockPlace);
    }

    @EventHandler
    protected void onHungerDrain(FoodLevelChangeEvent event) {
        if(event.getEntity() == null) return;
        event.setCancelled(!allowHungerDrain);
    }

    @EventHandler
    protected void onDamage(EntityDamageEvent event) {
        if (event.getEntity() == null) return;
        if (event.getEntity() instanceof Player)
            event.setCancelled(!allowDamage);
    }

    public void setAllowItemDrop(boolean allowItemDrop) {
        this.allowItemDrop = allowItemDrop;
    }

    public void setAllowConsume(boolean allowConsume) {
        this.allowConsume = allowConsume;
    }

    public void setAllowBlockBreak(boolean allowBlockBreak) {
        this.allowBlockBreak = allowBlockBreak;
    }

    public void setAllowBlockPlace(boolean allowBlockPlace) {
        this.allowBlockPlace = allowBlockPlace;
    }

    public void setAllowHungerDrain(boolean allowHungerDrain) {
        this.allowHungerDrain = allowHungerDrain;
    }

    public void setAllowDamage(boolean allowDamage) {
        this.allowDamage = allowDamage;
    }
}
