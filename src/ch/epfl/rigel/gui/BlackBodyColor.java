package ch.epfl.rigel.gui;

import ch.epfl.rigel.Preconditions;
import javafx.scene.paint.Color;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


/**
 * @author Antoine Moix (310052)
 *
 * Utilitary class used to convert the temperature in Kelvin to a color
 */
public class BlackBodyColor {

    private static final String BLACK_BODY_COLOR_NAME = "/bbr_color.txt";
    private static final HashMap<Integer, String> colorMap = new HashMap<>();

    //Block used to initialize the color map
    static{
        try (BufferedReader bbrStream = new BufferedReader(new InputStreamReader(BlackBodyColor.class.getResourceAsStream(BLACK_BODY_COLOR_NAME), StandardCharsets.US_ASCII))) {
            while(bbrStream.ready()){
                String readLine = bbrStream.readLine();

                if(readLine.charAt(0) != '#' && readLine.charAt(11) != '2'){ //If it is a comment line or the value for 2 deg, we skip it

                    String substring=readLine.substring(1, 6);
                    if(substring.charAt(0)==' '){
                        substring=substring.substring(1,5);
                    }
                    int kelvin = Integer.valueOf(substring) ;
                    String color = readLine.substring(80, 87);

                    colorMap.put(kelvin, color);
                }
            }
        }catch(IOException e){
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Useless constructor
     * @throws UnsupportedOperationException
     */
    private BlackBodyColor(){
        throw new UnsupportedOperationException("BlackBodyColor is not instantiable");
    }

    /**
     * Method used to convert the temperature in Kelvin to a JFX Color Object
     * @param kelvinTemp the temperature in Kelvin. Must be between 1000 and 40'000
     * @return a JFX Color object representing the black body color for a given temperature
     * @throws IllegalArgumentException if kelvinTemp is not between 1000 and 40'000
     */
    public static Color colorForTemperature(float kelvinTemp){
        Preconditions.checkArgument(kelvinTemp >= 1000 && kelvinTemp <= 40000);


        int roundedTemp=Math.round(kelvinTemp/100)*100;

        String color = colorMap.get(roundedTemp);

        return Color.web(color);
    }
}
