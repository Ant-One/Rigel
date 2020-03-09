package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.ClosedInterval;

import java.io.PrintWriter;

public final class Star extends CelestialObject {
    private final int hipparcosId;
    private final float colorIndex;
    private final static ClosedInterval colorIndexInterval= ClosedInterval.of(-0.5,5.5);

    /**
     * Basic contructor for a star
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
    public float colorIndex() {
        return colorIndex;
    }
}
