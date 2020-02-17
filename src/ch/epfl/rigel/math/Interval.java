package ch.epfl.rigel.math;

/**
 * Super class for interval
 *
 * @author Adrien Rey (313388)
 */

public abstract class Interval {

    private final double lowerBound,upperBound;


    /**
     *  Basic Interval constructor
     *
     * @param lowerBound
     *          Lower bound of the interval
     * @param upperBound
     *          Upper boud of the interval
     */
    public Interval(double lowerBound, double upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    /**
     * @return  the lower bound of the interval
     */
    public double low() {
        return lowerBound;
    }

    /**
     * @return  the upper bound of the interval
     */
    public double high() {
        return upperBound;
    }

    /**
     * @return  the size of the interval
     */
    public double size(){
        return upperBound-lowerBound;
    }

    /**
     *  return true if the interval contains the param
     * @param v
     *      value
     */
    public abstract boolean contains(double v);


    @Override
    final public int hashCode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }
}
