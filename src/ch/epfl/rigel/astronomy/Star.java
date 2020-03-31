package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.ClosedInterval;

/**
 * Basic Star object
 * @author Adrien Rey (313388)
 */

public final class Star extends CelestialObject {
    private final int hipparcosId;
    private final float colorIndex;
    private final static ClosedInterval colorIndexInterval= ClosedInterval.of(-0.5,5.5);

    /**
     * Basic constructor for a star
     * @param hipparcosId the hipparcos Id
     * @param name the star's name
     * @param equatorialPos the equatorial coordinates of the star
     * @param magnitude the magnitude of the star
     * @param colorIndex the index of the color of the star
     */
    public Star(int hipparcosId, String name, EquatorialCoordinates equatorialPos, float magnitude, float colorIndex){
        super(name,equatorialPos,0,magnitude);

        Preconditions.checkInInterval(colorIndexInterval,colorIndex);
        this.colorIndex=colorIndex;

        Preconditions.checkArgument(hipparcosId>=0);
        this.hipparcosId=hipparcosId;

    }

    /**
     * The hipparcosId of the star
     * @return the Hipparcos Id
     */
    public int hipparcosId() {
        return hipparcosId;
    }

    /**
     * the color index of the star
     * @return the color index
     */
    public int colorTemperature() {
        double colorTemperature = 4600 * (1/(0.92 * colorIndex + 1.7) + 1/(0.92 * colorIndex + 0.62));
        return (int) Math.floor(colorTemperature);
    }
}
