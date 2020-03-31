package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import java.util.Objects;

/**
 * Abstract class modelling a celestial object
 *
 * @author Antoine Moix (310052)
 */
public abstract class CelestialObject {

    private final String name;
    private final EquatorialCoordinates equatorialPos;
    private final float angularSize, magnitude;

    /**
     * Construct a celestial object
     *
     * @param name          non-null String ; name of the celestial object
     * @param equatorialPos non-null EquatorialCoordinates ; coordinates of the celestial object
     * @param angularSize   size of the object
     * @param magnitude     magnitude of the object. Cannot be negative
     * @throws NullPointerException     if equatorialPos or name non-defined
     * @throws IllegalArgumentException if angularSize is negative
     */
    CelestialObject(String name, EquatorialCoordinates equatorialPos, float angularSize, float magnitude) {

        Preconditions.checkArgument(angularSize >= 0);
        Objects.requireNonNull(name);
        Objects.requireNonNull(equatorialPos);

        this.name = name;
        this.equatorialPos = equatorialPos;
        this.angularSize = angularSize;
        this.magnitude = magnitude;
    }

    /**
     * Returns the name of the celestial object
     *
     * @return the name of the celestial object
     */
    public String name() {
        return name;
    }

    /**
     * Returns the angularSize of the celestial object
     *
     * @return the angularSize of the celestial object
     */
    public double angularSize() {
        return angularSize;
    }

    /**
     * Returns the magnitude of the celestial object
     *
     * @return the magnitude of the celestial object
     */
    public double magnitude() {
        return magnitude;
    }

    /**
     * Returns the equatorial position of the celestial object
     *
     * @return the equatorial position of the celestial object
     */
    public EquatorialCoordinates equatorialPos() {
        return equatorialPos;
    }

    /**
     * Method to be overridden. By default returns the same as the name() method. Is used to get more infos about the object
     *
     * @return by default same as name()
     */
    public String info() {
        return name();
    }

    /**
     * Same as name()
     *
     * @return same as name()
     */
    @Override
    public String toString() {
        return info();
    }
}
