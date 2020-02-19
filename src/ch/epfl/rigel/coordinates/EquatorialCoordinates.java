package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import java.util.Locale;

/**
 * Equatorial coordinates
 * @author Adrien Rey (313388)
 */
public final class EquatorialCoordinates extends SphericalCoordinates{

    private static final RightOpenInterval raInterval= RightOpenInterval.of(0,360);
    private static final ClosedInterval decInterval= ClosedInterval.symmetric(90);


    /**
     * Package private equatorial coordinates constructor
     *
     * @param ra , right ascension in rad
     * @param dec , declination in rad
     */
    private EquatorialCoordinates(double ra, double dec) {
        super(ra, dec);
    }

    /**
     *  Constructor of Equatorial coordinates
     * @param ra right ascension in degree
     * @param dec declination in degree
     * @return the new coordinates
     */
    public static EquatorialCoordinates of(double ra, double dec){

        Preconditions.checkInInterval(raInterval,ra);
        Preconditions.checkInInterval(decInterval,dec);

        return new EquatorialCoordinates(ra,dec);

    }

    /**
     * @return the right ascension in radian
     */
    public double ra(){
        return super.lon();
    }

    /**
     * @return the right ascension in deg
     */
    public double raDeg(){
        return super.lonDeg();
    }

    /**
     * @return the right ascension in hours
     */
    public double raHr(){
        return Angle.toHr(lon());
    }
    /**
     * @return the declination in radian
     */
    public double dec(){

        return super.lat();
    }

    /**
     * @return the declination in deg
     */

   public double decDeg(){
        return super.latDeg();
    }




    @Override
    public String toString() {
        return String.format(Locale.ROOT,"(ra=%.4f°h, dec=%.4f°)",raHr(),dec());
    }
}
