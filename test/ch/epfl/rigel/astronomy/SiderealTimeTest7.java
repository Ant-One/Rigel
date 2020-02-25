package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SiderealTimeTest7 {

    @Test
    void greenwich() {

        ZoneId id=ZoneId.of("UTC+0");
        LocalDate date=LocalDate.of(1980,4,22);
        LocalTime time=LocalTime.of(14,36,51,670000000);
        ZonedDateTime ZDT=ZonedDateTime.of(date,time,id);

        assertEquals(Angle.ofHr(4.668120),SiderealTime.greenwich(ZDT),1e-6);

    }

    @Test
    void local() {
    }
}