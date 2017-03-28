package me.lukebingham.core.i18n;

import me.lukebingham.core.packet.PacketEvent;
import me.lukebingham.core.packet.PacketHandler;
import me.lukebingham.core.profile.CoreProfile;
import me.lukebingham.core.profile.ProfileManager;
import me.lukebingham.core.util.Component;
import me.lukebingham.core.util.ServerUtil;
import net.minecraft.server.v1_11_R1.PacketPlayInSettings;
import org.bukkit.event.EventHandler;

/**
 * Created by LukeBingham on 19/03/2017.
 */
public class I18nComponent implements Component {

    private ProfileManager<? extends CoreProfile> profileManager;

    public I18nComponent(ProfileManager<? extends CoreProfile> profileManager) {
        this.profileManager = profileManager;

        PacketHandler.addPacketListener(PacketPlayInSettings.class);
    }

    @EventHandler
    public void onLanguageChange(PacketEvent event) {
        if(event.getPacketType() != PacketEvent.PacketType.INWARDS) return;
        if(!(event.getPacket() instanceof PacketPlayInSettings)) return;
        PacketPlayInSettings packet = (PacketPlayInSettings) event.getPacket();
        CoreProfile profile = profileManager.getCache(event.getPlayer().getUniqueId());
        if(packet.a().equals(profile.getLocale().getTag())) return;
        ServerUtil.logDebug(event.getPlayer().getName() + " has changed his language to: " + packet.a());
        profile.setLocale(Locale.fromString(packet.a()));

    }
}
