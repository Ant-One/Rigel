package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.astronomy.SiderealTime;
import ch.epfl.rigel.math.Angle;

import java.time.ZonedDateTime;
import java.util.function.Function;



/**
 * Model a conversion from Equatorial to Horizontal coordinates
 *
 * @author Antoine Moix (310052)
 */
public class EquatorialToHorizontalConversion implements Function<EquatorialCoordinates, HorizontalCoordinates> {

    private double localSiderealTime;
    private double sinLat;
    private double cosLat;

    /**
     * Constructs a conversion from Equatorial to Horizontal coordinates
     *
     * @param when ZonedDateTime of the current location
     */
    public EquatorialToHorizontalConversion(ZonedDateTime when, GeographicCoordinates where) {
        localSiderealTime = Angle.toHr(SiderealTime.local(when, where));

        sinLat = Math.sin(where.lat());
        cosLat = Math.cos(where.lat());
    }

    /**
     * Function to find the hour angle in hours
     * @param LST Local Sidereal Time of the observer
     * @param raHr the right ascension angle in hours
     * @return the hour angle in hours
     */
     private double findHourAngle(double LST, double raHr){
        double hourAngle = (LST - raHr);

        if (hourAngle < 0){
            hourAngle += 24.0;
        }
        return hourAngle;
    }


    @Override
    /**
     * Converts the given EquatorialCoordinates to HorizontalCoordinates
     * @param equatorialCoordinates EquatorialCoordinates to convert
     * @return the converted horizontal coordinates
     */
    public HorizontalCoordinates apply(EquatorialCoordinates equatorialCoordinates) {

        double hourAngle = Angle.ofHr(findHourAngle(localSiderealTime, equatorialCoordinates.raHr()));

        double sinDec = Math.sin(equatorialCoordinates.dec());
        double cosDec = Math.cos(equatorialCoordinates.dec());

        double altitude = Math.asin((sinDec * sinLat) + (cosDec * cosLat * Math.cos(hourAngle)));

        double azimuth = Math.atan2(-cosDec * cosLat * Math.sin(hourAngle), sinDec - sinLat * Math.sin(altitude));

        return HorizontalCoordinates.of(Angle.normalizePositive(azimuth), altitude);
    }

    /**
     * @throws UnsupportedOperationException when used
     */
    @Override
    final public int hashCode() {
        throw new UnsupportedOperationException();
    }

    /**
     * @throws UnsupportedOperationException when used
     */
    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }
}
