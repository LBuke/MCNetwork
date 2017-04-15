package me.lukebingham.util;

/**
 * Created by LukeBingham on 17/03/2017.
 */
public enum ServerType {
    LOBBY(512),
    GTA(512),

    //Games
    SKYWARS(512),

    //Unknown
    OTHER(-1),
    ;

    private final int mb;

    ServerType(int mb) {
        this.mb = mb;
    }

    public final int getMb() {
        return mb;
    }
}
