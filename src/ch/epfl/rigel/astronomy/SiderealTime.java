package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.math.Angle;
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

    private final static RightOpenInterval radInterval=RightOpenInterval.of(0,Angle.TAU/2);
    private  final static RightOpenInterval hrInterval =RightOpenInterval.of(0,24);

    /**
     * compute the sidereal time at greenwich
     *
     * @param when ZonedDateTime of your current place
     * @return greenwich sidereal time
     */
    public static double greenwich(ZonedDateTime when) {

        ZonedDateTime greenwichWhen = when.withZoneSameInstant(ZoneId.of("GMT+0"));

        double T = Epoch.J2000.julianCenturiesUntil(when.truncatedTo(ChronoUnit.DAYS));
        double t = (double) greenwichWhen.truncatedTo(ChronoUnit.DAYS).until(greenwichWhen, ChronoUnit.MILLIS) / (double) ChronoUnit.HOURS.getDuration().toMillis();


        double S0 = 0.000025862 * T * T + 2400.051336 * T + 6.697374558;
        double S1 =1.002737909 * t;
        double Sg =S0 + S1;
        return radInterval.reduce(Angle.ofHr(Sg));

    }

    /**
     * compute the local sidereal time
     *
     * @param when  ZonedDateTime of the current place
     * @param where Geographic coordinates of the location
     * @return local sidereal time
     */
    public static double local(ZonedDateTime when, GeographicCoordinates where) {

        return radInterval.reduce(greenwich(when) + where.lon());
    }

}
