package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;

/**
 * Mother class for all Spherical coordinates
 *
 * @author Adrien Rey (313388)
 */
abstract class SphericalCoordinates {
    private final double longitude;
    private final double latitude;

    /**
     * Package private spherical coordinates constructor
     *
     * @param longitude longitude in rad
     * @param latitude  latitude in rad
     */
    SphericalCoordinates(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * @return the latitude in radian
     */
    double lat() {
        return latitude;
    }

    /**
     * @return the latitude in deg
     */

    double latDeg() {
        return Angle.toDeg(latitude);
    }

    /**
     * @return the longitude in radian
     */
    double lon() {
        return longitude;
    }

    /**
     * @return the longitude in deg
     */

    double lonDeg() {
        return Angle.toDeg(longitude);
    }

    @Override
    final public boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    final public int hashCode() {
        throw new UnsupportedOperationException();
    }
}
