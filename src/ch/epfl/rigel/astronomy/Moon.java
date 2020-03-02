package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;

/**
 * Class representing the Moon
 *
 * @author Antoine Moix (310052)
 */
public final class Moon extends CelestialObject{
    /**
     * Construct a Moon object
     *
     * @param name          non-null String ; name of the moon object
     * @param equatorialPos non-null EquatorialCoordinates ; coordinates of the moon object
     * @param angularSize   size of the object
     * @param magnitude     magnitude of the object. Cannot be negative
     * @throws NullPointerException     if equatorialPos or name non-defined
     * @throws IllegalArgumentException if angularSize is negative
     */
    Moon(String name, EquatorialCoordinates equatorialPos, float angularSize, float magnitude) {
        super(name, equatorialPos, angularSize, magnitude);
    }
}
