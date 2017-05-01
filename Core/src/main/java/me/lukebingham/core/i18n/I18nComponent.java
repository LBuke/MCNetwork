package me.lukebingham.core.i18n;

import me.lukebingham.core.packet.PacketModule;
import me.lukebingham.core.packet.PacketHandler;
import me.lukebingham.core.packet.PacketType;
import me.lukebingham.core.profile.CoreProfile;
import me.lukebingham.core.profile.ProfileManager;
import me.lukebingham.core.util.Component;
import me.lukebingham.core.util.ServerUtil;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInSettings;
import org.bukkit.entity.Player;

/**
 * Created by LukeBingham on 19/03/2017.
 */
public final class I18nComponent implements Component {

    private final ProfileManager<? extends CoreProfile> profileManager;

    public I18nComponent(ProfileManager<? extends CoreProfile> profileManager) {
        this.profileManager = profileManager;

        PacketModule.addPacketListener(new PacketHandler(PacketType.IN_SETTINGS) {
            @Override public void handle(Player player, Packet<?> packet) {
                PacketPlayInSettings settings = (PacketPlayInSettings) packet;

                CoreProfile profile = profileManager.getCache(player.getUniqueId());
                if(profile == null) return;
                if(settings.a().equals(profile.getLocale().getTag())) return;

                ServerUtil.logDebug(player.getName() + " has changed his language to: " + settings.a());
                profile.setLocale(Locale.fromString(settings.a()));
            }
        });
    }
}
