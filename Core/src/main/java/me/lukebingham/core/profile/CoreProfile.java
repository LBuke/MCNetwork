package me.lukebingham.core.profile;

import me.lukebingham.core.currency.Currency;
import me.lukebingham.core.graphics.GraphicsType;
import me.lukebingham.core.i18n.Locale;
import me.lukebingham.core.util.rank.Rank;
import me.lukebingham.core.util.rank.Role;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by LukeBingham on 23/02/2017.
 */
public final class CoreProfile {

    private final UUID uniqueId;
    private final String name;
    private String displayName = "null";
    private Role role = Role.ADMINISTRATOR;
    private Rank rank = Rank.MEMBER;
    private Locale locale = Locale.en_US;
    private int graphics = 3;

    private Currency[] currencies;

    public CoreProfile(UUID uniqueId, String name) {
        this.uniqueId = uniqueId;
        this.name = name;
    }

    public CoreProfile(Player player) {
        this(player.getUniqueId(), player.getName());
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public String getName() {
        return this.name;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Rank getRank() {
        return this.rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Currency[] getCurrencies() {
        return this.currencies;
    }

    public Currency getCurrency(Class<? extends Currency> currency) {
        Optional<Currency> optional = Arrays.stream(this.currencies).filter(c -> c.getClass().getSimpleName().equals(currency.getSimpleName())).findAny();
        return optional.isPresent() ? optional.get() : null;
    }

    public void setCurrencies(Currency... currencies) {
        this.currencies = currencies;
    }

    public void setGraphics(int graphics) {
        this.graphics = graphics;
    }

    public GraphicsType getGraphics() {
        return GraphicsType.getById(this.graphics);
    }
}
