package ch.epfl.rigel.coordinates;


import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;

import java.util.Locale;

/**
 * Class modelling horizontal coordinates
 *
 * @author Antoine Moix (310052)
 */
 public final class HorizontalCoordinates extends SphericalCoordinates{

     private static final ClosedInterval AZIMUTH_INTERVAL = ClosedInterval.of(0, Angle.TAU);
     private static final ClosedInterval ALTITUDE_INTERVAL = ClosedInterval.of(-Angle.TAU/4.0, Angle.TAU/4.0);

     private static final ClosedInterval NORTH_INTERVAL_1 = ClosedInterval.of(0, 3*Angle.TAU/16.0);
     private static final ClosedInterval EAST_INTERVAL = ClosedInterval.of(Angle.TAU/16.0, 7*Angle.TAU/16.0);
     private static final ClosedInterval SOUTH_INTERVAL = ClosedInterval.of(5*Angle.TAU/16.0, 11*Angle.TAU/16.0);
     private static final ClosedInterval WEST_INTERVAL = ClosedInterval.of(9*Angle.TAU/16.0, 15*Angle.TAU/16);
    private static final ClosedInterval NORTH_INTERVAL_2 = ClosedInterval.of(13*Angle.TAU/16, Angle.TAU);

     private HorizontalCoordinates(double az, double alt){
         super(az, alt);
     }

    public static HorizontalCoordinates of(double az, double alt){
         Preconditions.checkInInterval(AZIMUTH_INTERVAL, az);
         Preconditions.checkInInterval(ALTITUDE_INTERVAL, alt);

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
         double normalizedAz = Angle.normalizePositive(az());
         String result = "";
         if(NORTH_INTERVAL_1.contains(normalizedAz) || NORTH_INTERVAL_2.contains(normalizedAz)){
             result += n;
         }else if(SOUTH_INTERVAL.contains(normalizedAz)){
             result += s;
         }

         if(EAST_INTERVAL.contains(normalizedAz)) {
             result += e;
         }
        else if(WEST_INTERVAL.contains(normalizedAz)){
            result += w;
    }
        return result;
     }

     public double alt(){
         return lat();

     }

     public double altDeg(){
         return Angle.toDeg(lat());
     }

     public double angularDistanceTo(HorizontalCoordinates that){
         return 0;
     }

    @Override
    public String toString() {
         return String.format(Locale.ROOT, "(az=%.4f°, alt=%.4f°)", azDeg(), altDeg());

    }
}
