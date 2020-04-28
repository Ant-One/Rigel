package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.ClosedInterval;

import java.util.Locale;

/**
 * Class representing the Moon
 *
 * @author Antoine Moix (310052)
 */
public final class Moon extends CelestialObject {

    private final float phase;

    private static final ClosedInterval INTERVAL_PHASE = ClosedInterval.of(0, 1);
    private static final String MOON_NAME = "Lune";

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
        super(MOON_NAME, equatorialPos, angularSize, magnitude);

        Preconditions.checkInInterval(INTERVAL_PHASE, phase);
        this.phase = phase;
    }

    /**
     * Returns the name of the Moon and its phase in percents in a String
     *
     * @return the name of the Moon and its phase in percents in a String
     */
    @Override
    public String info() {
        StringBuilder stringBuilder = new StringBuilder(name());
        stringBuilder.append(" (");
        stringBuilder.append(String.format(Locale.ROOT, "%.1f", phase * 100));
        stringBuilder.append("%)");
        return stringBuilder.toString();
    }
}
