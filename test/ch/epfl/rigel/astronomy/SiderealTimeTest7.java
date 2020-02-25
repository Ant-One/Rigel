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

        assertEquals(Angle.ofDMS(60,40,5.23),SiderealTime.greenwich(ZDT));




    }

    @Test
    void local() {
    }
}