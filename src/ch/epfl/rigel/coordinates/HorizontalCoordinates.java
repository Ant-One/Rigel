package ch.epfl.rigel.coordinates;


import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import java.util.Locale;

import static java.lang.Math.*;


/**
 * Class modelling horizontal coordinates
 *
 * @author Antoine Moix (310052)
 */
public final class HorizontalCoordinates extends SphericalCoordinates {

    private static final RightOpenInterval AZIMUTH_INTERVAL = RightOpenInterval.of(0, Angle.TAU);
    private static final ClosedInterval ALTITUDE_INTERVAL = ClosedInterval.of(-Angle.TAU / 4.0, Angle.TAU / 4.0);

    /**
     * Private constructor, only used by the HorizontalCoordinates of() method
     *
     * @param az  the azimuth in radians
     * @param alt the altitude in radians
     */
    private HorizontalCoordinates(double az, double alt) {
        super(az, alt);
    }

    /**
     * Static method to create a HorizontalCoordinates object via the azimuth and elevation angles in radians
     *
     * @param az  the azimuth angle in radians
     * @param alt the altitude angle in radians
     * @return the HorizontalCoordinates object created
     * @throws IllegalArgumentException if az or alt not in the right interval
     */
    public static HorizontalCoordinates of(double az, double alt) {
        Preconditions.checkInInterval(AZIMUTH_INTERVAL, az);
        Preconditions.checkInInterval(ALTITUDE_INTERVAL, alt);

        return new HorizontalCoordinates(az, alt);
    }

    /**
     * Similar to the of() method. It permits to create a HorizontalCoordinates object via the azimuth and elevation angles in degrees
     *
     * @param azDeg  the azimuth angle in degrees
     * @param altDeg the altitude angle in degrees
     * @return the HorizontalCoordinates object created
     */

    public static HorizontalCoordinates ofDeg(double azDeg, double altDeg) {
        return HorizontalCoordinates.of(Angle.ofDeg(azDeg), Angle.ofDeg(altDeg));
    }

    /**
     * Returns the azimuth/longitude angle in radians
     *
     * @return the azimuth/longitude in radians
     */
    public double az() {
        return lon();
    }

    /**
     * Returns the azimuth/longitude angle in degrees
     *
     * @return the azimuth/longitude in degrees
     */
    public double azDeg() {
        return Angle.toDeg(lon());
    }

    /**
     * Method used to obtain the string corresponding to the current octal of the azimuthal angle,
     * according to the strings passed as argument for respectively North, East, South, West
     *
     * @param n String to be used as the North's N
     * @param e String to be used as the East's E
     * @param s String to be used as the South's S
     * @param w String to be used as the West's W
     * @return The string created corresponding to the current octal of the azimuthal angle,
     * according to the strings passed as argument for respectively North, East, South, West
     */
    public String azOctantName(String n, String e, String s, String w) {

        int normalizedAz = (int) Math.round((Angle.normalizePositive(az())/(Angle.TAU/8)));

        switch (normalizedAz){
            default:
            case 0 :
            case 8 :
                return n;
            case 1 :
                return n + e;
            case 2 :
                return e;
            case 3 :
                return s + e;
            case 4 :
                return s;
            case 5 :
                return s + w;
            case 6 :
                return w;
            case 7 :
                return n + w;
        }
    }

    /**
     * Returns the altitude in radians
     *
     * @return the altitude in radians
     */
    public double alt() {
        return lat();

    }

    /**
     * Returns the altitude in degrees
     *
     * @return the altitude in degrees
     */
    public double altDeg() {
        return Angle.toDeg(lat());
    }

    /**
     * Computes the angular distance between theses HorizontalCoordinates and those passed as argument
     *
     * @param that the HorizontalCoordinates to which compute the angular distance
     * @return the angular distance between this and the parameter
     */
    public double angularDistanceTo(HorizontalCoordinates that) {
        double computeValue = sin(alt()) * sin(that.alt()) + cos(alt()) * cos(that.alt()) * cos(az() - that.az());

        return acos(computeValue);
    }

    /**
     * Returns the azimuth and altitude as a formatted String
     *
     * @return the azimuth and altitude as a formatted String
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT, "(az=%.4f°, alt=%.4f°)", azDeg(), altDeg());

    }
}
