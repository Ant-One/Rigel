package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.math.Angle;

/**
 * This class is used to compute sidereal time
 *
 * @author Adrien Rey (313388)
 */
public enum SunModel implements CelestialObjectModel<Sun> {
    SUN;
    private final static double epsylon = Angle.ofDeg(279.557208), w = Angle.ofDeg(283.112438), e = 0.016705, thetaZero = 0.533128;


    /**
     * Method to simulate the next position of the Sun
     *
     * @param daysSinceJ2010                 number of days after J2010
     * @param eclipticToEquatorialConversion conversion to convert ecliptic coordinates to equatorial ones
     * @return a new Sun object
     */
    @Override
    public Sun at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {
        double M = Angle.normalizePositive((Angle.TAU / 365.242191) * daysSinceJ2010 + epsylon - w);
        double v = M + 2 * e * Math.sin(M);
        double lambda = Angle.normalizePositive(v + w);
        EclipticCoordinates coordinates = EclipticCoordinates.of(Angle.normalizePositive(lambda), 0); //no need to reduce, lat = 0 anyways

        double theta = Angle.ofDeg(thetaZero * ((1 + e * Math.cos(v)) / (1 - e * e)));

        return new Sun(coordinates, eclipticToEquatorialConversion.apply(coordinates), (float) theta, (float) M);
    }
}
