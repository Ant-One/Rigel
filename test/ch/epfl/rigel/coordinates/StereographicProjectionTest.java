package ch.epfl.rigel.coordinates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StereographicProjectionTest {

    @Test
    void circleCenterForParallel() {
    }

    @Test
    void circleRadiusForParallel() {
    }

    @Test
    void applyToAngle() {
    }

    @Test
    void apply() {
    }

    @Test
    void inverseApply() {
    }

    @Test
    void exceptionOnHashCode() {
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0,0));

        assertThrows(UnsupportedOperationException.class, () -> {
            sp.hashCode();
        });
    }

    @Test
    void exceptionOnEquals() {
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0,0));

        assertThrows(UnsupportedOperationException.class, () -> {
            sp.equals(sp);
        });
    }

    @Test
    void testToString(){
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0.6,1.3));

        assertEquals("StereographicProjection{center of (az=34.3775°, alt=74.4845°)}", sp.toString());
    }
}