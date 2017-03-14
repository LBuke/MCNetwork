package me.lukebingham.core.profile;

import com.google.common.collect.Sets;
import me.lukebingham.core.util.Callback;
import me.lukebingham.core.util.Component;

import java.util.HashSet;
import java.util.UUID;

/**
 * Created by LukeBingham on 23/02/2017.
 */
public class ProfileManager<T extends CoreProfile> implements Component {

    private final HashSet<T> playerCache;

    public ProfileManager() {
        playerCache = Sets.newHashSet();
    }

    @Override
    public boolean disableAble() {
        return false;
    }

    @Override
    public void onDisable() {
        playerCache.forEach(t -> saveData(t.getUniqueId(), (call) -> {}));
    }

    public T getData(UUID uniqueId) {
        return playerCache.stream().filter(t -> t.getUniqueId().equals(uniqueId)).findFirst().get();
    }

    public void removeData(UUID uniqueId) {
        playerCache.remove(uniqueId);
    }

    public void loadData(T data, Callback<T> callback) {
        //TODO grab from database
        playerCache.add(data);
        callback.call(data);
    }

    public void saveData(UUID uniqueId, Callback<T> callback) {
        //TODO save to database
        callback.call(getData(uniqueId));
    }

    public HashSet<T> getPlayerCache() {
        return playerCache;
    }
}
