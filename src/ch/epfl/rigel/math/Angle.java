package ch.epfl.rigel.math;

/**
 * Utilitary class for angles
 * @author Antoine Moix (310052)
 */
public final class Angle {

    public final static double TAU = Math.PI * 2.0; //We define here the Tau constant

    private Angle(){
    }

    double normalizePositive(double rad){
        return 0;
    }

    double ofArcsec(double sec){
        return 0;
    }

    double ofDMS(int deg, int min, double sec){
        return 0;
    }

    /**
     * Return the value in degrees for a given radian angle
     * @param rad angle in radians to convert
     * @return the given angle in degrees
     */
    double toDeg(double rad){
        return Math.toDegrees(rad);
    }

    double ofHr(double hr){
        return 0;
    }

    double toHr(double rad){
        return 0;
    }

}
