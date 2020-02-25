package ch.epfl.rigel.astronomy;

import java.sql.Time;
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
    private static double DAYS_PER_MILLIS = (double) 1/(1000 * 60 * 60 * 24);
    private static double CENTURIES_PER_MILLIS = (double) 1/ChronoUnit.CENTURIES.getDuration().toMillis();

    Epoch(ZonedDateTime beginning){
        this.beginning = beginning;
    }

    public double daysUntil(ZonedDateTime when) {
        return beginning.until(when, ChronoUnit.MILLIS) * DAYS_PER_MILLIS;
    }

    public double julianCenturiesUntil(ZonedDateTime when){
        return beginning.until(when, ChronoUnit.MILLIS) * CENTURIES_PER_MILLIS;
    }
}