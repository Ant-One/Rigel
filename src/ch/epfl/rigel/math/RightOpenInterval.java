package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

import java.util.Locale;

/**
 * Interval open right
 *
 * @author Adrien Rey (313388)
 */

public class RightOpenInterval extends Interval {
    /**
     * Basic Interval constructor
     *
     * @param lowerBound Lower bound of the interval
     * @param upperBound Upper bound of the interval
     */
    private RightOpenInterval(double lowerBound, double upperBound) {
        super(lowerBound, upperBound);
    }

    /**
     * Construct an interval between the low and upper bound
     *
     * @param lowerBound lower Bound of the interval
     * @param upperBound UpperBound of the interval
     * @return the new interval
     * @throws IllegalArgumentException if the lower bound is bigger than the upper one
     */
    static public RightOpenInterval of(double lowerBound, double upperBound) {
        Preconditions.checkArgument(lowerBound < upperBound);
        return new RightOpenInterval(lowerBound, upperBound);
    }

    /**
     * Construct an interval around 0 with a size of size
     *
     * @param size of the interval
     * @return the new interval
     */
    static public RightOpenInterval symmetric(double size) {
        Preconditions.checkArgument(size > 0);

        return new RightOpenInterval(-size / 2, size / 2);
    }


    @Override
    public boolean contains(double v) {
        return ((v >= low()) && (v < high()));
    }

    /**
     * reduce the value on the interval
     *
     * @param v the value to reduce
     * @return the reduced value
     */
    public double reduce(double v) {
        return low() + floorMod(v - low(), high() - low());
    }

    /**
     * Mathematical floorMod
     *
     * @return floorMod
     */
    private static double floorMod(double x, double y) {
        return x - y * Math.floor(x / y);
    }


    /**
     * toString method
     *
     * @return the string representation of the RightOpenInterval object
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT, "[%f,%f[", low(), high());
    }
}
