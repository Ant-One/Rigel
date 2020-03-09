package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.ClosedInterval;

/**
 * Class representing the Moon
 *
 * @author Antoine Moix (310052)
 */
public final class Moon extends CelestialObject{

    private final float phase;

    private static final ClosedInterval intervalPhase = ClosedInterval.of(0, 1);
    private static final String moonName = "Lune";

    /**
     * Construct a Moon object
     *
     * @param equatorialPos non-null EquatorialCoordinates ; coordinates of the moon object
     * @param angularSize   size of the object
     * @param magnitude     magnitude of the object. Cannot be negative
     * @throws NullPointerException     if equatorialPos or name non-defined
     * @throws IllegalArgumentException if angularSize is negative or phase is not in [0;1]
     */
    public Moon(EquatorialCoordinates equatorialPos, float angularSize, float magnitude, float phase) {
        super(moonName, equatorialPos, angularSize, magnitude);

        Preconditions.checkInInterval(intervalPhase, phase);
        this.phase = phase;
    }

    /**
     * Returns the String for the Moon ("Lune" in French) in a String
     * @return the String for the Moon ("Lune" in French) in a String
     */
    @Override
    public String name() {
        return super.name();
    }

    /**
     * Returns the name of the Moon and its phase in percents in a String
     * @return the name of the Moon and its phase in percents in a String
     */
    @Override
    public String info() {
        StringBuilder stringBuilder = new StringBuilder(name());
        stringBuilder.append(" (");
        stringBuilder.append(phase * 100);
        stringBuilder.append("%)");
        return stringBuilder.toString();
    }
}