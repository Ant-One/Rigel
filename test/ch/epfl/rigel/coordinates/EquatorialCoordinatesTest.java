package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EquatorialCoordinatesTest {

    @Test
    void ofThrowExecption() {
        assertThrows(IllegalArgumentException.class,() -> {
            EquatorialCoordinates.of(-1,1);
        });
    }

    @Test
    void ra() {
        EquatorialCoordinates c =EquatorialCoordinates.of(3,1);
        assertEquals(3,c.ra());
    }

    @Test
    void raDeg() {
        EquatorialCoordinates c =EquatorialCoordinates.of(3,1);
        assertEquals(Angle.toDeg(3),c.raDeg());

    }

    @Test
    void raHr() {
        EquatorialCoordinates c =EquatorialCoordinates.of(3,1);
        assertEquals(Angle.toHr(3),c.raHr());

    }

    @Test
    void dec() {
        EquatorialCoordinates c =EquatorialCoordinates.of(3,1);
        assertEquals(1,c.dec());
    }

    @Test
    void decDeg() {
        EquatorialCoordinates c =EquatorialCoordinates.of(3,1);
        assertEquals(Angle.toDeg(1),c.decDeg());
    }

    @Test
    void testToString() {
        EquatorialCoordinates c =EquatorialCoordinates.of(Angle.ofHr(1.5),Angle.TAU/8);
        assertEquals("(ra=1.5000h, dec=45.0000°)",c.toString());
    }
}