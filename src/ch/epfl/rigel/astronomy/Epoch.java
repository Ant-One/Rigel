package ch.epfl.rigel.astronomy;

import java.time.*;

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

    Epoch(ZonedDateTime beginning){
        this.beginning = beginning;
    }

    public double daysUntil(ZonedDateTime when) {
        return 0;
    }

    public double julianCenturiesUntil(ZonedDateTime when){
        return 0;
    }
}