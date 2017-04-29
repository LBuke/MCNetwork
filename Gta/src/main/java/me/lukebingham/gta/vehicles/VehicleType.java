package me.lukebingham.gta.vehicles;

/**
 * Created by LukeBingham on 28/04/2017.
 */
public enum VehicleType {
    CAR(1),
    MOTORCYCLE(2),
    PLANE(3),
    HELICOPTER(4),
    BOAT(5),
    ;

    private final int id;

    VehicleType(int id) {
        this.id = id;
    }

    /**
     * Get the Unique Identifier of the Vehicle Type.
     *
     * @return Unique Identifier
     */
    public int getUniqueIdentifier() {
        return id;
    }

    /**
     * Get the Vehicle Type by the Unique Identifier.
     *
     * @param input Unique Identifier
     * @return VehicleType
     */
    public static VehicleType getById(int input) {
        for(VehicleType type : values()) {
            if(type.id == input) return type;
        }
        return null;
    }
}
