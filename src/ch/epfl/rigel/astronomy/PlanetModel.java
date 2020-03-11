package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.math.Angle;

import java.util.Arrays;
import java.util.List;
import static java.lang.Math.*;

/**
 * Model of Solar System Planets
 * @author Antoine Moix (310052)
 */

public enum PlanetModel implements CelestialObjectModel<Planet> {

    MERCURY("Mercure", 0.24085, 75.5671, 77.612, 0.205627,
            0.387098, 7.0051, 48.449, 6.74, -0.42),
    VENUS("Vénus", 0.615207, 272.30044, 131.54, 0.006812,
            0.723329, 3.3947, 76.769, 16.92, -4.40),
    EARTH("Terre", 0.999996, 99.556772, 103.2055, 0.016671,
            0.999985, 0, 0, 0, 0),
    MARS("Mars", 1.880765, 109.09646, 336.217, 0.093348,
            1.523689, 1.8497, 49.632, 9.36, -1.52),
    JUPITER("Jupiter", 11.857911, 337.917132, 14.6633, 0.048907,
            5.20278, 1.3035, 100.595, 196.74, -9.40),
    SATURN("Saturne", 29.310579, 172.398316, 89.567, 0.053853,
            9.51134, 2.4873, 113.752, 165.60, -8.88),
    URANUS("Uranus", 84.039492, 271.063148, 172.884833, 0.046321,
            19.21814, 0.773059, 73.926961, 65.80, -7.19),
    NEPTUNE("Neptune", 165.84539, 326.895127, 23.07, 0.010483,
            30.1985, 1.7673, 131.879, 62.20, -6.87);

    private String frenchName;
    private double tropicalYear, J2010Long, periapsisLongitude, orbitalEccentricity, semiMajorAxis, orbitalDeclination;
    private double ascendingNodeLongitude, angularSize, magnitude;

    public static List<PlanetModel> ALL = Arrays.asList(PlanetModel.values());

    /**
     * construct a PlanetModel according to the specified data
     * @param frenchName string of the French name of the planet
     * @param tropicalYear Revolution period of the planet in tropicalYear
     * @param J2010Long longitude at J2010 in degrees
     * @param periapsisLongitude longitude at the periapsis in degrees (périgée in French)
     * @param orbitalEccentricity orbit's eccentricity
     * @param semiMajorAxis semi-major axis of the orbit in UA
     * @param orbitalDeclination orbital declination in degrees
     * @param ascendingNodeLongitude longitude at the ascending node in degrees
     * @param angularSize angular size of the planet in arcsecs
     * @param magnitude magnitude of the planet
     */
    PlanetModel(String frenchName, double tropicalYear, double J2010Long, double periapsisLongitude, double orbitalEccentricity,
                double semiMajorAxis, double orbitalDeclination, double ascendingNodeLongitude,
                double angularSize, double magnitude) {

        this.frenchName = frenchName;
        this.tropicalYear = tropicalYear;
        this.J2010Long = Angle.ofDeg(J2010Long);
        this.periapsisLongitude = Angle.ofDeg(periapsisLongitude);
        this.orbitalEccentricity = orbitalEccentricity;
        this.orbitalDeclination = Angle.ofDeg(orbitalDeclination);
        this.semiMajorAxis = semiMajorAxis;
        this.ascendingNodeLongitude = Angle.ofDeg(ascendingNodeLongitude);
        this.angularSize = Angle.ofArcsec(angularSize);
        this.magnitude = magnitude;
    }

    /**
     * Method to compute the object after a number of days (can be negative!)
     *
     * @param daysSinceJ2010                 number of days after J2010
     * @param eclipticToEquatorialConversion conversion to convert ecliptic coordinates to equatorial ones
     * @return the object at the time specified
     */
    @Override
    public Planet at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {
        double meanAnomaly = (Angle.TAU/365.242191) * (daysSinceJ2010/ tropicalYear) + J2010Long - periapsisLongitude;
        double realAnomaly = meanAnomaly + 2 * orbitalEccentricity * sin(meanAnomaly);

        double radius = (semiMajorAxis * (1 - pow(orbitalEccentricity, 2))) / (1 + orbitalEccentricity * cos(realAnomaly));
        double heliocentricLongitude = realAnomaly + periapsisLongitude;
        double heliocentricEclipticLatitude = asin(Math.sin(heliocentricLongitude - ascendingNodeLongitude) * sin(orbitalDeclination));

        double projectedRadius = radius * cos(heliocentricEclipticLatitude);
        double projectedLongitude = atan2(sin(heliocentricLongitude - ascendingNodeLongitude) * cos(orbitalDeclination), cos(heliocentricLongitude - ascendingNodeLongitude))
                + ascendingNodeLongitude;

        double earthMeanAnomaly = (Angle.TAU/365.242191) * (daysSinceJ2010/PlanetModel.EARTH.tropicalYear) + PlanetModel.EARTH.J2010Long - PlanetModel.EARTH.periapsisLongitude;
        double earthRealAnomaly = earthMeanAnomaly + 2 * PlanetModel.EARTH.orbitalEccentricity * sin(earthMeanAnomaly);

        double earthRadius = (PlanetModel.EARTH.semiMajorAxis * (1 - pow(PlanetModel.EARTH.orbitalEccentricity, 2)))
                / (1 + PlanetModel.EARTH.orbitalEccentricity * cos(earthRealAnomaly));
        double earthHeliocentricLongitude = earthRealAnomaly + PlanetModel.EARTH.periapsisLongitude;

        double geocentricEclipticLongitude;

        if(this == PlanetModel.MERCURY || this == PlanetModel.VENUS){
            geocentricEclipticLongitude = Angle.TAU/2 + earthHeliocentricLongitude +
                    atan2(projectedRadius * sin(earthHeliocentricLongitude - projectedLongitude),
                            earthRadius - projectedRadius * cos(earthHeliocentricLongitude - projectedLongitude));
        }else{
            geocentricEclipticLongitude = projectedLongitude
                    + atan2(earthRadius * sin(projectedLongitude - earthHeliocentricLongitude),
                    projectedRadius - earthRadius * cos(projectedLongitude - earthHeliocentricLongitude));
        }

        double geocentricLatitude = atan2(projectedRadius * tan(heliocentricEclipticLatitude)
                * sin(geocentricEclipticLongitude - projectedLongitude),
                earthRadius * sin(projectedLongitude - earthHeliocentricLongitude));

        double rho = sqrt(pow(earthRadius, 2) + pow(radius, 2) - 2 * earthRadius * radius
                * cos(heliocentricLongitude - earthHeliocentricLongitude) * cos(heliocentricEclipticLatitude));

        double adujustedAngularSize = angularSize / rho;

        double phase = (1 + cos(geocentricLatitude - heliocentricLongitude) / 2);
        double adjustedMagnitude = magnitude + 5 * log10((radius * rho) / sqrt(phase));

        return new Planet(frenchName, eclipticToEquatorialConversion.apply(EclipticCoordinates.of(geocentricEclipticLongitude, geocentricLatitude)),
                (float) adujustedAngularSize, (float) adjustedMagnitude);
    }
}
