package me.lukebingham.gta.weapon;

/**
 * Created by LukeBingham on 28/04/2017.
 */
public enum WeaponType {
    GUN(1),
    MELEE(2),
    ;

    private int id;

    WeaponType(int id) {
        this.id = id;
    }

    /**
     * Get the Unique Identifier of the Weapon Type.
     *
     * @return Unique Identifier
     */
    public int getUniqueIdentifier() {
        return id;
    }

    /**
     * Get the Weapon Type by the Unique Identifier.
     *
     * @param input Unique Identifier
     * @return Weapon Type
     */
    public static WeaponType getById(int input) {
        for(WeaponType type : values()) {
            if(type.id == input) return type;
        }
        return null;
    }
}
