package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AsterismTest {

    @Test
    void throwsIAEIfStarListIsNull(){
        assertThrows(IllegalArgumentException.class, () -> {
            Asterism a = new Asterism(new ArrayList<>());
        });
    }

    @Test
    void stars() {
        ArrayList<Star> list = new ArrayList<>();

        list.add(new Star(4, "test", EquatorialCoordinates.of(1, 1), 1f, 2.45f));
        list.add(new Star(8, "test2", EquatorialCoordinates.of(1, 1), 1f, 2.45f));

        Asterism a = new Asterism(list);

        assertEquals(list, a.stars());
    }

    @Test
    void starsImmutable() {
        ArrayList<Star> list = new ArrayList<>();

        list.add(new Star(4, "test", EquatorialCoordinates.of(1, 1), 1f, 2.45f));
        list.add(new Star(8, "test2", EquatorialCoordinates.of(1, 1), 1f, 2.45f));

        Asterism a = new Asterism(list);

        list.add(new Star(9, "test3", EquatorialCoordinates.of(1, 1), 1f, 2.45f));

        assertEquals(2, a.stars().size());
    }
}