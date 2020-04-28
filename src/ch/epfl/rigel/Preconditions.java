package ch.epfl.rigel;

import ch.epfl.rigel.math.Interval;

/**
 * Utility Class used to check things
 *
 * @author Antoine Moix (310052)
 */
public final class Preconditions {

    /**
     * Useless constructor
     */
    private Preconditions() {
    }

    /**
     * Method that throws an IllegalArgumentException if its parameter is false
     *
     * @param isTrue parameter to use
     * @throws IllegalArgumentException if isTrue is false
     */
    public static void checkArgument(boolean isTrue) {
        if (!isTrue) {
            throw new IllegalArgumentException("Wrong Argument");
        }

    }

    /**
     * Method that checks if a value is in an interval object
     *
     * @param interval interval in which the value may be
     * @param value    value to check if it is in the interval
     * @return value if the value is in the interval
     * @throws IllegalArgumentException if the value is not in the interval
     */
    public static double checkInInterval(Interval interval, double value) {
        if (interval.contains(value)) {
            return value;
        } else {
            throw new IllegalArgumentException();
        }

    }
}