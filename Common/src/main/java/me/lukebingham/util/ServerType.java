package me.lukebingham.util;

/**
 * Created by LukeBingham on 17/03/2017.
 */
public enum ServerType {
    LOBBY(512),

    //Games
    SKYWARS(512),
    ;

    private final int mb;

    ServerType(int mb) {
        this.mb = mb;
    }

    public final int getMb() {
        return mb;
    }
}
