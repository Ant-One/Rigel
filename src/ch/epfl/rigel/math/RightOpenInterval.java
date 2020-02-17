package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

import java.util.Locale;

public class RightOpenInterval extends Interval {
    /**
     * Basic Interval constructor
     *
     * @param lowerBound Lower bound of the interval
     * @param upperBound
     */
    private RightOpenInterval(double lowerBound, double upperBound) {
        super(lowerBound, upperBound);
    }

    /**
     * Contruct an interval between the low and upper bound
     * @param lowerBound lower Bound of the interval
     * @param upperBound UpperBound of the interval
     * @return the new interval
     */
    static public RightOpenInterval  of(double lowerBound, double upperBound){
        Preconditions.checkArgument(!(lowerBound<upperBound));
        RightOpenInterval  interval=new RightOpenInterval(lowerBound,upperBound) ;
        return interval;
    }

    /**
     *  Contruct an interval around 0 with a size of size
     * @param size of the interval
     * @return the new interval
     */
    static public RightOpenInterval  symmetric(double size){
        Preconditions.checkArgument(!(size>0));

        RightOpenInterval  interval=new RightOpenInterval(-size/2,size/2) ;
        return interval;
    }


    @Override
    public boolean contains(double v) {
        return ( (v>=low()) && (v<high() ));
    }

    public double reduce(double v){
        return low()+floorMod(v-low(),high()-low());
    }

    private double floorMod(double x,double y){
        return x-y*Math.floor(x/y);
    }


    @Override
    public String toString() {
        return String.format(Locale.ROOT,"[%f,%f[",low(),high());
    }
}
