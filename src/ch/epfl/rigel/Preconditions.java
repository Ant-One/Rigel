/**
 * Utilitary Class used to check things
 *
 * @author Antoine Moix (310052)
 */

package ch.epfl.rigel;

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
    void checkArgument(boolean isTrue) throws IllegalArgumentException{
        if(!isTrue){
            throw new IllegalArgumentException();
        }

    }
    double checkInInterval(Interval interval, double value){ //TODO Class Interval
    }
}