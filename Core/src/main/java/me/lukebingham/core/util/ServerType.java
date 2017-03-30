package me.lukebingham.core.util;

/**
 * Created by LukeBingham on 17/03/2017.
 */
public enum ServerType {
    LOBBY(512),

    //Games
    SKYWARS(512),
    ;

    private int mb;

    ServerType(int mb) {
        this.mb = mb;
    }

    public int getMb() {
        return mb;
    }
}
