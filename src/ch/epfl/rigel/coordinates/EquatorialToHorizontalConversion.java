package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.astronomy.Epoch;
import ch.epfl.rigel.math.Angle;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;


/**
 * Model a conversion from Equatorial to Horizontal coordinates
 *
 * @author Antoine Moix (310052)
 */
public class EquatorialToHorizontalConversion implements Function<EquatorialCoordinates, HorizontalCoordinates> {

    private final double sinEpsylon, cosEpsylon, Epsylon;

//TODO CORRECT CODE
    /**
     * Constructor to convert coordinates
     *
     * @param when ZonedDateTime of the current location
     */
    public EquatorialToHorizontalConversion(ZonedDateTime when) {
        double T = Epoch.J2000.julianCenturiesUntil(when.truncatedTo(ChronoUnit.DAYS));

        Epsylon = Angle.ofArcsec(0.00181) * T * T * T - Angle.ofArcsec(0.0006) * T * T - Angle.ofArcsec(46.815) * T + Angle.ofDMS(23, 26, 21.45);
        sinEpsylon = Math.sin(Epsylon);
        cosEpsylon = Math.cos(Epsylon);
    }


    @Override
    /**
     * Converts the given EclipticCoordinates to EclipticCoordinates
     * @param equatorialCoordinates EclipticCoordinates to convert
     * @return the converted coordinates
     */
    public HorizontalCoordinates apply(EquatorialCoordinates equatorialCoordinates) {
        double sinTheta = Math.sin(equatorialCoordinates.lon());

        double alpha = (sinTheta * cosEpsylon - Math.tan(equatorialCoordinates.lat())) / (Math.cos(equatorialCoordinates.lon()));
        alpha = Math.atan2(alpha, 1);
        double delta = Math.asin(Math.sin(equatorialCoordinates.lat()) * cosEpsylon + Math.cos(equatorialCoordinates.lat()) * sinEpsylon * sinTheta);


        return HorizontalCoordinates.of(alpha, delta);
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
