package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.Interval;
import ch.epfl.rigel.math.RightOpenInterval;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public final class SiderealTime {

    private final static RightOpenInterval interval=RightOpenInterval.of(0,Math.PI);

    public static double greenwich(ZonedDateTime when)
    {

        ZonedDateTime greenwichWhen= when.withZoneSameInstant(ZoneId.of("GMT+0"));
        double T=0;//TODO
        double t= greenwichWhen.withHour(0).until(greenwichWhen, ChronoUnit.HOURS);

        double S0 =0.000025862*T*T+2400.051336*T+6.697374558;
        double S1 =1.002737909*t;
        double Sg=S0+S1;
        return interval.reduce(Angle.ofHr(Sg));

    }

    public static double local(ZonedDateTime when, GeographicCoordinates where){

        return interval.reduce(greenwich(when)+where.lon());
    }

}
