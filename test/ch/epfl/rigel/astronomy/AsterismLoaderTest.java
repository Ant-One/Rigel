package ch.epfl.rigel.astronomy;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AsterismLoaderTest {
    private static final String AST_CATALOGUE_NAME =
            "/asterisms.txt";
    private static final String HYG_CATALOGUE_NAME =
            "/hygdata_v3.csv";

    @Test
    void asterismsIsCorrectlyInstalled() throws IOException {
        try (InputStream hygStream = getClass()
                .getResourceAsStream(AST_CATALOGUE_NAME)) {
            assertNotNull(hygStream);
        }
    }


    @Test
    void asterismLoaderWork() throws IOException {
        try (InputStream hygStream = getClass()
                .getResourceAsStream(HYG_CATALOGUE_NAME)) {
            try (InputStream astStream = getClass()
                    .getResourceAsStream(AST_CATALOGUE_NAME)) {

                StarCatalogue.Builder builder = new StarCatalogue.Builder()
                        .loadFrom(hygStream, HygDatabaseLoader.INSTANCE);
                StarCatalogue catalogue =builder.loadFrom(astStream, AsterismLoader.INSTANCE).build();

                Set<Asterism> setAst=catalogue.asterisms();
                int i=0;
                for (Asterism ast : setAst
                     ) {

                    for (Star star : ast.stars()
                         ) {

                        if(star.hipparcosId()==7607){
                            i=1;
                        }

                    }

                }

                assertEquals(1,i);

            }
        }


    }





}