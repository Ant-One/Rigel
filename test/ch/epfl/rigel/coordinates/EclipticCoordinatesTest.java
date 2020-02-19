package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import javax.security.auth.login.AccountNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class EclipticCoordinatesTest {

    @Test
    void of() {
        assertThrows(IllegalArgumentException.class,() -> {
            EclipticCoordinates.of(2,-3);
        });

    }

    @Test
    void lon() {
        EclipticCoordinates c =EclipticCoordinates.of(3,1);
        assertEquals(3,c.lon());
    }

    @Test
    void lonDeg() {
        EclipticCoordinates c =EclipticCoordinates.of(Angle.TAU/4,1);
        assertEquals(90,c.lonDeg());
    }

    @Test
    void lat() {
        EclipticCoordinates c =EclipticCoordinates.of(3,1);
        assertEquals(1,c.lat());
    }

    @Test
    void latDeg() {
        EclipticCoordinates c =EclipticCoordinates.of(3,Angle.TAU/8);
        assertEquals(45,c.latDeg());
    }

    @Test
    void testToString() {

        EclipticCoordinates c =EclipticCoordinates.of(Angle.ofDeg(22.5),Angle.ofDeg(18));
        assertEquals("(λ=22.5000°, β=18.0000°)",c.toString());
    }
}