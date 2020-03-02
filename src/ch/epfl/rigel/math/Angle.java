package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

/**
 * Utility class for angles
 *
 * @author Antoine Moix (310052)
 */
public final class Angle {

    public final static double TAU = Math.PI * 2.0; //We define here the Tau constant
    public final static double HOUR_PER_RAD = 24.0 / TAU; //Hours per radian
    public final static double RAD_PER_HOUR = TAU / 24.0; //Radians per hour
    public final static double DEGREES_PER_SECOND = 1.0 / 3600.0; //Hours per second
    private static final double DEGREES_PER_MINUTE = 1.0/60.0;

    private Angle() {
    }

    /**
     * Normalizes the value on a positive interval between 0 and TAU
     *
     * @param rad value to normalize on the interval
     * @return the normalized value
     */
    public static double normalizePositive(double rad) {
        RightOpenInterval interval = RightOpenInterval.of(0, TAU);
        return interval.reduce(rad);
    }

    /**
     * Convert a given angle from seconds to radians
     *
     * @param sec angle in seconds
     * @return the same angle in radians
     */
    public static double ofArcsec(double sec) {
        return ofDeg(sec * DEGREES_PER_SECOND);
    }

    /**
     * Convert an angle from DMS to radians
     *
     * @param deg degrees of the angle to convert
     * @param min minutes of the angle to convert
     * @param sec seconds of the angle to convert
     * @throws IllegalArgumentException if the min or sec not in the right interval
     * @return the angle in radians
     */
    public static double ofDMS(int deg, int min, double sec) {
        Preconditions.checkArgument(min >= 0 && min < 60);
        Preconditions.checkArgument(sec >= 0 && sec < 60);

        double result = ofDeg(deg);
        result += ofDeg(min * DEGREES_PER_MINUTE);
        result += ofArcsec(sec);

        return result;
    }

    /**
     * Converts an angle from degrees to radians
     *
     * @param deg the angle in degrees to be converted
     * @return the corresponding in radians
     */
    public static double ofDeg(double deg) {
        return Math.toRadians(deg);
    }

    /**
     * Returns the value in degrees for a given radian angle
     *
     * @param rad angle in radians to convert
     * @return the given angle in degrees
     */
    public static double toDeg(double rad) {
        return Math.toDegrees(rad);
    }

    /**
     * Convert an angle from hours to radians
     *
     * @param hr the angle in hours to convert in radians
     * @return the same angle in radians
     */
    public static double ofHr(double hr) {
        return hr * RAD_PER_HOUR;
    }

    /**
     * Convert an angle in radians to its hours counterpart
     *
     * @param rad the angle to convert
     * @return the angle in radian
     */
    public static double toHr(double rad) {
        return rad * HOUR_PER_RAD;
    }

}
