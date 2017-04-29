package me.lukebingham.gta.vehicles.upgrade;

import java.lang.annotation.Annotation;

/**
 * Created by LukeBingham on 29/04/2017.
 */
public interface VehicleUpgradeEnchant<Attribute extends Annotation> {

    /**
     * Get the Vehicle Attribute class.
     *
     * @return Attribute Class
     */
    Class<Attribute> getAttributeAnnotation();
}
