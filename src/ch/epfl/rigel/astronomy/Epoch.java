package ch.epfl.rigel.astronomy;

import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 * Enum to model J2000 and J2010 Epoch
 *
 * @author Antoine Moix (310052)
 */
public enum Epoch {
    J2000(ZonedDateTime.of(LocalDate.of(2000, Month.JANUARY, 1),
            LocalTime.NOON,
            ZoneOffset.UTC)),
    J2010(ZonedDateTime.of(LocalDate.of(2010, Month.JANUARY, 1).minusDays(1),
            LocalTime.MIDNIGHT,
            ZoneOffset.UTC));

    private static final double DAYS_PER_MILLIS = (double) 1 / (ChronoUnit.DAYS.getDuration().toMillis());
    private static final double CENTURIES_PER_MILLIS = (double) 1 / ChronoUnit.CENTURIES.getDuration().toMillis();
    private final ZonedDateTime beginning;

    Epoch(ZonedDateTime beginning) {
        this.beginning = beginning;
    }

    /**
     * Returns the number of days (not necessarily an integer) between the beginning date of the standard (J2000 or J2010)
     * and the when parameter
     *
     * @param when date to which compute the numbers of days remaining
     * @return a non-integer number of days remaining
     */
    public double daysUntil(ZonedDateTime when) {
        return beginning.until(when, ChronoUnit.MILLIS) * DAYS_PER_MILLIS;
    }

    /**
     * Returns the number of julian centuries (not necessarily an integer) between the beginning date of the standard (J2000 or J2010)
     * and the when parameter
     *
     * @param when date to which compute the numbers of julian centuries remaining
     * @return a non-integer number of julian centuries remaining
     */
    public double julianCenturiesUntil(ZonedDateTime when) {
        return beginning.until(when, ChronoUnit.MILLIS) * CENTURIES_PER_MILLIS;
    }
}