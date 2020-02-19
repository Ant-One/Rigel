package ch.epfl.rigel.coordinates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HorizontalCoordinatesTest {

    @Test
    void of() {
    }

    @Test
    void ofDeg() {
    }

    @Test
    void az() {
    }

    @Test
    void azDeg() {
    }

    @Test
    void azOctantName() {
        assertEquals("N", HorizontalCoordinates.ofDeg(0, 0)
                .azOctantName("N", "E", "S", "O"));
    }

    @Test
    void alt() {
    }

    @Test
    void altDeg() {
    }

    @Test
    void angularDistanceTo() {
    }

    @Test
    void testToString() {
    }
}