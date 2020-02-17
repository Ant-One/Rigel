package ch.epfl.rigel.math;

/**
 * Utilitary class for angles
 * @author Antoine Moix (310052)
 */
public final class Angle {

    public final static double TAU = Math.PI * 2.0; //We define here the Tau constant
    public final static double HOUR_PER_RAD = 24/TAU; //Radians per hour

    private Angle(){
    }

    /**
     * Normalizes the value on a postive interval between 0 and TAU
     * @param rad value to normalize on the interval
     * @return the normalized value
     */
    double normalizePositive(double rad){
        RightOpenInterval interval = RightOpenInterval.of(0, TAU);
        return interval.reduce(rad);
    }

    double ofArcsec(double sec){
        return 0;
    }

    double ofDMS(int deg, int min, double sec){
        return 0;
    }

    /**
     * Converts an angle from degrees to radians
     * @param deg the angle in degrees to be converted
     * @return the corresponding in radians
     */
    double ofDeg(double deg){
        return Math.toRadians(deg);
    }

    /**
     * Returns the value in degrees for a given radian angle
     * @param rad angle in radians to convert
     * @return the given angle in degrees
     */
    double toDeg(double rad){
        return Math.toDegrees(rad);
    }

    double ofHr(double hr){
        return 0;
    }

    /**
     * Convert an angle in radians to its hours counterpart
     * @param rad the angle to convert
     * @return the angle in radian
     */
    double toHr(double rad){
        return rad * HOUR_PER_RAD;
    }

}
