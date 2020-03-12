package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.math.Angle;

public enum SunModel implements CelestialObjectModel<Sun> {
    SUN;
    private final static double epsylon = Angle.ofDeg(279.557208), w = Angle.ofDeg(283.112438), e = 0.016705, thetaZero = 0.533128;


    @Override
    public Sun at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {
        double M = Angle.normalizePositive((Angle.TAU / 365.242191) * daysSinceJ2010 + epsylon - w);
        double v = M + 2 * e * Math.sin(M);
        double lambda = v + w;
        EclipticCoordinates coordinates = EclipticCoordinates.of(Angle.normalizePositive(lambda), 0);

        double theta = Angle.ofDeg(thetaZero * ((1 + e * Math.cos(v)) / (1 - e * e)));

        return new Sun(coordinates, eclipticToEquatorialConversion.apply(coordinates), (float) theta, (float) M);
    }
}
