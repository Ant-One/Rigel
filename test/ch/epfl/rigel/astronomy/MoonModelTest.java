package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class MoonModelTest {

    @Test
    void at() {
        EclipticToEquatorialConversion ETEC = new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2003, Month.SEPTEMBER, 1),
                LocalTime.MIDNIGHT, ZoneOffset.UTC));


        EquatorialCoordinates bookValues = ETEC.apply(EclipticCoordinates.of(Angle.ofDeg(214.862515), Angle.ofDeg(1.716257)));

        Moon moon = MoonModel.MOON.at(Epoch.J2010.daysUntil(ZonedDateTime.of(LocalDate.of(2003, Month.SEPTEMBER, 1),
                LocalTime.MIDNIGHT, ZoneOffset.UTC)), ETEC);

        assertEquals(bookValues.ra(), moon.equatorialPos().ra(),1e-6);
        assertEquals(bookValues.dec(), moon.equatorialPos().dec(),1e-6);

        assertEquals(Angle.ofDeg(0.546822), moon.angularSize(), 1e-7);

    }
}