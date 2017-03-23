package me.lukebingham.core.profile;

import com.google.common.collect.Sets;
import me.lukebingham.core.util.Callback;
import me.lukebingham.core.util.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by LukeBingham on 23/02/2017.
 */
public class ProfileManager<Profile extends CoreProfile> implements Component {

    private static ProfileManager instance;

    private final HashSet<Profile> playerCache;

    public ProfileManager() {
        instance = this;
        playerCache = Sets.newHashSet();
    }

    @Override
    public boolean disableAble() {
        return false;
    }

    @Override
    public void onDisable() {
        playerCache.forEach(t -> saveCache(t.getUniqueId(), (call) -> {}));
    }

    public Profile getCache(UUID uniqueId) {
        Optional<Profile> optional = playerCache.stream().filter(t -> t.getUniqueId().equals(uniqueId)).findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

    public void removeCache(UUID uniqueId) {
        playerCache.remove(getCache(uniqueId));
    }

    public boolean isCached(UUID uniqueId) {
        return playerCache.stream().anyMatch(profile -> profile.getUniqueId().equals(uniqueId));
    }

    public void cacheProfile(Profile data, Callback<Profile> callback) {
        //TODO grab from database
        playerCache.add(data);
        callback.call(data);
    }

    public void saveCache(UUID uniqueId, Callback<Profile> callback) {
        //TODO save to database
        Profile profile = getCache(uniqueId);
        callback.call(profile);
    }

    public HashSet<Profile> getPlayerCache() {
        return playerCache;
    }

    public static ProfileManager getInstance() {
        return instance;
    }
}
