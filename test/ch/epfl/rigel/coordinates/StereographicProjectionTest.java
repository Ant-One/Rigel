package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.astronomy.Sun;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class StereographicProjectionTest {

    @Test
    void circleCenterForParallel() {
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0.2, 0.12));
        HorizontalCoordinates hor = HorizontalCoordinates.of(0.3, 0.98);

        assertEquals(0, sp.circleCenterForParallel(hor).x());
        assertEquals(1.04483122363, sp.circleCenterForParallel(hor).y(), 1e-8);

        sp = new StereographicProjection(HorizontalCoordinates.of(0.3, 0));
        hor = HorizontalCoordinates.of(0.2, 0);

        assertEquals(0, sp.circleCenterForParallel(hor).x());
        assertEquals(4.f/0.f, sp.circleCenterForParallel(hor).y(), 1e-8);
    }

    @Test
    void circleRadiusForParallel() {
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0.2, 0.12));
        HorizontalCoordinates hor = HorizontalCoordinates.of(0.3, 0.98);

        assertEquals(0.58621020014, sp.circleRadiusForParallel(hor), 1e-8);

        sp = new StereographicProjection(HorizontalCoordinates.of(0.3, 0));
        hor = HorizontalCoordinates.of(0.2, 0);

        assertEquals(4.f/0.f, sp.circleRadiusForParallel(hor), 1e-8);

    }

    @Test
    void applyToAngle() {
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0.2, 0.12));
        Sun sun = new Sun(EclipticCoordinates.of(1,1), EquatorialCoordinates.of(1,1), 2.33f,1.f);

       assertEquals(1.3174948651, sp.applyToAngle(sun.angularSize()), 1e-7);

    }

    @Test
    void apply() {
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0.2, 0.12));

        CartesianCoordinates cc = sp.apply(HorizontalCoordinates.of(1.3, 1.56));

        CartesianCoordinates ccTest = CartesianCoordinates.of(0.008555807, 0.882263534);

        assertEquals(ccTest.x(), cc.x(), 1e-6);
        assertEquals(ccTest.y(), cc.y(), 1e-6);

    }

    @Test
    void inverseApply() {
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0.2, 0.12));

        HorizontalCoordinates hc = HorizontalCoordinates.of(0.2, 0.7);

        assertEquals(hc.az(), sp.inverseApply(sp.apply(hc)).az());
        assertEquals(hc.alt(), sp.inverseApply(sp.apply(hc)).alt());

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

    @Test
    void inverseWorkOnAplly(){

       Random r=new Random();
        for (int j = 0; j <1000 ; j++) {

            StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.ofDeg(r.nextDouble() * 360, r.nextDouble() * 180 - 90));
            for (int i = 0; i < 1000; i++) {
                HorizontalCoordinates cord = HorizontalCoordinates.ofDeg(r.nextDouble() * 360, r.nextDouble() * 180 - 90);
                HorizontalCoordinates coord = sp.inverseApply(sp.apply(cord));
                assertEquals(cord.alt(), coord.alt(), 1e-6);
                assertEquals(cord.az(), coord.az(), 1e-6);
            }
        }


    }
}