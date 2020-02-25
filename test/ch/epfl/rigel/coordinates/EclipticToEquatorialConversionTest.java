package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.RightOpenInterval;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EclipticToEquatorialConversionTest {

    @Test
    void applyWorkForRa() {
        ZoneId id=ZoneId.of("UTC+0");
        LocalDate date=LocalDate.of(2009,7,6);
        LocalTime time=LocalTime.of(14,36,51,670000000);
        ZonedDateTime ZDT=ZonedDateTime.of(date,time,id);

        EclipticToEquatorialConversion ETEC=new EclipticToEquatorialConversion(ZDT);
        EclipticCoordinates ec=EclipticCoordinates.of(Angle.ofDeg(139.686111),Angle.ofDeg(4.875278));

        EquatorialCoordinates eq=EquatorialCoordinates.of(Angle.ofHr(9+34.0/60.0+53.32/3600),Angle.ofDMS(19,32,6.01));
        assertEquals(eq.ra(),ETEC.apply(ec).ra(),1e-6);
    }
    @Test
    void applyWorkForDec() {
        ZoneId id=ZoneId.of("UTC+0");
        LocalDate date=LocalDate.of(2009,7,6);
        LocalTime time=LocalTime.of(14,36,51,670000000);
        ZonedDateTime ZDT=ZonedDateTime.of(date,time,id);

        EclipticToEquatorialConversion ETEC=new EclipticToEquatorialConversion(ZDT);
        EclipticCoordinates ec=EclipticCoordinates.of(Angle.ofDeg(139.686111),Angle.ofDeg(4.875278));
        EquatorialCoordinates eq=EquatorialCoordinates.of(Angle.ofHr(9+34.0/60+53.32/3600),Angle.ofDeg(19.5350030));
        assertEquals(eq.dec(),ETEC.apply(ec).dec(),1e-8);
    }

}