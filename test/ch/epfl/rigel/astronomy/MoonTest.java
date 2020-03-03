package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoonTest {


    @Test
    void constructorThrowExceptionName(){
        assertThrows(IllegalArgumentException.class,() -> {
            new Moon(EquatorialCoordinates.of(1,1),8.f,0.2f,1.0001f) ;
        });
    }

    @Test
    void info() {
        assertEquals("Lune (37.5%)",new Moon(EquatorialCoordinates.of(1,1),8.f,0.2f,0.375f).info()) ;


    }
}