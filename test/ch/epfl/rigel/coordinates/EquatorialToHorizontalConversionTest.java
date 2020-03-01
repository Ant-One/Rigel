package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class EquatorialToHorizontalConversionTest {

    @Test
    void applyWorksForRad() {
        HorizontalCoordinates hc = HorizontalCoordinates.of(Angle.ofDMS(283, 16, 15.70), Angle.ofDMS(19, 20, 3.64));

        EquatorialCoordinates ec = EquatorialCoordinates.of(1, Angle.ofDMS(23, 13, 10));

        EquatorialToHorizontalConversion ETHC = new EquatorialToHorizontalConversion(ZonedDateTime.of(LocalDate.of(2000, Month.JANUARY, 1),
                LocalTime.NOON, ZoneOffset.UTC),
                GeographicCoordinates.ofDeg(52, 52));

        assertEquals(hc.alt(), ETHC.apply(ec).alt(), 1e-6);
        assertEquals(hc.az(), ETHC.apply(ec).az(), 1e-6);
    }

    @Test
    void findHourAngle() {
        assertEquals(9.873237,
                EquatorialToHorizontalConversion.findHourAngle(4.412404, 18 + 32 /60.0 + 21/3600.0), 1e-6);
    }
}