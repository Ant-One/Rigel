package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class SunModelTest {

    @Test
    void at() {
        Sun sun = new Sun(EclipticCoordinates.of(1, 0.5), EquatorialCoordinates.of(1, 0.7), 1f, 2f);

        SunModel sm = SunModel.SUN;
        EclipticToEquatorialConversion ETEC = new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2003, Month.JULY, 27),
                LocalTime.MIDNIGHT, ZoneOffset.UTC));


        EclipticCoordinates bookValues = EclipticCoordinates.of(Angle.ofDeg(123.580601), 0);

        Sun newSun = sm.at(Epoch.J2010.daysUntil(ZonedDateTime.of(LocalDate.of(2003, Month.JULY, 27),
                LocalTime.MIDNIGHT, ZoneOffset.UTC)), ETEC);

       assertEquals(ETEC.apply(bookValues).dec(), newSun.equatorialPos().dec(), 1e-8);
        assertEquals(ETEC.apply(bookValues).ra(), newSun.equatorialPos().ra(), 1e-8);

        assertEquals(bookValues.lat(), newSun.eclipticPos().lat(), 1e-8);
        assertEquals(bookValues.lon(), newSun.eclipticPos().lon(), 1e-8);
    }

    @Test
    void AngularSizeTest(){
        Sun sun = new Sun(EclipticCoordinates.of(1, 0.5), EquatorialCoordinates.of(1, 0.7), 1f, 2f);

        SunModel sm = SunModel.SUN;

        ZonedDateTime zdt = ZonedDateTime.of(LocalDate.of(1988, Month.JULY, 27),
                LocalTime.MIDNIGHT, ZoneOffset.UTC);

        EclipticToEquatorialConversion ETEC = new EclipticToEquatorialConversion(zdt);


        Sun newSun = sm.at(Epoch.J2010.daysUntil(zdt), ETEC);

        assertEquals(Angle.ofDMS(0, 31, 30), newSun.angularSize(), 1e-6);
    }

}