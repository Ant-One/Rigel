package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import java.util.Objects;

/**
 * Class modelling the Sun
 *
 * @author Antoine Moix (310052)
 */
public final class Sun extends CelestialObject{

    private static final String sunName = "Soleil";
    private static final float sunMagnitude = -26.7f;

    private final float meanAnomaly;
    private final EclipticCoordinates eclipticPos;

    /**
     * Construct a Sun object
     * @param eclipticPos non-null EclipticCoordinates ; Ecliptic coordinates of the sun object
     * @param equatorialPos non-null EquatorialCoordinates ; Equatorial coordinates of the sun object
     * @param angularSize   size of the object
     * @throws NullPointerException if eclipticPos non-defined
     */

    public Sun(EclipticCoordinates eclipticPos, EquatorialCoordinates equatorialPos, float angularSize, float meanAnomaly){
        super(sunName, equatorialPos, angularSize, sunMagnitude);

        Objects.requireNonNull(eclipticPos);

        this.eclipticPos = eclipticPos;
        this.meanAnomaly = meanAnomaly;
    }

    /**
     * Returns the ecliptic position of the sun object
     * @return the ecliptic position of the sun object
     */
    public EclipticCoordinates eclipticPos() {
        return eclipticPos;
    }

    /**
     * Returns the mean anomaly of the sun object
     * @return the mean anomaly of the sun object
     */
    public double meanAnomaly(){
        return meanAnomaly;
    }
}
