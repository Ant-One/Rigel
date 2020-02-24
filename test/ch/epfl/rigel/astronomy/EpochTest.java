package ch.epfl.rigel.astronomy;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.zone.ZoneOffsetTransition;

import static org.junit.jupiter.api.Assertions.*;

class EpochTest {

    @Test
    void daysUntil() {
        assertEquals(366, Epoch.J2000.daysUntil(
                ZonedDateTime.of(LocalDate.of(2001, Month.JANUARY, 1),
                        LocalTime.NOON,
                        ZoneOffset.UTC)));
    }

    @Test
    void julianCenturiesUntil() {
        assertEquals(10, Epoch.J2010.julianCenturiesUntil(
                ZonedDateTime.of(LocalDate.of(3010, Month.JANUARY, 1),
                        LocalTime.MIDNIGHT,
                        ZoneOffset.UTC)), 1e-4);
    }
}