package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HorizontalCoordinatesTest {

    @Test
    void ofAzNeg() {
        assertThrows(IllegalArgumentException.class, () -> {
            HorizontalCoordinates hc = HorizontalCoordinates.of(-1.2, 0.21);
        });
    }
    @Test
    void ofAltOutOfBounds() {
        assertThrows(IllegalArgumentException.class, () -> {
            HorizontalCoordinates hc = HorizontalCoordinates.of(1.2, 1000);
        });
    }

    @Test
    void ofDegAzNeg() {
        assertThrows(IllegalArgumentException.class, () -> {
            HorizontalCoordinates hc = HorizontalCoordinates.ofDeg(-1.2, 0.21);
        });
    }

    @Test
    void ofDegAltOutOfBounds() {
        assertThrows(IllegalArgumentException.class, () -> {
            HorizontalCoordinates hc = HorizontalCoordinates.ofDeg(1.2, 91);
        });
    }

    @Test
    void az() {
        HorizontalCoordinates hc = HorizontalCoordinates.of(1.2, 0.21);
        assertEquals(1.2, hc.az());
    }

    @Test
    void azDeg() {
        HorizontalCoordinates hc = HorizontalCoordinates.ofDeg(1.2, 0.21);
        assertEquals(1.2, hc.azDeg());
    }

    @Test
    void azOctantNameWithNullAngle() {
        assertEquals("N", HorizontalCoordinates.ofDeg(0, 0)
                .azOctantName("N", "E", "S", "O"));
    }

    @Test
    void azOctantNameWithARandomAngle() {
        assertEquals("SO", HorizontalCoordinates.ofDeg(227.432, 0)
                .azOctantName("N", "E", "S", "O"));
    }

    @Test
    void alt() {
        HorizontalCoordinates hc = HorizontalCoordinates.of(1.2, 0.21);
        assertEquals(0.21, hc.alt());
    }

    @Test
    void altDeg() {
        HorizontalCoordinates hc = HorizontalCoordinates.of(1.5, Angle.TAU/4);
        assertEquals(90, hc.altDeg());
    }

    @Test
    void angularDistanceTo() {
        HorizontalCoordinates hc = HorizontalCoordinates.ofDeg(6.5682, 46.5183);
        assertEquals(0.0279, hc.angularDistanceTo(HorizontalCoordinates.ofDeg(8.5479, 47.3763)), 1e-4);
    }

    @Test
    void testToString() {
        HorizontalCoordinates hc = HorizontalCoordinates.ofDeg(350, 7.2);
        assertEquals("(az=350.0000°, alt=7.2000°)", hc.toString());
    }
}