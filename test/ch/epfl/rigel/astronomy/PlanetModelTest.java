package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PlanetModelTest {


    @Test
    void testFormercury(){


        ZoneId id=ZoneId.of("UTC+0");
        LocalDate date=LocalDate.of(2003,11,22);
        LocalTime time=LocalTime.of(0,0,0,0);
        ZonedDateTime ZDT=ZonedDateTime.of(date,time,id);
        EclipticToEquatorialConversion ETE=new EclipticToEquatorialConversion(ZDT);

        Planet mercury=PlanetModel.MERCURY .at(-2231,ETE);
        EquatorialCoordinates  EC=ETE.apply(EclipticCoordinates.of(Angle.ofDeg(253.929758),Angle.ofDeg(-2.044057)));

        assertEquals(EC.dec(),mercury .equatorialPos().dec(),1e-8);
        assertEquals(EC.ra(),mercury .equatorialPos().ra(),1e-8);

    }
    @Test
    void testForJupiter(){


        ZoneId id=ZoneId.of("UTC+0");
        LocalDate date=LocalDate.of(2003,11,22);
        LocalTime time=LocalTime.of(0,0,0,0);
        ZonedDateTime ZDT=ZonedDateTime.of(date,time,id);
        EclipticToEquatorialConversion ETE=new EclipticToEquatorialConversion(ZDT);

        Planet jupiter=PlanetModel.JUPITER.at(-2231,ETE);
        EquatorialCoordinates  EC=ETE.apply(EclipticCoordinates.of(Angle.ofDeg(166.310510),Angle.ofDeg(1.036466)));

        assertEquals(EC.dec(),jupiter.equatorialPos().dec(),1e-8);
        assertEquals(EC.ra(),jupiter.equatorialPos().ra(),1e-8);
        assertEquals(Angle.ofArcsec(35.1),jupiter.angularSize(),1e-6);
        assertEquals(-2,jupiter.magnitude());

    }
}