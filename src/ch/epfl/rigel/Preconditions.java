/**
 * Utilitary Class used to check things
 *
 * @author Antoine Moix (310052)
 */

package ch.epfl.rigel;

import ch.epfl.rigel.math.Interval;

public final class Preconditions {

    /**
     * Useless constructor
     */
    private Preconditions() {}

    /**
     * Method that throws an IllegalArgumentException if its parameter is false
     * @param isTrue parameter to use
     *
     * @throws IllegalArgumentException if isTrue is false
     */
    public static void checkArgument(boolean isTrue){
        if(!isTrue){
            throw new IllegalArgumentException();
        }

    }
    public static double checkInInterval(Interval interval, double value){
        if(interval.contains(value)){
            return value;
        }
        else{
            throw new IllegalArgumentException();
        }

    }
}