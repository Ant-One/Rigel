package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;

/**
 * @param <O> the type of the model
 * @author Antoine Moix (310052)
 */
public interface CelestialObjectModel<O> {

    /**
     * Method to compute the object after a number of days (can be negative!)
     *
     * @param daysSinceJ2010                 number of days after J2010
     * @param eclipticToEquatorialConversion conversion to convert ecliptic coordinates to equatorial ones
     * @return the object at the time specified
     */
    O at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion);
}
