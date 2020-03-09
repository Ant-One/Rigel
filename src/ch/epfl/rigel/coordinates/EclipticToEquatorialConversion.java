package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.astronomy.Epoch;
import ch.epfl.rigel.math.Angle;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;


/**
 * Class used to converse Ecliptic coordinates to Equatorial coordinates
 *
 * @author Adrien Rey (313388)
 */

public final class EclipticToEquatorialConversion implements Function<EclipticCoordinates, EquatorialCoordinates> {

    private final double sinEpsilon;
    private final double cosEpsilon;


    /**
     * Constructor to convert coordinates
     *
     * @param when ZonedDateTime of the current location
     */
    public EclipticToEquatorialConversion(ZonedDateTime when) {
        double T = Epoch.J2000.julianCenturiesUntil(when.truncatedTo(ChronoUnit.DAYS));


        double epsilon = (0.00181 * T * T * T - 0.0006 * T * T - 46.815 * T) / 3600 + 23.439292; //Formula from the book
        sinEpsilon = Math.sin(Angle.ofDeg(epsilon));
        cosEpsilon = Math.cos(Angle.ofDeg(epsilon));
    }


    /**
     * Converts the given EclipticCoordinates to EquatorialCoordinates
     *
     * @param ec1 EclipticCoordinates to convert
     * @return the converted coordinates
     */
    @Override
    public EquatorialCoordinates apply(EclipticCoordinates ec1) {
        double sinLambda = Math.sin(ec1.lon());

        double delta = Math.asin(Math.sin(ec1.lat()) * cosEpsilon + Math.cos(ec1.lat()) * sinEpsilon * sinLambda);
        double alpha = Math.atan2(sinLambda * cosEpsilon - Math.tan(ec1.lat())* sinEpsilon,Math.cos(ec1.lon()));
        alpha=Angle.normalizePositive(alpha);

        return EquatorialCoordinates.of(alpha, delta);
    }


    /**
     * @throws UnsupportedOperationException when used
     */
    @Override
    final public int hashCode() {
        throw new UnsupportedOperationException();
    }

    /**
     * @throws UnsupportedOperationException when used
     */
    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }
}

