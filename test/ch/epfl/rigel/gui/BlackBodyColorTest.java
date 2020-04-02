package ch.epfl.rigel.gui;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class BlackBodyColorTest {
    private static final String BLACK_BODY_COLOR_NAME = "/bbr_color.txt";


    @Test
    void BlackBodyColorIsCorrectlyInstalled() throws IOException {
        try (InputStream hygStream = getClass()
                .getResourceAsStream(BLACK_BODY_COLOR_NAME)) {
            assertNotNull(hygStream);
        }
    }

    @Test
    void throwExeptionForBadValue() throws IOException {
        try (InputStream hygStream = getClass()
                .getResourceAsStream(BLACK_BODY_COLOR_NAME)) {
            assertThrows(IllegalArgumentException.class,() -> {
                BlackBodyColor.colorForTemperature(999);
            });
            assertThrows(IllegalArgumentException.class,() -> {
                BlackBodyColor.colorForTemperature(40001);
            });
        }
    }

    @Test
    void workAsExepted() throws IOException{
        try(InputStream hygStream=getClass()
                .getResourceAsStream(BLACK_BODY_COLOR_NAME)){

            assertEquals(BlackBodyColor.colorForTemperature(1000),Color.web("#ff3800"));
            assertEquals(BlackBodyColor.colorForTemperature(10549),Color.web("#c8d9ff"));
            assertEquals(BlackBodyColor.colorForTemperature(10550),Color.web("#c7d8ff"));
            assertEquals(BlackBodyColor.colorForTemperature(40000),Color.web("#9bbcff"));

        }



    }




}