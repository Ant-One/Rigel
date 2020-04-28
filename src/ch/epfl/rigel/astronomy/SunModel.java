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
    private final static double EPSYLON = Angle.ofDeg(279.557208), W = Angle.ofDeg(283.112438), E = 0.016705, THETA_ZERO = 0.533128,C=Angle.TAU / 365.242191;
    private  final static double E2=E*E;

    /**
     * Method to simulate the next position of the Sun
     *
     * @param daysSinceJ2010                 number of days after J2010
     * @param eclipticToEquatorialConversion conversion to convert ecliptic coordinates to equatorial ones
     * @return a new Sun object
     */
    @Override
    public Sun at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {
        double M = Angle.normalizePositive(C * daysSinceJ2010 + EPSYLON - W);
        double v = M + 2 * E * Math.sin(M);
        double lambda = Angle.normalizePositive(v + W);
        EclipticCoordinates coordinates = EclipticCoordinates.of(Angle.normalizePositive(lambda), 0); //no need to reduce, lat = 0 anyways

        double theta = Angle.ofDeg(THETA_ZERO * ((1 + E * Math.cos(v)) / (1 - E2)));

        return new Sun(coordinates, eclipticToEquatorialConversion.apply(coordinates), (float) theta, (float) M);
    }
}
