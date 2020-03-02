package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import java.util.Locale;

/**
 * Ecliptic coordinates
 *
 * @author Adrien Rey (313388)
 */
public final class EclipticCoordinates extends SphericalCoordinates {

    private static final RightOpenInterval longitudeInterval = RightOpenInterval.of(0,Angle.TAU);
    private static final ClosedInterval latitudeInterval = ClosedInterval.symmetric(Angle.TAU / 4);


    /**
     * Package private ecliptic coordinates constructor
     *
     * @param longitude , longitude in rad
     * @param latitude  , latitude in rad
     */
    private EclipticCoordinates(double longitude, double latitude) {

        super(longitude, latitude);
    }

    /**
     * Constructor of ecliptic coordinates
     *
     * @param lon longitude in degree
     * @param lat latitude in degree
     * @return the new coordinates
     */
    public static EclipticCoordinates of(double lon, double lat) {

        Preconditions.checkInInterval(latitudeInterval, lat);
        Preconditions.checkInInterval(longitudeInterval, lon);
        return new EclipticCoordinates(lon, lat);

    }


    /**
     * @return the longitude in radian
     */
    public double lon() {
        return super.lon();
    }

    /**
     * @return the longitude in deg
     */
    public double lonDeg() {
        return super.lonDeg();
    }


    /**
     * @return the latitude in radian
     */
    public double lat() {

        return super.lat();
    }

    /**
     * @return the latitude in deg
     */

    public double latDeg() {
        return super.latDeg();
    }


    @Override
    public String toString() {
        return String.format(Locale.ROOT, "(λ=%.4f°, β=%.4f°)", lonDeg(), latDeg());
    }
}
