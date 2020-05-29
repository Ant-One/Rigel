package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * loader for the HygData file
 *
 * @author Antoine Moix (310052)
 */
public enum HygDatabaseLoader implements StarCatalogue.Loader {
    INSTANCE;

    private enum Types {
        D, HIP, HD, HR, GL, BF, PROPER, RA, DEC, DIST, PMRA, PMDEC,
        RV, MAG, ABSMAG, SPECT, CI, X, Y, Z, VX, VY, VZ,
        RARAD, DECRAD, PMRARAD, PMDECRAD, BAYER, FLAM, CON,
        COMP, COMP_PRIMARY, BASE, LUM, VAR, VAR_MIN, VAR_MAX
    }


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

            inStream.readLine();

            while (inStream.ready()) {
                String line = inStream.readLine();
                String[] values = line.split(",");

                int hipparcosID;
                if (values[Types.HIP.ordinal()].isBlank()) {
                    hipparcosID = 0;
                } else {
                    hipparcosID = Integer.parseInt(values[Types.HIP.ordinal()]);
                }


                String name = values[Types.PROPER.ordinal()];
                String bayer = values[Types.BAYER.ordinal()];

                if (name.isEmpty() && !bayer.isEmpty()) {
                    name = bayer + " " + values[Types.CON.ordinal()];
                } else if (bayer.isEmpty()) {
                    name = "? " + values[Types.CON.ordinal()];
                }

                double raRad = Double.parseDouble(values[Types.RARAD.ordinal()]);
                double decRad = Double.parseDouble(values[Types.DECRAD.ordinal()]);

                double magnitude;
                if (values[Types.MAG.ordinal()].isBlank()) {
                    magnitude = 0;
                } else {
                    magnitude = Double.parseDouble(values[Types.MAG.ordinal()]);
                }

                double colorIndex;
                if (values[Types.CI.ordinal()].isBlank()) {
                    colorIndex = 0;
                } else {
                    colorIndex = Double.parseDouble(values[Types.CI.ordinal()]);
                }

                Star currentStar = new Star(hipparcosID, name, EquatorialCoordinates.of(raRad, decRad), (float) magnitude, (float) colorIndex);

                builder.addStar(currentStar);
            }
        }
    }
}
