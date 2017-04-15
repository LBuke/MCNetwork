package me.lukebingham.gta.guns;

import me.lukebingham.gta.attributes.Accuracy;
import me.lukebingham.gta.attributes.Damage;
import me.lukebingham.gta.attributes.FireRate;
import me.lukebingham.gta.attributes.Range;
import me.lukebingham.gta.guns.assult.AK47;
import me.lukebingham.gta.guns.assult.HLBullpupRifle;
import me.lukebingham.gta.guns.assult.M16A1;
import me.lukebingham.gta.guns.assult.Shrewsbury;
import me.lukebingham.gta.guns.assult.ShrewsburyCompactRifile;
import me.lukebingham.gta.guns.assult.VomFeuerAdvancedRifle;
import me.lukebingham.gta.guns.assult.VomFeuerRifle;
import me.lukebingham.gta.guns.assult.VomFeuerSpecialRifile;
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
                new M16A1(),
                new AK47(),
                new Shrewsbury(),
                new ShrewsburyCompactRifile(),
                new VomFeuerRifle(),
                new VomFeuerSpecialRifile(),
                new VomFeuerAdvancedRifle(),
                new HLBullpupRifle()
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
