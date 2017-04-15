package me.lukebingham.gta.guns;

import me.lukebingham.gta.attributes.Accuracy;
import me.lukebingham.gta.attributes.Damage;
import me.lukebingham.gta.attributes.FireRate;
import me.lukebingham.gta.attributes.Range;
import me.lukebingham.gta.guns.type.assult.AK47;
import me.lukebingham.gta.guns.type.assult.HLBullpupRifle;
import me.lukebingham.gta.guns.type.assult.M16A1;
import me.lukebingham.gta.guns.type.assult.Shrewsbury;
import me.lukebingham.gta.guns.type.assult.ShrewsburyCompactRifile;
import me.lukebingham.gta.guns.type.assult.VomFeuerAdvancedRifle;
import me.lukebingham.gta.guns.type.assult.VomFeuerRifle;
import me.lukebingham.gta.guns.type.assult.VomFeuerSpecialRifile;
import me.lukebingham.gta.guns.type.pistols.APPistolVomFeuerAPPistol;
import me.lukebingham.gta.guns.type.pistols.CoilStunGun;
import me.lukebingham.gta.guns.type.pistols.FlareGun;
import me.lukebingham.gta.guns.type.pistols.HLCombatPistol;
import me.lukebingham.gta.guns.type.pistols.HLHeavyRevolver;
import me.lukebingham.gta.guns.type.pistols.HLPistol;
import me.lukebingham.gta.guns.type.pistols.HLPistol50;
import me.lukebingham.gta.guns.type.pistols.HeavyPistol;
import me.lukebingham.gta.guns.type.pistols.MarksmanPistol;
import me.lukebingham.gta.guns.type.pistols.ShrewsburySNSPistol;
import me.lukebingham.gta.guns.type.pistols.VintagePistol;
import me.lukebingham.gta.guns.type.shotguns.DoubleBarreledShotgun;
import me.lukebingham.gta.guns.type.shotguns.HLBullpupShotgun;
import me.lukebingham.gta.guns.type.shotguns.Musket;
import me.lukebingham.gta.guns.type.shotguns.ShrewsburyHeavyShotgun;
import me.lukebingham.gta.guns.type.shotguns.ShrewsburyPumpShotgun;
import me.lukebingham.gta.guns.type.shotguns.ShrewsburySawedOffShotgun;
import me.lukebingham.gta.guns.type.shotguns.VomFeuerAssaultShotgun;
import me.lukebingham.util.C;

import java.util.Arrays;
import java.util.HashSet;

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
                new HLHeavyRevolver()

        ));
    }

    public HashSet<Gun> getGuns() {
        return guns;
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
