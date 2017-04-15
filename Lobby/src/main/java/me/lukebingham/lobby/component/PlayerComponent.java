package me.lukebingham.lobby.component;

import me.lukebingham.core.Core;
import me.lukebingham.core.cosmetic.CosmeticManager;
import me.lukebingham.core.cosmetic.gadget.gadgets.CookieGadget;
import me.lukebingham.database.Database;
import me.lukebingham.core.graphics.GraphicsManager;
import me.lukebingham.core.module.Module;
import me.lukebingham.core.module.PluginState;
import me.lukebingham.core.profile.ProfileManager;
import me.lukebingham.util.C;
import me.lukebingham.core.util.Component;
import me.lukebingham.core.util.ServerUtil;
import me.lukebingham.core.util.factory.ItemFactory;
import me.lukebingham.core.util.rank.Role;
import me.lukebingham.lobby.Lobby;
import me.lukebingham.lobby.cosmetic.gadget.GadgetDAO;
import me.lukebingham.lobby.profile.LobbyProfile;
import me.lukebingham.lobby.profile.ProfileInventory;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.*;

/**
 * Created by LukeBingham on 23/02/2017.
 */
public final class PlayerComponent implements Component {

    private final Core core;
    private final Database database;
    private final CosmeticManager cosmeticManager;
    private final GraphicsManager graphicsManager;
    private final ProfileManager<LobbyProfile> profileManager;

    private boolean allowItemDrop, allowConsume, allowBlockBreak, allowBlockPlace,
            allowHungerDrain, allowDamage;

    public PlayerComponent(Core core, Database database, CosmeticManager cosmeticManager, GraphicsManager graphicsManager, ProfileManager<LobbyProfile> profileManager) {
        this.core = core;
        this.database = database;
        this.cosmeticManager = cosmeticManager;
        this.graphicsManager = graphicsManager;
        this.profileManager = profileManager;
    }

    @Override
    public final boolean disableAble() {
        return false;
    }

    @Override
    public final void onDisable() {
        profileManager.getPlayerCache().forEach(cache -> profileManager.saveCache(cache.getUniqueId(), call -> {}));
    }

    @EventHandler
    protected final void onPlayerLogin(AsyncPlayerPreLoginEvent event) {
        //TODO pull from database
        profileManager.cacheProfile(new LobbyProfile(event.getUniqueId(), event.getName()), call -> {
            if (Lobby.class.isAnnotationPresent(Module.class)) {
                PluginState state = Lobby.class.getAnnotation(Module.class).state();
                if (state == PluginState.PRE_ALPHA || state == PluginState.ALPHA) {
                    if (!call.getRole().hasRole(Role.MODERATOR)) {
                        event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
                        event.setKickMessage(C.RED + "You don't have permission to join this server.");
                    }
                }
            }

            new GadgetDAO(call).fetch(database, object -> ServerUtil.logDebug("Gadgets loaded for " + call.getName()));
            call.addGadgetData(new CookieGadget());
        });
    }

    @EventHandler
    protected final void onPlayerQuit(PlayerQuitEvent event) {
        if(event.getPlayer() == null) return;
        profileManager.saveCache(event.getPlayer().getUniqueId(), call -> {
            new GadgetDAO(call).save(database, object -> ServerUtil.logDebug("Gadget DAO saved for " + call.getName()));
            ServerUtil.logDebug("Profile saved for " + call.getName());
        });
    }

    @EventHandler
    protected final void onPlayerJoin(PlayerJoinEvent event) {
        if(event.getPlayer() == null) return;

        if(!event.getPlayer().isOp())
            event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0.5, 34, 0.5, 45.0f, 5.0f));

        event.getPlayer().getInventory().clear();
        event.getPlayer().getInventory().setItem(4, new ItemFactory(Material.SKULL_ITEM, (byte) 3).setName("Profile").setOwner(event.getPlayer().getName()).build());
    }

    @EventHandler
    protected final void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getPlayer() == null) return;
        if(event.getItem() == null) return;

        //Profile
        if(event.getItem().getType() == Material.SKULL_ITEM) {
            new ProfileInventory(cosmeticManager, graphicsManager, profileManager.getCache(event.getPlayer().getUniqueId()))
                    .openInventory(event.getPlayer());
            return;
        }

        //TODO: More lobby inventory items.
    }

    @EventHandler
    protected final void onItemDrop(PlayerDropItemEvent event) {
        if(event.getPlayer() == null) return;
        event.setCancelled(!allowItemDrop);
        event.getPlayer().updateInventory();
    }

    @EventHandler
    protected final void onConsume(PlayerItemConsumeEvent event) {
        if(event.getPlayer() == null) return;
        event.setCancelled(!allowConsume);
        event.getPlayer().updateInventory();
    }

    @EventHandler (ignoreCancelled = true)
    protected final void onBlockBreak(BlockBreakEvent event) {
        if(event.getPlayer() == null) return;
        if(event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        event.setCancelled(!allowBlockBreak);
    }

    @EventHandler (ignoreCancelled = true)
    protected final void onBlockPlace(BlockPlaceEvent event) {
        if(event.getPlayer() == null) return;
        if(event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        event.setCancelled(!allowBlockPlace);
    }

    @EventHandler
    protected final void onHungerDrain(FoodLevelChangeEvent event) {
        if(event.getEntity() == null) return;
        event.setCancelled(!allowHungerDrain);
    }

    @EventHandler
    protected final void onDamage(EntityDamageEvent event) {
        if (event.getEntity() == null) return;
        if (event.getEntity() instanceof Player)
            event.setCancelled(!allowDamage);
    }

    @EventHandler
    protected final void onItemMove(InventoryClickEvent event) {
        if(event.getWhoClicked() == null) return;
        if(event.getWhoClicked().getGameMode() == GameMode.CREATIVE) return;
        event.setCancelled(!event.getWhoClicked().isOp());
    }

    @EventHandler
    protected final void onItemMove(InventoryMoveItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    protected final void onItemMove(InventoryDragEvent event) {
        if(event.getWhoClicked() == null) return;
        if(event.getWhoClicked().getGameMode() == GameMode.CREATIVE) return;
        event.setCancelled(!event.getWhoClicked().isOp());
    }

    public final void setAllowItemDrop(boolean allowItemDrop) {
        this.allowItemDrop = allowItemDrop;
    }

    public final void setAllowConsume(boolean allowConsume) {
        this.allowConsume = allowConsume;
    }

    public final void setAllowBlockBreak(boolean allowBlockBreak) {
        this.allowBlockBreak = allowBlockBreak;
    }

    public final void setAllowBlockPlace(boolean allowBlockPlace) {
        this.allowBlockPlace = allowBlockPlace;
    }

    public final void setAllowHungerDrain(boolean allowHungerDrain) {
        this.allowHungerDrain = allowHungerDrain;
    }

    public final void setAllowDamage(boolean allowDamage) {
        this.allowDamage = allowDamage;
    }
}
