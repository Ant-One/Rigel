package ch.epfl.rigel.astronomy;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * Enum to model J2000 and J2010 Epoch
 * @author Antoine Moix (310052)
 */
public enum Epoch {
    J2000(ZonedDateTime.of(LocalDate.of(2000, Month.JANUARY, 1),
            LocalTime.NOON,
            ZoneOffset.UTC)),
    J2010(ZonedDateTime.of(LocalDate.of(2010, Month.JANUARY, 1).minusDays(1),
            LocalTime.MIDNIGHT,
            ZoneOffset.UTC));

    private ZonedDateTime beginning;
    private static int DAY_PER_MILLIS = 1/(1000 * 60 * 60 * 24);

    Epoch(ZonedDateTime beginning){
        this.beginning = beginning;
    }

    public double daysUntil(ZonedDateTime when) {
        double tmp = beginning.until(when, ChronoUnit.MILLIS);
        return tmp * DAY_PER_MILLIS;
    }

    public double julianCenturiesUntil(ZonedDateTime when){
        return 0;
    }
}