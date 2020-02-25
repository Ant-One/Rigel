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

    private final double sinEpsylon, cosEpsylon, Epsylon;


    /**
     * Constructor to convert coordinates
     *
     * @param when ZonedDateTime of the current location
     */
    public EclipticToEquatorialConversion(ZonedDateTime when) {
        double T = Epoch.J2000.julianCenturiesUntil(when.truncatedTo(ChronoUnit.DAYS));//TODO;

        Epsylon = Angle.ofArcsec(0.00181) * T * T * T - Angle.ofArcsec(0.0006) * T * T - Angle.ofArcsec(46.815) * T + Angle.ofDMS(23, 26, 21.45);
        sinEpsylon = Math.sin(Epsylon);
        cosEpsylon = Math.cos(Epsylon);
    }


    @Override
    /**
     * Converts the given EclipticCoordinates to EclipticCoordinates
     * @param ec1 EclipticCoordinates to convert
     * @return the converted coordinates
     */
    public EquatorialCoordinates apply(EclipticCoordinates ec1) {
        double sinTheta = Math.sin(ec1.lon());

        double alpha = (sinTheta * cosEpsylon - Math.tan(ec1.lat())) / (Math.cos(ec1.lon()));
        alpha = Math.atan2(alpha, 1);
        double delta = Math.asin(Math.sin(ec1.lat()) * cosEpsylon + Math.cos(ec1.lat()) * sinEpsylon * sinTheta);


        return EquatorialCoordinates.of(alpha, delta);
    }


    @Override
    final public int hashCode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }
}

