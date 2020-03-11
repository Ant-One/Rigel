package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.math.Angle;

import java.util.Arrays;
import java.util.List;

/**
 * Model of Solar System Planet
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
    private double tropicalYean, J2010Long, periapsisLong, orbitalEccentricity, semiMajorAxis, orbitalDeclination;
    private double ascendingNodeLong, angularSize, magnitude;

    public List<PlanetModel> ALL = Arrays.asList(PlanetModel.values());

    /**
     * construct a PlanetModel according to the specified data
     * @param frenchName string of the French name of the planet
     * @param tropicalYear Revolution period of the planet
     * @param J2010Long longitude at J2010
     * @param periapsisLong longitude at the periapsis
     * @param orbitalEccentricity orbit's eccentricity
     * @param semiMajorAxis semi-major axis of the orbit
     * @param orbitalDeclination orbital declination
     * @param ascendingNodeLong longitude at the ascending node
     * @param angularSize angular size of the planet
     * @param magnitude magnitude of the planet
     */
    PlanetModel(String frenchName, double tropicalYear, double J2010Long, double periapsisLong, double orbitalEccentricity,
                double semiMajorAxis, double orbitalDeclination, double ascendingNodeLong,
                double angularSize, double magnitude) {

        this.frenchName = frenchName;
        this.tropicalYean = tropicalYear;
        this.J2010Long = Angle.ofDeg(J2010Long);
        this.periapsisLong = Angle.ofDeg(periapsisLong);
        this.orbitalEccentricity = orbitalEccentricity;
        this.orbitalDeclination = Angle.ofDeg(orbitalDeclination);
        this.semiMajorAxis = semiMajorAxis;
        this.ascendingNodeLong = Angle.ofDeg(ascendingNodeLong);
        this.angularSize = angularSize;
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
        return null;
    }
}
