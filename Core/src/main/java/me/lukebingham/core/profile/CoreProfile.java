package me.lukebingham.core.profile;

import me.lukebingham.core.util.Role;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by LukeBingham on 23/02/2017.
 */
public class CoreProfile {

    private UUID uniqueId;
    private String name;
    private String displayName = "null";
    private Role role = Role.ADMINISTRATOR;

    public CoreProfile(UUID uniqueId, String name) {
        this.uniqueId = uniqueId;
        this.name = name;
    }

    public CoreProfile(Player player) {
        this(player.getUniqueId(), player.getName());
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
