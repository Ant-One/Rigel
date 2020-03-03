package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlanetTest {
    @Test
    void constructorThrowExceptionName(){
        assertThrows(NullPointerException.class,() -> {
            new Planet(null,EquatorialCoordinates.of(1,1),8.f,0.2f);
        });
    }
    @Test
    void constructorThrowExceptionCoord(){
        assertThrows(NullPointerException.class,() -> {
            new Planet("fs",null,8.f,0.2f);
        });
    }
    @Test
    void constructorThrowExceptionAngular(){
        assertThrows(IllegalArgumentException.class,() -> {
            new Planet("sds",EquatorialCoordinates.of(1,1),-8.f,0.2f);
        });
    }

}