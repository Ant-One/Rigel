package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author Antoine Moix (310052)
 */
public enum HygDatabaseLoader implements StarCatalogue.Loader{
    INSTANCE;

    private enum Types{
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
        try (BufferedReader inStream = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.US_ASCII))){

            inStream.readLine();

            while(inStream.ready()) {
                String line = inStream.readLine();
                String[] values = line.split(",");

                int hipparcosID;
                try{
                    hipparcosID = Integer.parseInt(values[Types.HIP.ordinal()]);
                }
                catch (NumberFormatException e){
                    hipparcosID = 0;
                }

                String name = values[Types.PROPER.ordinal()];
                String bayer = values[Types.BAYER.ordinal()];

                if(name.isEmpty() && !bayer.isEmpty()){
                    name = bayer + " " + values[Types.CON.ordinal()];
                }else if(bayer.isEmpty()){
                    name = "? " + values[Types.CON.ordinal()];
                }

                double raRad = Double.parseDouble(values[Types.RARAD.ordinal()]);
                double decRad = Double.parseDouble(values[Types.DECRAD.ordinal()]);

                double magnitude;
                try{
                    magnitude = Double.parseDouble(values[Types.MAG.ordinal()]);
                }
                catch (NumberFormatException e){
                    magnitude = 0;
                }

                double colorIndex;
                try{
                    colorIndex = Double.parseDouble(values[Types.CI.ordinal()]);
                }
                catch (NumberFormatException e){
                    colorIndex = 0;
                }

                Star currentStar = new Star(hipparcosID, name, EquatorialCoordinates.of(raRad, decRad), (float) magnitude, (float) colorIndex);

                builder.addStar(currentStar);
            }
        }
    }
}
