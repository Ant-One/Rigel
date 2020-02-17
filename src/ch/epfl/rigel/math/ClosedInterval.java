package ch.epfl.rigel.math;

public final class ClosedInterval extends Interval{


    /**
     * Basic Interval constructor
     *
     * @param lowerBound Lower bound of the interval
     * @param upperBound
     */
    private ClosedInterval(double lowerBound, double upperBound) {
        super(lowerBound, upperBound);
    }




    @Override
    public boolean contains(double v) {
        return false;
    }
}
