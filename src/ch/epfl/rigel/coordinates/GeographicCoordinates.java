package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.Interval;
import ch.epfl.rigel.math.RightOpenInterval;

import java.util.Locale;

/**
 * Geographic coordinates
 * @author Adrien Rey (313388)
 */
public final class GeographicCoordinates extends SphericalCoordinates{

    private static final RightOpenInterval longitudeInterval= RightOpenInterval.symmetric(180);
    private static final ClosedInterval latitudeInterval= ClosedInterval.symmetric(90);


    /**
     * Package private spherical coordinates constructor
     *
     * @param longitude , longitude in rad
     * @param lattitude , latitude in rad
     */
    private GeographicCoordinates(double longitude, double lattitude) {
        super(longitude, lattitude);
    }

    /**
     *  Constructor of Spheric coordinates
     * @param longDeg longitude in degree
     * @param latDeg latitude in degree
     * @return the new coordinates
     */
    GeographicCoordinates ofDeg(double longDeg,double latDeg){

        Preconditions.checkArgument(   isValidLonDeg(longDeg)  && isValidLatDeg(latDeg) );

        return new GeographicCoordinates(Angle.ofDeg(longDeg),Angle.ofDeg(latDeg));

    }

    /**
     * Check if it is a valid longitude
     * @param lonDeg longitude in deg
     */
    public static boolean isValidLonDeg(double lonDeg){
        return longitudeInterval.contains(lonDeg);
    }
    /**
     * Check if it is a valid latitude
     * @param latDeg latitude in deg
     */
    public static boolean isValidLatDeg(double latDeg){
        return latitudeInterval.contains(latDeg);
    }

    /**
     * @return the latitude in radian
     */
    public double lat(){

        return super.lat();
    }

    /**
     * @return the latitude in deg
     */

   public double latDeg(){
        return super.latDeg();
    }

    /**
     * @return the longitude in radian
     */
    public double lon(){
        return super.lon();
    }

    /**
     * @return the longitude in deg
     */
    public double lonDeg(){
        return super.lonDeg();
    }


    @Override
    public String toString() {
        return String.format(Locale.ROOT,"(lon=%.4f°, lat=%.4f°)",lon(),lat());
    }
}