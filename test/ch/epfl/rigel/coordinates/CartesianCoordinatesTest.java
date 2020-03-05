package ch.epfl.rigel.coordinates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartesianCoordinatesTest {

    @Test
    void x() {
        CartesianCoordinates cc = CartesianCoordinates.of(1, 2);
        assertEquals(1, cc.x());
    }

    @Test
    void y() {
        CartesianCoordinates cc = CartesianCoordinates.of(1, 2);
        assertEquals(2, cc.y());
    }

    @Test
    void exceptionOnHashCode() {
        CartesianCoordinates cc = CartesianCoordinates.of(1, 2);

        assertThrows(UnsupportedOperationException.class, () -> {
            cc.hashCode();
        });
    }

    @Test
    void testToString() {
        CartesianCoordinates cc = CartesianCoordinates.of(1, 2);

        assertEquals("(1.0 ; 2.0)", cc.toString());
    }

    @Test
    void exceptionOnEquals() {
        CartesianCoordinates cc = CartesianCoordinates.of(1, 2);

        assertThrows(UnsupportedOperationException.class, () -> {
            cc.equals(cc);
        });
    }
}