package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SunTest {

    @Test
    void name() {
        assertEquals("Soleil",new Sun(EclipticCoordinates.of(1,1), EquatorialCoordinates.of(1,1),1.f,1.f).name());
    }
    @Test
    void constructorThrowExceptionName(){
        assertThrows(NullPointerException.class,() -> {
            new Sun(null,EquatorialCoordinates.of(1,1),8.f,0.2f) ;
        });
    }


}