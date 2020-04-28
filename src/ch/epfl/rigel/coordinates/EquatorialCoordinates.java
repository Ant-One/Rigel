package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import java.util.Locale;

/**
 * Equatorial coordinates
 *
 * @author Adrien Rey (313388)
 */
public final class EquatorialCoordinates extends SphericalCoordinates {

    private static final RightOpenInterval RA_INTERVAL = RightOpenInterval.of(0, Angle.TAU);
    private static final ClosedInterval DEC_INTERVAL = ClosedInterval.symmetric(Angle.TAU / 2);


    /**
     * Package private equatorial coordinates constructor
     *
     * @param ra  , right ascension in rad
     * @param dec , declination in rad
     */
    private EquatorialCoordinates(double ra, double dec) {
        super(ra, dec);
    }

    /**
     * Constructor of Equatorial coordinates
     *
     * @param ra  right ascension in rad
     * @param dec declination in rad
     * @return the new coordinates
     * @throws IllegalArgumentException if ra or dec not in the right interval
     */
    public static EquatorialCoordinates of(double ra, double dec) {

        Preconditions.checkInInterval(RA_INTERVAL, ra);
        Preconditions.checkInInterval(DEC_INTERVAL, dec);

        return new EquatorialCoordinates(ra, dec);

    }

    /**
     * @return the right ascension in radian
     */
    public double ra() {
        return super.lon();
    }

    /**
     * @return the right ascension in deg
     */
    public double raDeg() {
        return super.lonDeg();
    }

    /**
     * @return the right ascension in hours
     */
    public double raHr() {
        return Angle.toHr(lon());
    }

    /**
     * @return the declination in radian
     */
    public double dec() {

        return super.lat();
    }

    /**
     * @return the declination in deg
     */

    public double decDeg() {
        return super.latDeg();
    }


    @Override
    public String toString() {
        return String.format(Locale.ROOT, "(ra=%.4fh, dec=%.4f°)", raHr(), decDeg());
    }
}
