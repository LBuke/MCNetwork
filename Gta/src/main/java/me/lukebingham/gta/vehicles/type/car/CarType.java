package me.lukebingham.gta.vehicles.type.car;

/**
 * Created by LukeBingham on 28/04/2017.
 */
public enum CarType {
    COMPACT(1),
    COUPE(2),
    EMERGENCY(3),
    MUSCLE(4),
    OFFROAD(5),
    SEDAN(6),
    SPORT(7),
    SUV(8),
    VAN(9),
    ;

    private final int id;

    CarType(int id) {
        this.id = id;
    }

    /**
     * Get the Unique Identifier of the Car Type.
     *
     * @return Unique Identifier
     */
    public int getUniqueIdentifier() {
        return id;
    }

    /**
     * Get the Car Type by the Unique Identifier.
     *
     * @param input Unique Identifier
     * @return CarType
     */
    public static CarType getById(int input) {
        for(CarType type : values()) {
            if(type.id == input) return type;
        }
        return null;
    }
}
