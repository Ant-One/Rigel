package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.Polynomial;
import ch.epfl.rigel.math.RightOpenInterval;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * This class is used to compute sidereal time
 *
 * @author Adrien Rey (313388)
 */
public final class SiderealTime {

    private final static RightOpenInterval radInterval = RightOpenInterval.of(0, Angle.TAU);
    private final static Polynomial S0Polynomial=Polynomial.of( 0.000025862,2400.051336,6.697374558);
    private final static double C=1.002737909;

    private SiderealTime() {
        throw new UnsupportedOperationException("Non instantiable class");
    }

    /**
     * Computes the sidereal time at greenwich
     *
     * @param when ZonedDateTime of your current place
     * @return greenwich sidereal time
     */
    public static double greenwich(ZonedDateTime when) {

        ZonedDateTime greenwichWhen = when.withZoneSameInstant(ZoneId.of("GMT+0"));

        ZonedDateTime whenTruncated=greenwichWhen.truncatedTo(ChronoUnit.DAYS);

        double T = Epoch.J2000.julianCenturiesUntil(whenTruncated);
        double t = (double) whenTruncated.until(greenwichWhen, ChronoUnit.MILLIS) / (double) ChronoUnit.HOURS.getDuration().toMillis();


        double S0 =S0Polynomial.at(T);
        double S1 = C * t;
        double Sg = S0 + S1;
        return radInterval.reduce(Angle.ofHr(Sg));

    }

    /**
     * Computes the local sidereal time
     *
     * @param when  ZonedDateTime of the current place
     * @param where Geographic coordinates of the location
     * @return local sidereal time
     */
    public static double local(ZonedDateTime when, GeographicCoordinates where) {

        return radInterval.reduce(greenwich(when) + where.lon());
    }

}
