package me.lukebingham.core.i18n;

import com.google.common.collect.Maps;
import me.lukebingham.core.profile.CoreProfile;
import me.lukebingham.core.profile.ProfileManager;
import me.lukebingham.core.util.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by LukeBingham on 19/03/2017.
 */
public class I18n {

    private static final HashMap<Locale, Properties> properties;

    static {
        properties = Maps.newHashMap();

        for (Locale locale : Locale.values()) {
            try {
                Properties prop = new Properties();
                InputStream inputStream = I18n.class.getClassLoader().getResourceAsStream(locale.getTag() + ".properties");
                prop.load(inputStream);
                inputStream.close();
                properties.put(locale, prop);
                ServerUtil.logDebug("Loaded locale: " + locale.getTag());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T extends CoreProfile> String get(T profile, I18nMessage message, String... arguments) {
        String prop = properties.get(profile.getLocale()).getProperty(message.getKey());
        for(int i = 0; i < arguments.length; i++) prop = prop.replace("{" + i + "}", arguments[i]);
        return ChatColor.translateAlternateColorCodes('&', prop);
    }

    public static <T extends CoreProfile> String get(T profile, String message, String... arguments) {
        String prop = properties.get(profile.getLocale()).getProperty(message);
        for(int i = 0; i < arguments.length; i++) prop = prop.replace("{" + i + "}", arguments[i]);
        return ChatColor.translateAlternateColorCodes('&', prop);
    }

    public static void message(Player player, I18nMessage message, String... arguments) {
        if(player == null || !player.isOnline()) return;
        CoreProfile profile = ProfileManager.getInstance().getCache(player.getUniqueId());
        if(profile == null) return;
        String prop = properties.get(profile.getLocale()).getProperty(message.getKey());
        for(int i = 0; i < arguments.length; i++) {
            prop = prop.replace("{" + i + "}", arguments[i]);
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prop));
    }

    public static void message(Player player, String message, String... arguments) {
        if(player == null || !player.isOnline()) return;
        CoreProfile profile = ProfileManager.getInstance().getCache(player.getUniqueId());
        if(profile == null) return;
        String prop = properties.get(profile.getLocale()).getProperty(message);
        for(int i = 0; i < arguments.length; i++) {
            prop = prop.replace("{" + i + "}", arguments[i]);
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prop));
    }

    public static <T extends CoreProfile> void message(T profile, I18nMessage message, String... arguments) {
        Player player = Bukkit.getPlayer(profile.getUniqueId());
        if(player == null || !player.isOnline()) return;
        String prop = properties.get(profile.getLocale()).getProperty(message.getKey());
        for(int i = 0; i < arguments.length; i++) {
            prop = prop.replace("{" + i + "}", arguments[i]);
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prop));
    }

    public static <T extends CoreProfile> void message(T profile, String message, String... arguments) {
        Player player = Bukkit.getPlayer(profile.getUniqueId());
        if(player == null || !player.isOnline()) return;
        String prop = properties.get(profile.getLocale()).getProperty(message);
        for(int i = 0; i < arguments.length; i++) {
            prop = prop.replace("{" + i + "}", arguments[i]);
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prop));
    }
}
