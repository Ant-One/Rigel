package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

import java.util.Locale;

/**
 * interval closed on both side
 *
 * @author Adrien Rey (313388)
 */
public final class ClosedInterval extends Interval {


    /**
     * Basic Interval constructor
     *
     * @param lowerBound Lower bound of the interval
     * @param upperBound Upper bound of the interval
     */
    private ClosedInterval(double lowerBound, double upperBound) {
        super(lowerBound, upperBound);
    }

    /**
     * Construct an interval between the low and upper bound
     *
     * @param lowerBound lower Bound of the interval
     * @param upperBound UpperBound of the interval
     * @return the new interval
     */
    static public ClosedInterval of(double lowerBound, double upperBound) {
        Preconditions.checkArgument(lowerBound < upperBound);
        return new ClosedInterval(lowerBound, upperBound);
    }

    /**
     * Construct an interval around 0 with a size of size
     *
     * @param size of the interval
     * @return the new interval
     */
    static public ClosedInterval symmetric(double size) {
        Preconditions.checkArgument(size > 0);

        return new ClosedInterval(-size / 2, size / 2);
    }


    @Override
    public boolean contains(double v) {
        return ((v >= low()) && (v <= high()));
    }

    /**
     * clips the value v to the interval
     *
     * @param v value to clip
     * @return the clipped value
     */
    public double clip(double v) {
        if (v <= low()) {
            return low();
        } else if (v >= high()) {
            return high();
        } else {
            return v;
        }
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "[%f,%f]", low(), high());
    }
}
