package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import static org.junit.jupiter.api.Assertions.*;

class StarTest {

    @Test
    void testConstructorStar(){
        Star s = new Star(4, "test", EquatorialCoordinates.of(1, 1), 1f, 2.45f);

        assertEquals(2763, s.colorTemperature());
        assertEquals(4, s.hipparcosId());
        assertEquals(0, s.angularSize()); //Must be zero
    }

    @Test
    void throwsIAEifHipparcosNegative(){
        assertThrows(IllegalArgumentException.class, () -> {
            Star s = new Star(-2, "test", EquatorialCoordinates.of(1, 1), 1f, 1);
        });
    }

    @Test
    void throwsIAEifColorIndexIncorrect(){
        assertThrows(IllegalArgumentException.class, () -> {
            Star s = new Star(2, "test", EquatorialCoordinates.of(1, 1), 1f, -1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Star s = new Star(2, "test", EquatorialCoordinates.of(1, 1), 1f, -0.51f);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Star s = new Star(2, "test", EquatorialCoordinates.of(1, 1), 1f, 5.51f);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Star s = new Star(2, "test", EquatorialCoordinates.of(1, 1), 1f, 1000f);
        });
    }
}