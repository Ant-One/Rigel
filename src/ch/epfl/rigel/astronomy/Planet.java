package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;

/**
 * Class representing a planet
 *
 * @author Antoine Moix (310052)
 */
public final class Planet extends CelestialObject {

    /**
     * Construct a planet object
     *
     * @param name          non-null String ; name of the planet object
     * @param equatorialPos non-null EquatorialCoordinates ; coordinates of the planet object
     * @param angularSize   size of the object
     * @param magnitude     magnitude of the object. Cannot be negative
     * @throws NullPointerException     if equatorialPos or name non-defined
     * @throws IllegalArgumentException if angularSize is negative
     */
    public Planet(String name, EquatorialCoordinates equatorialPos, float angularSize, float magnitude) {
        super(name, equatorialPos, angularSize, magnitude);
    }
}
