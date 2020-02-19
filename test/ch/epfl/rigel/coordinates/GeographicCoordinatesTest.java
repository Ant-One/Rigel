package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeographicCoordinatesTest {

    @Test
    void ofDegThrowExeption() {
        assertThrows(IllegalArgumentException.class,() -> {
            GeographicCoordinates.ofDeg(180,90);
        });
    }

    @Test
    void isValidLonDeg() {
        assertTrue(GeographicCoordinates.isValidLatDeg(-10));
    }

    @Test
    void isValidLatDeg() {
        assertTrue(GeographicCoordinates.isValidLatDeg(-90));
    }

    @Test
    void lat() {
        GeographicCoordinates c =GeographicCoordinates.ofDeg(25,30);
        assertEquals(c.lat(), Angle.ofDeg(30));
    }

    @Test
    void latDeg() {
        GeographicCoordinates c =GeographicCoordinates.ofDeg(25,30);
        assertEquals(c.latDeg(), 30, 1e-6);

    }



    @Test
    void testToString() {
        GeographicCoordinates c =GeographicCoordinates.ofDeg(6.57,46.52);
        assertEquals("(lon=6.5700°, lat=46.5200°)",c.toString());
    }
}