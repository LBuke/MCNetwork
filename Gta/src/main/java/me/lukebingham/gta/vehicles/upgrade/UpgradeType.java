package me.lukebingham.gta.vehicles.upgrade;

/**
 * Created by LukeBingham on 28/04/2017.
 */
public enum UpgradeType {
    ARMOUR(1, 101, true),
    BRAKES(2, 102, true),
    BUMPERS(3, -1, false),
    ENGINE(4, 103, true),
    EXHAUST(5, -1, false),
    HOOD(6, -1, false),
    LIGHTS(7, -1, false),
    TRACKER(8, 104, true),
    SPRAY(9, -1, false),
    SKIRTS(10, -1, false),
    SPOILER(11, -1, false),
    SUSPENSION(12, -1, false),
    TRANSMISSION(13, 105, true),
    TURBO(14, 106, true),
    WHEELS(15, -1, false),
    WINDOWS(16, -1, false),
    ;

    private final int id;
    private final int enchantIdentifier;
    private final boolean enabled;

    UpgradeType(int id, int enchantIdentifier, boolean enabled) {
        this.id = id;
        this.enchantIdentifier = enchantIdentifier;
        this.enabled = enabled;
    }

    /**
     * Get the Unique Identifier of the Upgrade Type.
     *
     * @return Unique Identifier
     */
    public final int getUniqueIdentifier() {
        return id;
    }

    /**
     * Get the Enchantment Identifier.
     *
     * @return Enchantment Identifier
     */
    public final int getEnchantIdentifier() {
        return enchantIdentifier;
    }

    /**
     * If the Upgrade is not enabled, They won't be shown in production.
     *
     * @return Enabled status
     */
    public final boolean isEnabled() {
        return enabled;
    }

    /**
     * Get the Upgrade Type by the Unique Identifier.
     *
     * @param input Unique Identifier
     * @return UpgradeType
     */
    public static UpgradeType getById(int input) {
        for(UpgradeType type : values()) {
            if(type.id == input) return type;
        }
        return null;
    }

}
