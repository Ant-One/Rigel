package ch.epfl.rigel.astronomy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Loader of asterisms
 *
 * @author Antoine Moix (310052)
 */
public enum AsterismLoader implements StarCatalogue.Loader {
    INSTANCE;

    /**
     * Loads stars and/or asterisms from the input stream and add them to the builder's lists
     *
     * @param inputStream the input stream of data for the stars and asterisms
     * @param builder     the builder to whose catalogue add the read data
     * @throws IOException if an error with the streams happens
     */
    @Override
    public void load(InputStream inputStream, StarCatalogue.Builder builder) throws IOException {
        try (BufferedReader inStream = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.US_ASCII))) {

            do {
                String line = inStream.readLine();
                String[] values = line.split(",");

                ArrayList<Star> currentStars = new ArrayList<>();

                HashMap<Integer,Star > starsHipparcos=new HashMap<>();
                for (Star star : builder.stars()) {
                    starsHipparcos.put(star.hipparcosId(),star);
                }

                for (String idString : values) {
                    int id = Integer.parseInt(idString);
                    currentStars.add(starsHipparcos.get(id));
                }

                builder.addAsterism(new Asterism(currentStars));
            } while (inStream.ready());
        }
    }
}
