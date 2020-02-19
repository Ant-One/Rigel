package ch.epfl.rigel.coordinates;


import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.Interval;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Class modelling horizontal coordinates
 *
 * @author Antoine Moix (310052)
 */
 public final class HorizontalCoordinates extends SphericalCoordinates{

     private static final ClosedInterval azimuthInterval = ClosedInterval.of(0, Angle.TAU);
     private static final ClosedInterval altitudeInterval = ClosedInterval.of(-Angle.TAU/4.0, Angle.TAU/4.0);

     private static final Map<Interval, String> octantMap = Map.of();

     private HorizontalCoordinates(double az, double alt){
         super(az, alt);
     }

     public static HorizontalCoordinates of(double az, double alt){
         Preconditions.checkInInterval(azimuthInterval, az);
         Preconditions.checkInInterval(altitudeInterval, alt);

         return new HorizontalCoordinates(az, alt);
     }

     public static HorizontalCoordinates ofDeg(double azDeg, double altDeg){
         return new HorizontalCoordinates(Angle.ofDeg(azDeg), Angle.ofDeg(altDeg));
     }

     public double az(){
         return lon();
     }

     public double azDeg(){
         return Angle.toDeg(lon());
     }

     public String azOctantName(String n, String e, String s, String w){

     }

     public double alt(){
         return lat();

     }

     public double altDeg(){
         return Angle.toDeg(lat());
     }

     public double angularDistanceTo(HorizontalCoordinates that){

     }

    @Override
    public String toString() {
         return String.format(Locale.ROOT, "(az=%.4f°, alt=%.4f°)", azDeg(), altDeg());

    }
}
