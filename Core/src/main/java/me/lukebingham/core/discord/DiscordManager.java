package me.lukebingham.core.discord;

import me.lukebingham.core.util.ServerUtil;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RateLimitException;

/**
 * Created by ThatAbstractWolf on 2017-04-16.
 */
public class DiscordManager {

	private IDiscordClient client;
	private final String TOKEN = "MzAzMTU4NTM1ODM4MjM2Njkz.C9UFwQ.XaGfcRwV4heERA3YL3PRIIXJXCM";

	public void onEnable() {
		setupClient();

		try {
			getClient().login();

			if (getClient().isReady()) {
				IDiscordClient discordClient = getClient();

				discordClient.changeUsername("MCNetwork Bot");
				discordClient.changePresence(true);

				for (IRole role : discordClient.getRoles()) {
					ServerUtil.logDebug("Role: " + role.getName());
				}
			}
		} catch (DiscordException | RateLimitException e) {
			e.printStackTrace();
		}
	}
	public void onDisable() {
		try {
			getClient().logout();
			ServerUtil.logError("Logged out of Discord! Bot now offline.");
		} catch (DiscordException e) {
			e.printStackTrace();
		}
	}

	private void setupClient() {

		ClientBuilder builder = new ClientBuilder();
		ServerUtil.logDebug("Written Token: '" + TOKEN + "'!");
		builder.withToken(TOKEN);
		ServerUtil.logDebug("Found Token: '" + builder.getToken() + "'!");

		try {
			client = builder.build();
		} catch (DiscordException e) {
			e.printStackTrace();
		}
	}

	public IDiscordClient getClient() {
		return client;
	}
}
