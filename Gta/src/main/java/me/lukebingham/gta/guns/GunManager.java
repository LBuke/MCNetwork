package me.lukebingham.gta.guns;

import me.lukebingham.gta.attributes.Accuracy;
import me.lukebingham.gta.attributes.Damage;
import me.lukebingham.gta.attributes.FireRate;
import me.lukebingham.gta.attributes.Range;
import me.lukebingham.gta.guns.type.assult.*;
import me.lukebingham.gta.guns.type.sniper.*;
import me.lukebingham.gta.guns.type.submachine.*;
import me.lukebingham.gta.guns.type.shotguns.*;
import me.lukebingham.gta.guns.type.pistols.*;
import me.lukebingham.util.C;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

/**
 * Created by LukeBingham on 13/04/2017.
 */
public class GunManager {

    private final HashSet<Gun> guns;

    public GunManager() {
        guns = new HashSet<>();
        guns.addAll(Arrays.asList(
                // Rifles
                new M16A1(),
                new AK47(),
                new Shrewsbury(),
                new ShrewsburyCompactRifile(),
                new VomFeuerRifle(),
                new VomFeuerSpecialRifile(),
                new VomFeuerAdvancedRifle(),
                new HLBullpupRifle(),
                // Shotguns
                new DoubleBarreledShotgun(),
                new HLBullpupShotgun(),
                new Musket(),
                new ShrewsburyHeavyShotgun(),
                new ShrewsburyPumpShotgun(),
                new ShrewsburySawedOffShotgun(),
                new VomFeuerAssaultShotgun(),
                // Pistols
                new HLPistol(),
                new HLPistol50(),
                new HLCombatPistol(),
                new CoilStunGun(),
                new APPistolVomFeuerAPPistol(),
                new ShrewsburySNSPistol(),
                new HeavyPistol(),
                new VintagePistol(),
                new MarksmanPistol(),
                new FlareGun(),
                new HLHeavyRevolver(),
                // Sniper Rifles
                new ShrewsburySniperRifle(),
                new VomFeuerHeavySniper(),
                new VomFeuerMarksmanRifle(),
                // Sub Machine
                new ShrewsburyMicroSMG(),
                new HLSMG(),
                new VomFeuerAssault(),
                new CoilCombatPDW()
        ));
    }

    public HashSet<Gun> getGuns() {
        return guns;
    }

    public Optional<Gun> getGun(Material material, short durability) {
        return guns.stream().filter(gun -> gun.getGunItem() == material && gun.getGunId() == durability).findAny();
    }

    public Damage getDamage(Gun gun) {
        return gun.getClass().getAnnotation(Damage.class);
    }

    public Accuracy getAccuracy(Gun gun) {
        return gun.getClass().getAnnotation(Accuracy.class);
    }

    public FireRate getFireRate(Gun gun) {
        return gun.getClass().getAnnotation(FireRate.class);
    }

    public Range getRange(Gun gun) {
        return gun.getClass().getAnnotation(Range.class);
    }

    public static String getProgress(float value) {
        StringBuilder bar = new StringBuilder();
        for(int i = 0; i < 10; i++) bar.append(value <= i ? C.GRAY : C.GREEN).append(C.STRIKE + "-");
        return bar.append(C.RESET).toString();
    }
}
